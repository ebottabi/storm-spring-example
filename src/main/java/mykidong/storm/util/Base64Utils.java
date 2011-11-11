package mykidong.storm.util;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class Base64Utils {
    // base64 인코딩
    public static String base64Encoding(String value) {
        String retVal = "";

        try {
            byte[] plainText = null; // 평문
            plainText = value.getBytes();

            BASE64Encoder encoder = new BASE64Encoder();
            retVal = encoder.encode(plainText);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return retVal;
    }

    // base64 디코딩
    public static String base64decoding(String encodedString) {
        String retVal = "";

        try {
            byte[] plainText = null; // 해쉬 값
            BASE64Decoder decoder = new BASE64Decoder();
            plainText = decoder.decodeBuffer(encodedString);

            retVal = new String(plainText);
        } catch (Exception e) {

            e.printStackTrace();
        }

        return retVal;
    }



}
