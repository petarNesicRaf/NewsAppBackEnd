package rs.raf.publicnewstest.entities;

import rs.raf.publicnewstest.roles.Role;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class User {
    private int id;

    @NotNull(message = "Email is required")
    @NotEmpty(message = "Email is required")
    private String email;
    @NotNull(message = "Name is required")
    @NotEmpty(message = "Name is required")
    private String name;
    @NotNull(message = "Surname is required")
    @NotEmpty(message = "Surname is required")
    private String surname;
    @NotNull(message = "Role is required")
    @NotEmpty(message = "Role is required")
    private String role;
    @NotNull(message = "Status is required")
    //@NotEmpty(message = "Status is required")
    private int status;
    @NotNull(message = "Password is required")
    @NotEmpty(message = "Password is required")
    private String password;

    public User() {
    }

    public User(int id, String email, String name, String surname, String role, int status, String password) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.role = role;
        this.status = status;
        this.password = password;
    }

    public User(String email, String name, String surname, String role, int status, String password) {
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.role = role;
        this.status = status;
        this.password = password;
    }

    public User(String email, String name, String surname, String password) {
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
