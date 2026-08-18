package com.ashimCS.linkedinClone.userService.utils;

import static org.mindrot.jbcrypt.BCrypt.*;

public class BCrypt {
    public static String hashPassword(String password) {
        return hashpw(password, gensalt());
    }


    public static boolean match(String passwordText, String passwordHashed){
        return checkpw(passwordText, passwordHashed);
    }
}
