package br.inf.datum.AuthService.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

/**
 * @author Jefferson
 * @descrition: Classe usada para criptografar texto e gerar hash
 *
 */
public class SegurancaUtil {

    /**
     * Metodo para encriptar senha para sha-256
     * @param password
     * @return String
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     */
    public static String encriptSHA256(String password) throws NoSuchAlgorithmException, UnsupportedEncodingException {

        MessageDigest algorithm = MessageDigest.getInstance("SHA-256");
        algorithm.update(password.getBytes("UTF-8"));
        return convertByteToString(algorithm.digest());
    }

    /**
     * Metodo gera UUID novos usuarios
     * @return String
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     */
    public static String createUUid()throws NoSuchAlgorithmException, UnsupportedEncodingException{
        UUID uuid = UUID.randomUUID();
        MessageDigest salt = MessageDigest.getInstance("SHA-256");
        salt.update(UUID.randomUUID().toString().getBytes("UTF-8"));
        return convertByteToString(salt.digest());

    }

    /**
     * Metodo para converter hexadecimal para String
     * @param digest
     * @return String
     */
    private static String convertByteToString(byte[] digest){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < digest.length; ++i) {
            sb.append(Integer.toHexString((digest[i] & 0xFF) | 0x100).substring(1, 3));
        }
        return sb.toString();
    }
}
