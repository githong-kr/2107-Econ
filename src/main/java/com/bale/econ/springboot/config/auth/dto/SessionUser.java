package com.bale.econ.springboot.config.auth.dto;

import com.bale.econ.springboot.domain.user.User;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class SessionUser implements Serializable {
    private String cstno;
    private String name;
    private String email;
    private String picture;
    private String platform;
    private String accessToken;

    public SessionUser(User user) {
        this.cstno = user.getCstno();
        this.name = user.getName();
        this.email = user.getEmail();
        this.picture = user.getPicture();
        this.picture = user.getPlatform();
        this.accessToken = user.getAccessToken();
    }
}
