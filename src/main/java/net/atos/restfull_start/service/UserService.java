package net.atos.restfull_start.service;

import lombok.AllArgsConstructor;
import net.atos.restfull_start.model.AggregatedValue;
import net.atos.restfull_start.model.Message;
import net.atos.restfull_start.model.Role;
import net.atos.restfull_start.model.User;
import net.atos.restfull_start.model.dtos.UserDto;
import net.atos.restfull_start.repository.MessageRepository;
import net.atos.restfull_start.repository.RoleRepository;
import net.atos.restfull_start.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private MessageRepository messageRepository;

    public List<String> getAggregatedValuesNativeQuery(){
        return messageRepository.getAggregatedValues();
    }

    public List<Message> getAllMessagesCustomQuery(){
        return messageRepository.getAllMessagesQuery();
    }
    public List<Message> getMessagesForUser(Long user_id){
        return userRepository.getOne(user_id).getMessages();
    }

    public Message getMessageById(Long message_id){
        return messageRepository.getOne(message_id);
    }

    public User getUserById(Long user_id){
        Optional<User> userOptinal = userRepository.findById(user_id);
        return userOptinal.orElseGet(
                () -> new User(null, null, null, null, null, null, null, null));
    }

    // zwraca liczbę rekordów
    public Long countAllMessages(){
        return messageRepository.count();
    }
    public Map<String,Integer> groupMessagesByUsers(){
        Map<String, Integer> groupedMessages = new HashMap<>();
        for(User user : userRepository.findAll()){
            Integer messageCount = user.getMessages().size();
            System.out.println(messageCount);
            groupedMessages.put(user.getLogin(), messageCount);
        }
        return groupedMessages;
    }

    public List<Message> getAllMessages(){
        return messageRepository.findAll();
    }
    // stronicowanie wiadomości
    public List<Message> getPagingMessages(int page, int size){
        Page<Message> messagesPage = messageRepository.findAll(PageRequest.of(page,size));
        return messagesPage.getContent();
    }


    public Message addMessage(String content,Long user_id){
        Message message=new Message(content);
        message.setUser(userRepository.getOne(user_id));
        return messageRepository.save(message);
    }
    public void addMessageByUser(Long user_id, String content){
        Optional<User> userOptional = userRepository.findById(user_id);
        if(userOptional.isPresent()){
            User user = userOptional.get();
            // ustawienie pole user w obiekci message
            Message message = new Message(content);
            message.setUser(user);
            // dodanie wiadomości do listy messages
            user.addMessage(message);
            userRepository.save(user);
        }
    }

    public User addUser(UserDto userDto){
        // save() -> insert into user values (?,?)
        return userRepository.save(new User(userDto.getLogin(), userDto.getPassword()));
    }
    // metoda dodająca wybraną po id rolę do użytkownika
    public User addRoleToUserById(Long user_id, Long role_id){
        // wyszukaj użytkownika po id
        Optional<User> userOptional = userRepository.findById(user_id);
        Optional<Role> roleOptional = roleRepository.findById(role_id);
        if(userOptional.isPresent() && roleOptional.isPresent()){
            // przypisanie roli do użytkownika -> dodanie roli do zbioru roles klasy user
            User user = userOptional.get();
            user.addRole(roleOptional.get());
            return userRepository.save(user);
        }
        return null;
    }
    // metoda usuwająca użytkownika po id
    public void deleteUserById(Long user_id){
        userRepository.deleteById(user_id);
    }
    // metoda pobierająca wszystkich użytkowników posortowanych po loginie
    public List<User> getAllUsersSortedByLogin(){
        return userRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(User::getLogin))
                .collect(Collectors.toList());
    }
    public List<User> getUsersWithRole(List<Role> role){
        return userRepository.findAllByRoles(role);
    }
    public List<User> getAllUsersOrderByLogin(){
//        Page<User> pages = userRepository.findAll(PageRequest.of(0,2));
//           System.out.println(pages);
        return userRepository.findAllByLoginLikeOrderByLogin("%");
    }
    public List<User> getUsersWithStatusOrderedByregisterDate(Boolean status){
        return userRepository.findAllByStatusOrderByRegisterDateAsc(status);
    }









}
