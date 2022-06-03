package com.javipege5.zeobackend.DTO;


public class UserList {
    private String name;
    private Long age;
    private String email;

    public UserList(){}

    public UserList(String name, Long age, String email){
        this.name = name;
        this.age = age;
        this.email = email;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getAge() {
        return age;
    }

    public void setAge(Long age) {
        this.age = age;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
}
