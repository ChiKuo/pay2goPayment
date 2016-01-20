package chikuo.tw.pay2gopayment;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by chikuo on 2016/1/20.
 */
public class Encrypt {

    public static String sha256(String base) {
        try{
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(base.getBytes("UTF-8"));
            StringBuffer hexString = new StringBuffer();

            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(0xff & hash[i]);
                if(hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();
        } catch(Exception ex){
            throw new RuntimeException(ex);
        }
    }

    public static byte[] encryptAES (String data) throws UnsupportedEncodingException {

        byte[] ivs = "9g7U8YoWAmxv6BOu".getBytes("UTF-8") ;
        byte[] key = "Up5QKRRuOBXAVJ3iofcY5HZhM7bTmJbh".getBytes("UTF-8");

//        byte[] ivs = "1234567890123456".getBytes() ;
//        byte[] key = "12345678901234567890123456789012".getBytes();

        try {
            AlgorithmParameterSpec mAlgorithmParameterSpec = new IvParameterSpec(ivs);
            SecretKeySpec mSecretKeySpec = new SecretKeySpec(key, "AES");
            Cipher mCipher = null;
            mCipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
            mCipher.init(Cipher.ENCRYPT_MODE, mSecretKeySpec, mAlgorithmParameterSpec);

            return mCipher.doFinal(padString(data).getBytes());
        } catch (Exception ex) {
            return null;
        }
    }

    private static String padString(String source) {
        char paddingChar = ' ';
        int size = 32;
        int x = source.length() % size;
        int padLength = size - x;

        for (int i = 0; i < padLength; i++) {
            source += paddingChar;
        }

        return source;
    }

    public static String bytesToHex(byte[] data) {
        if (data==null) {
            return null;
        }

        int len = data.length;
        String str = "";
        for (int i=0; i<len; i++) {
            if ((data[i]&0xFF)<16)
                str = str + "0" + Integer.toHexString(data[i]&0xFF);
            else
                str = str + Integer.toHexString(data[i]&0xFF);
        }
        return str;
    }
}
