package com.example.movierec.service;

import com.example.movierec.dto.RegistrationForm;
import com.example.movierec.entity.User;

public interface RegistrationService {

    User register(RegistrationForm form);
}
