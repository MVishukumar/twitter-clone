package com.springframeworkvishu.services;

import com.springframeworkvishu.config.AppConfigForJasyptSimple;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PasswordEncryptorTest {

    @Autowired
    PropertyServiceForJasyptSimple propertyServiceForJasyptSimple;

    @Test
    public void whenDecryptedPasswordNeeded_GetFromService() {
        System.setProperty("jasypt.encryptor.password", "hireme");

        assertEquals("appuser", propertyServiceForJasyptSimple.getProperty1());
        assertEquals("apppassword", propertyServiceForJasyptSimple.getProperty2());
    }
}