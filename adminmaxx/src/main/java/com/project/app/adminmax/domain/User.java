package com.project.app.adminmax.domain;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//import lombok.Data;
import java.io.Serializable;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;

/**
 *
 * @author suzy
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Document(collection = "user")
public class User implements Serializable {
    
    public enum RoleType {
        regular,
        admin
    }
    
    @Id
    private String id;

    @NotEmpty
    @Email(message = "Email should be valid")
    private String email;

    @NotNull
    @NotEmpty
    @Size(min = 3, max = 15)
    private String password;

    @NotNull
    @NotEmpty
    private String firstName;

    @NotNull
    @NotEmpty
    private String lastName;

    @NotNull
    @NotEmpty
    private String role;
    
}
