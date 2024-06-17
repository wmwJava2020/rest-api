/**
 * Created by:Wondafrash
 * Date : 6/17/2024
 * Time : 12:19 PM
 */
package com.example.userApp.shared;
import java.io.Serializable;

public class UserDTO implements Serializable {
    private static final long serialVersionUID = 3l;
    private long id;
    private String userId;
    private String firstName;
    private String lastName;
    private String email;
    private String password; // unencrypted plane text password
    private String encryptedPassword;
    private String emailVerificationToken;
    private boolean emailVerificationStatus;
}
