/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.app.adminmax.domain;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 *
 * @author suzy
 */


@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString

public class LoginRequest implements Serializable {
    
    private String email;
    private String password;
}
