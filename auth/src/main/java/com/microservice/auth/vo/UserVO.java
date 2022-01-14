package com.microservice.auth.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;

@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class UserVO implements Serializable {

    private static final long serialVersionUID = 2254672522432776316L;

    @JsonProperty("username")
    private String userName;

    @JsonProperty("password")
    private String password;
}
