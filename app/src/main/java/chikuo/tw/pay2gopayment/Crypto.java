package chikuo.tw.pay2gopayment;

import java.math.BigInteger;
import java.nio.charset.Charset;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by Chi on 2016/1/22.
 */
public class Crypto {

    private static final String mEngine = "AES";
    private static final String mCrypto = "AES/CBC/PKCS7Padding";
    private String mKey;
    private String mIv;

    public Crypto(String key, String iv) {
        this.mKey = key;
        this.mIv = iv;
    }

    public byte[] cipher(byte[] data, int mode, String crypto)
            throws NoSuchAlgorithmException, NoSuchPaddingException,
            InvalidKeyException, IllegalBlockSizeException,
            BadPaddingException, InvalidAlgorithmParameterException {

        SecretKeySpec sks = new SecretKeySpec(mKey.getBytes(Charset.forName("UTF-8")), mEngine);
        IvParameterSpec ivs = new IvParameterSpec(mIv.getBytes(Charset.forName("UTF-8")));
        Cipher c = Cipher.getInstance(crypto);
        c.init(mode, sks, ivs);
        return c.doFinal(data);
    }

    public byte[] encrypt(byte[] data) throws InvalidKeyException,
            NoSuchAlgorithmException, NoSuchPaddingException,
            IllegalBlockSizeException, BadPaddingException,
            InvalidAlgorithmParameterException {
        return cipher(data, Cipher.ENCRYPT_MODE, mCrypto);
    }

    public byte[] decrypt(byte[] data) throws InvalidKeyException,
            NoSuchAlgorithmException, NoSuchPaddingException,
            IllegalBlockSizeException, BadPaddingException,
            InvalidAlgorithmParameterException {
        return cipher(data, Cipher.DECRYPT_MODE, mCrypto);
    }

    public static String bytesToHex(byte[] data) {
        return String.format("%0" + (data.length * 2) + 'x', new BigInteger(1, data));
    }



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

}