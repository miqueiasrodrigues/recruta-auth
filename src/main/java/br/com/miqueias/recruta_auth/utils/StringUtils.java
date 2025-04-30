package br.com.miqueias.recruta_auth.utils;

public class StringUtils {
    public static String clean(String texto) {
        return texto.replaceAll("[^a-zA-Z0-9]", "");
    }
}