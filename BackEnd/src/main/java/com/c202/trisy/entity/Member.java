package com.c202.trisy.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
@EntityListeners(value = AuditingEntityListener.class)
@Builder
public class Member extends BaseTimeEntity{

    @Column(nullable = false, updatable = false, length = 45,unique = true)
    private String email;
    @Column(nullable = false, length = 100)
    private String password;
    @Column(nullable = false, length = 100)
    private String name;

    @Column(length = 20)
    @Enumerated(EnumType.STRING)
    private Role role;

    private LocalDate birth;
    @Column(nullable = false,length = 11)
    private String phone;

    @Column(nullable = false, length = 30)
    private String nickname;

    private String provider;
    private String providerId;

    private String profileUrl;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "survey_id")
    private Survey survey;

    public void setUserPassword(String password) {
        this.password = password;
    }

    public void changeNickname(String nickname) {this.nickname = nickname;}
    public void changeName(String name){
        this.name = name;
    }
    public void changeBirth(LocalDate birth){
        this.birth =birth;
    }
    public void changePhone(String phone){
        this.phone=phone;
    }

    public void updateSurvey(Survey survey){
        this.survey = survey;
    }

    public void updateProfile(String profileUrl){
        this.profileUrl = profileUrl;
    }
}
