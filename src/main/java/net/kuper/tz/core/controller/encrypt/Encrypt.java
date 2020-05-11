package net.kuper.tz.core.controller.encrypt;

import net.kuper.tz.core.utils.EncodeUtils;
import net.kuper.tz.core.utils.EncryptUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class Encrypt {

    private static Logger logger = LoggerFactory.getLogger(Encrypt.class);

    private static String key = "ea003686e805aa4a";
    private static String iv = "10144a283749a54a";
    private static String transformation = "AES/CBC/PKCS5Padding";

    public static byte[] encrypt(String src) {
        logger.debug(src);
        byte[] bytes = EncryptUtils.encryptAES(EncodeUtils.base64Encode(src), key.getBytes(), transformation, iv.getBytes());
        return EncodeUtils.base64Encode(bytes);
    }

    public static byte[] decrypt(String src) {
        logger.debug(src);
        byte[] bytes = EncryptUtils.decryptAES(EncodeUtils.base64Decode(src), key.getBytes(), transformation, iv.getBytes());
        return EncodeUtils.base64Decode(bytes);
    }


}
