package rs.raf.publicnewstest.requests;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class EditUserRequest {
    @NotNull(message = "Name is required")
    @NotEmpty(message = "Name is required")
    private String name;
    @NotNull(message = "Surname is required")
    @NotEmpty(message = "Surname is required")
    private String surname;
    @NotNull(message = "Email is required")
    @NotEmpty(message = "Email is required")
    private String email;
    @NotNull(message = "Role is required")
    @NotEmpty(message = "Role is required")
    private String role;

    public EditUserRequest() {
    }

    public EditUserRequest(String name, String surname, String email, String role) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.role = role;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
