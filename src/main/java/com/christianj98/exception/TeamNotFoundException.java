package com.christianj98.exception;

public class TeamNotFoundException extends RuntimeException {

    public TeamNotFoundException() {
        super("Team is not found!");
    }
}