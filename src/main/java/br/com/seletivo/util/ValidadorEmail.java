package br.com.seletivo.util;

import java.util.regex.Pattern;

public class ValidadorEmail {

    private static final String REGEX_EMAIL =
            "^[a-zA-Z0-9._%+\\-]+@[a-zA-Z0-9.\\-]+\\.[a-zA-Z]{2,}$";

    private static final Pattern PATTERN = Pattern.compile(REGEX_EMAIL);

    public static boolean validar(String email) {
        if (email == null || email.isBlank()) return false;
        return PATTERN.matcher(email.trim()).matches();
    }
}
