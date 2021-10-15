package com.bale.econ.springboot.config.auth.dto;

import com.bale.econ.springboot.domain.user.Role;
import com.bale.econ.springboot.domain.user.User;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
public class OAuthAttributes {
    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String cstno;
    private String name;
    private String email;
    private String picture;
    private String platform;

    @Builder
    public OAuthAttributes(Map<String, Object> attributes, String nameAttributeKey, String cstno, String name, String email, String picture, String platform) {
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.cstno = cstno;
        this.name = name;
        this.email = email;
        this.picture = picture;
        this.platform = platform;
    }

    public static OAuthAttributes of(String registrationId, String userNameAttributeName, Map<String, Object> attributes) {

        if ("naver".equals(registrationId)) {
            return ofNaver("id", attributes);
        } else if ("kakao".equals(registrationId)) {
            return ofKakao("id", attributes);
        } else {
            return ofGoogle(userNameAttributeName, attributes);
        }
    }

    private static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes) {
        return OAuthAttributes.builder()
                .cstno((String) attributes.get("sub"))
                .name((String) attributes.get("name"))
                .email((String) attributes.get("email"))
                .picture((String) attributes.get("picture"))
                .platform("Google")
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    public static OAuthAttributes ofNaver(String userNameAttributeName, Map<String, Object> attributes) {
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");

        return OAuthAttributes.builder()
                .cstno((String) response.get("id"))
                .name((String) response.get("name"))
                .email((String) response.get("email"))
                .picture((String) response.get("profile_image"))
                .platform("Naver")
                .attributes(response)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    public static OAuthAttributes ofKakao(String userNameAttributeName, Map<String, Object> attributes) {
        Map<String, Object> response = (Map<String, Object>) attributes.get("kakao_account");
        Map<String, Object> profile = (Map<String, Object>) response.get("profile");

        return OAuthAttributes.builder()
                .cstno(String.valueOf(attributes.get("id")))
                .name((String) profile.get("nickname"))
                .email((String) response.get("email"))
                .picture((String) profile.get("profile_image_url"))
                .platform("Kakao")
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    public User toEntity() {
        return User.builder()
                .cstno(cstno)
                .name(name)
                .email(email)
                .picture(picture)
                .platform(platform)
                .role(Role.GUEST)
                .build();
    }
}
