package com.bale.econ.springboot.domain.user;

import com.bale.econ.springboot.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Table(name="CUST_M")
@Entity
public class User extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String cstno;

    @Column(nullable = false)
    private String name;

    private String email;

    @Column
    private String picture;

    @Column
    private String platform;

    @Column
    private String accessToken;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Builder
    public User(String cstno, String name, String email, String picture, String platform, String accessToken, Role role) {
        this.cstno = cstno;
        this.name = name;
        this.email = email;
        this.picture = picture;
        this.platform = platform;
        this.accessToken = accessToken;
        this.role = role;
    }

    public User update(String name, String email, String picture, String accessToken) {
        this.name = name;
        this.email = email;
        this.picture = picture;
        this.accessToken = accessToken;

        return this;
    }

    public String getRoleKey() {
        return this.role.getKey();
    }
}
