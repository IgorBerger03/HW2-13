package com.example.hw213.service;

import com.example.hw213.exception.IncorrectNameException;
import com.example.hw213.exception.IncorrectSurnameException;
import org.springframework.util.StringUtils;

import static org.apache.tomcat.util.http.parser.HttpParser.isAlpha;

public class ValidationService {
    public String validateName(String name) {
        if (!isAlpha(Integer.parseInt(name))) {
            throw new IncorrectNameException();
        }
        return StringUtils.capitalize(name.toLowerCase());
    }

    public String validateSurname(String surname) {
        String[] surnames = surname.split("-");
        for (int i = 0; i < surnames.length; i++) {
            if (!isAlpha(Integer.parseInt(surnames[i]))) {
                throw new IncorrectSurnameException();
            }
            surnames[i] = StringUtils.capitalize(surnames[i].toLowerCase());
        }
        return String.join("-", surnames);
    }
}
