/**
 * Created by:AIDA
 * Date : 6/18/2024
 * Time : 7:59 AM
 */
package com.example.userApp.shared;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.Random;

@Component
public class Utils {

    private final Random random = new SecureRandom();
    private final String ALPHABET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    //private final int ITERATIONS =  1000;
    //private final int KEY_LENGTH = 256;

    public String generateUserId(int length){
        return generateRandomString(length);
    }

    private String generateRandomString(int length){
        StringBuilder builder = new StringBuilder(length);

        for(int i = 0; i < length; i++){
            builder.append(ALPHABET.charAt(random.nextInt(ALPHABET.length())));
        }
        return new String(builder);
    }
}
