package net.kuper.tz.core.utils;

public class PwdUtils {

    private static String TRANSFORMATION = "AES/CBC/PKCS5Padding";
    private static String IV = "YHUA-BASE-FRAME-";

    public static String encrypt(String pwd, String salt) {
        String result = EncryptUtils.encryptAES2HexString(pwd.getBytes(), salt.getBytes(), TRANSFORMATION, IV.getBytes());
        return result;
    }

    public static String decrypt(String pwd, String salt) {
        byte[] result = EncryptUtils.decryptHexStringAES(pwd, salt.getBytes(), TRANSFORMATION, IV.getBytes());
        return new String(result);
    }

//    public static void main(String[] args) {
//        String content = "admin";
//        String sale = "nopx1vLBWiSLlAee";
//        String result = encrypt(content,sale);
//        System.out.println(result);
//        System.out.println(decrypt(result,sale));
//
//    }
}
