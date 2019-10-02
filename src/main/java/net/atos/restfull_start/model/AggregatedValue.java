package net.atos.restfull_start.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AggregatedValue {
    private Integer user_id;
    private Integer message_id;
}
