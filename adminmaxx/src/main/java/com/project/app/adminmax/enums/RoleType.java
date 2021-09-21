package com.project.app.adminmax.enums;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author suzy
 */
public enum RoleType {
    
    REGULAR("regular"),
    ADMIN("admin");

    private final String value;

    RoleType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
