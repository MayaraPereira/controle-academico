package com.works.api.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AuthError {
    private String mensagemErro;
}
