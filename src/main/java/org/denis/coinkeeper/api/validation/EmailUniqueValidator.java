package org.denis.coinkeeper.api.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.denis.coinkeeper.api.entities.UserEntity;
import org.denis.coinkeeper.api.repositories.UserRepository;

import java.util.Optional;

@RequiredArgsConstructor
public class EmailUniqueValidator implements ConstraintValidator<EmailUnique,String> {

    private final UserRepository userRepository;

    @Override
    public void initialize(EmailUnique constraintAnnotation) {
    }

    @Override
    public boolean isValid(String uniqEmail, ConstraintValidatorContext context) {
        Optional<UserEntity> userEntityOptional = userRepository.findByEmail(uniqEmail);

        return userEntityOptional.isEmpty();
    }
}
