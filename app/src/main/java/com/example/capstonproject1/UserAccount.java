package com.example.capstonproject1;

/*
 * 사용자 계정 정보 모델 클래스
 */
public class UserAccount {

    private String idToken;
    private String id;
    private String password;
    private String name;
    private String hpNumber;

    public UserAccount(){ }

    public String getIdToken() {
        return idToken;
    }

    public void setIdToken(String idToken) {
        this.idToken = idToken;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHpNumber() {
        return hpNumber;
    }

    public void setHpNumber(String hpNumber) {
        this.hpNumber = hpNumber;
    }
}
