package com.clientdata.schemas.model;

import com.clientdata.schemas.enums.Message;
import lombok.Data;

@Data
public class ResponseBody {

    private String id;
    private Message message;
    private Customer customer;
}
