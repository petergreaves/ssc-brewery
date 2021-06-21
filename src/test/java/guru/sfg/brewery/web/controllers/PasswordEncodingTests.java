package guru.sfg.brewery.web.controllers;

import org.hibernate.boot.archive.scan.internal.NoopEntryHandler;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.LdapShaPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Disabled
public class PasswordEncodingTests {

    static final String PASSWORD = "password";


    @Test
    public void bcrypt15Encoder() throws Exception {

        PasswordEncoder bcrypt15 = new BCryptPasswordEncoder(15);

        //uses a random salt so value changes
        // default strength = 10
     //   System.out.println(bcrypt15.encode(PASSWORD));
        System.out.println(bcrypt15.encode("tiger"));
    //    System.out.println(bcrypt15.encode(PASSWORD));
    }

    @Test
    public void bcryptEncoder() throws Exception {

        PasswordEncoder bcrypt = new BCryptPasswordEncoder();

        //uses a random salt so value changes
        // default strength = 10
        System.out.println(bcrypt.encode(PASSWORD));
        System.out.println(bcrypt.encode("bar"));
        System.out.println(bcrypt.encode(PASSWORD));
    }
    @Test
    public void sha265Encoder() throws Exception {

        PasswordEncoder sha256 = new StandardPasswordEncoder();

        //uses a random salt so value changes
        System.out.println(sha256.encode(PASSWORD));
        System.out.println(sha256.encode(PASSWORD));
        System.out.println(sha256.encode("tiger"));
    }

    @Test
    public void testLdapEncoder() throws Exception {

        PasswordEncoder ldap = new LdapShaPasswordEncoder();

        //uses a random salt so value changes
        System.out.println(ldap.encode(PASSWORD));
        System.out.println(ldap.encode("bar"));

        String encodedPwd = ldap.encode(PASSWORD);

        assertTrue(ldap.matches(PASSWORD, encodedPwd ));
    }

    @Test
    public void noOpEncoder() throws Exception {

        PasswordEncoder noOp = NoOpPasswordEncoder.getInstance();

        System.out.println(noOp.encode(PASSWORD));
    }




    @Test
    public void hashingExample() throws Exception {

        System.out.println(DigestUtils.md5DigestAsHex(PASSWORD.getBytes(StandardCharsets.UTF_8)));
        System.out.println(DigestUtils.md5DigestAsHex(PASSWORD.getBytes(StandardCharsets.UTF_8)));

        String salted = PASSWORD+"ThisIsASaltValue";

        System.out.println(DigestUtils.md5DigestAsHex(salted.getBytes(StandardCharsets.UTF_8)));

    }
}

