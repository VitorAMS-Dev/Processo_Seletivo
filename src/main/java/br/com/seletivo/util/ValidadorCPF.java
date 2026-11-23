package br.com.seletivo.util;

public class ValidadorCPF {

    public static boolean validar(String cpf) {
        if (cpf == null) return false;

        // Remove formatação
        cpf = cpf.replaceAll("[^0-9]", "");

        if (cpf.length() != 11) return false;

        // Verifica sequências inválidas (ex: 111.111.111-11)
        if (cpf.matches("(\\d)\\1{10}")) return false;

        // Validação do primeiro dígito verificador
        int soma = 0;
        for (int i = 0; i < 9; i++) {
            soma += Character.getNumericValue(cpf.charAt(i)) * (10 - i);
        }
        int primeiroDigito = 11 - (soma % 11);
        if (primeiroDigito >= 10) primeiroDigito = 0;
        if (primeiroDigito != Character.getNumericValue(cpf.charAt(9))) return false;

        // Validação do segundo dígito verificador
        soma = 0;
        for (int i = 0; i < 10; i++) {
            soma += Character.getNumericValue(cpf.charAt(i)) * (11 - i);
        }
        int segundoDigito = 11 - (soma % 11);
        if (segundoDigito >= 10) segundoDigito = 0;

        return segundoDigito == Character.getNumericValue(cpf.charAt(10));
    }

    public static String formatar(String cpf) {
        cpf = cpf.replaceAll("[^0-9]", "");
        if (cpf.length() == 11) {
            return cpf.substring(0, 3) + "." + cpf.substring(3, 6) + "."
                    + cpf.substring(6, 9) + "-" + cpf.substring(9);
        }
        return cpf;
    }
}
