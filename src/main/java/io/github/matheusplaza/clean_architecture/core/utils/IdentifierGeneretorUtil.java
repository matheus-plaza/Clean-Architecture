package io.github.matheusplaza.clean_architecture.core.utils;

import java.security.SecureRandom;

public class IdentifierGeneretorUtil {

    private static final String LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String NUMBERS = "0123456789";
    private static final SecureRandom RANDOM = new SecureRandom();

    public static String execute() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            int index = RANDOM.nextInt(LETTERS.length());
            sb.append(LETTERS.charAt(index));
        }
        for (int i = 0; i < 4; i++) {
            int index = RANDOM.nextInt(NUMBERS.length());
            sb.append(NUMBERS.charAt(index));
        }
        return sb.toString();
    }
}
