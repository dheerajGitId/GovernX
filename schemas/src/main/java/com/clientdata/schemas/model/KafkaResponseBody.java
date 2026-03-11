package com.clientdata.schemas.model;

import com.clientdata.schemas.enums.Message;
import lombok.Data;

import java.util.Date;

@Data
public class KafkaResponseBody {

    private String id;
    private Message message;
    private Date timestamp;
}
