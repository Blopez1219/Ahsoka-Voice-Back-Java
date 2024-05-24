package com.api.ahsoka.exceptions;

public class Exceptions {


    public static class UsernameNotFoundException extends RuntimeException {
        public UsernameNotFoundException(String message) {
            super(message);
        }
    }
}

