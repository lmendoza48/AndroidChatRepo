package edu.galileo.android.androidchat.domain;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Alejandro on 19/7/2017.
 * clase que me permite cargar el avatar de una imagen
 */
public class AvatarHelper {
    private final static  String GRAVATAR_URL = "http://www.gravatar.com/avatar/";

    /**
     * metodo que me permite agregar una imagen
     * NOTA: como es un metodo estatico no necesito instanciarlo de donde lo voy a usar
     * @param email
     * @return
     */
    public static String getAvatarUrl(String email){
        return GRAVATAR_URL + md5(email) + "?s=72";
    }

    private static  final String md5(String email){
        final String MD5 = "MD5";
        try{
            //Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest
                    .getInstance(MD5);
            digest.update(email.getBytes());
            byte messageDIgest[] = digest.digest();

            //Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDIgest){
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while(h.length() < 2){
                    h = "0"+h;
                }
                hexString.append(h);
            }
            return hexString.toString();
        }catch (NoSuchAlgorithmException e){
            e.printStackTrace();
        }
        return "";
    }
}
