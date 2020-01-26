package com.springframeworkvishu.config;

import com.ulisesbocchio.jasyptspringboot.annotation.EncryptablePropertySource;
import com.ulisesbocchio.jasyptspringboot.configuration.EnableEncryptablePropertiesConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@EncryptablePropertySource(name= "qaEncryptionProperties", value = "encryptedv2.properties")
public class AppConfigForJasyptSimple {
}