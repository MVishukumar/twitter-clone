package com.springframeworkvishu.services;

import lombok.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Service
public class PropertyServiceForJasyptSimple {

    @Value("${spring.datasource.username}")
    private String property1;


    @Value("${spring.datasource.password}")
    private String property2;

}