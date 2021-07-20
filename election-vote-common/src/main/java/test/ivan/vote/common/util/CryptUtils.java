package test.ivan.vote.common.util;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class CryptUtils {
    //参数分别代表 算法名称/加密模式/数据填充方式
    private static final String ALGORITHMSTR = "AES/ECB/PKCS5Padding";

    private static final char[] HEX = {'0', '1', '2', '3', '4', '5',
            '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    /**
     * 密码加盐，再加密
     *
     * @param pwd
     * @param salt
     * @return
     */
    public static String encrypt(String pwd, String salt) {
        if (StringUtils.isBlank(pwd)) {
            throw new IllegalArgumentException("密码不能为空");
        }
        if (StringUtils.isBlank(salt)) {
            throw new IllegalArgumentException("盐值不能为空");
        }
        return DigestUtils.sha256Hex(pwd + salt);
    }

    /**
     * AES加密
     *
     * @param content    加密的字符串
     * @param encryptKey 密码，16位字符串
     * @return
     * @throws Exception
     */
    public static String encryptWithAES(String content, String encryptKey) throws Exception {
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        kgen.init(128);
        Cipher cipher = Cipher.getInstance(ALGORITHMSTR);
        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(encryptKey.getBytes(), "AES"));
        byte[] b = cipher.doFinal(content.getBytes("utf-8"));
        // 采用base64算法进行转码,避免出现中文乱码
        return Base64.getEncoder().encodeToString(b);
    }

    /**
     * AES加密后转成Hex字符串形式
     *
     * @param content
     * @param encryptKey
     * @return
     * @throws Exception
     */
    public static String encryptWithAESToHex(String content, String encryptKey) throws Exception {
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        kgen.init(128);
        Cipher cipher = Cipher.getInstance(ALGORITHMSTR);
        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(encryptKey.getBytes(), "AES"));
        byte[] b = cipher.doFinal(content.getBytes("utf-8"));
        return bytesToHex(b);
    }

    /**
     * AES解密
     *
     * @param encryptStr 密文
     * @param decryptKey 解密密码，16位字符串
     * @return
     * @throws Exception
     */
    public static String decryptWithAES(String encryptStr, String decryptKey) throws Exception {
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        kgen.init(128);
        Cipher cipher = Cipher.getInstance(ALGORITHMSTR);
        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(decryptKey.getBytes(), "AES"));
        // 采用base64算法进行转码,避免出现中文乱码
        byte[] encryptBytes = Base64.getDecoder().decode(encryptStr);
        byte[] decryptBytes = cipher.doFinal(encryptBytes);
        return new String(decryptBytes);
    }

    /**
     * 用AES解密Hex字符串
     *
     * @param encryptHexStr Hex字符串
     * @param decryptKey    密钥
     * @return
     * @throws Exception
     */
    public static String decryptHexWithAES(String encryptHexStr, String decryptKey) throws Exception {
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        kgen.init(128);
        Cipher cipher = Cipher.getInstance(ALGORITHMSTR);
        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(decryptKey.getBytes(), "AES"));
        // 采用base64算法进行转码,避免出现中文乱码
        byte[] encryptBytes = hexToByes(encryptHexStr);
        byte[] decryptBytes = cipher.doFinal(encryptBytes);
        return new String(decryptBytes);
    }

    /**
     * 二进制数组转Hex字符串
     *
     * @param bytes
     * @return
     */
    public static String bytesToHex(byte[] bytes) {
        int len = bytes.length;
        StringBuilder buf = new StringBuilder(len * 2);
        // 把密文转换成十六进制的字符串形式
        for (int j = 0; j < len; j++) {
            buf.append(HEX[(bytes[j] >> 4) & 0x0f]);
            buf.append(HEX[bytes[j] & 0x0f]);
        }
        return buf.toString();
    }

    public static byte[] hexToByes(String hex) {
        byte[] result = new byte[hex.length() / 2];
        for (int i = 0; i < result.length; i++) {
            result[i] = (byte) (Integer.valueOf(hex.substring(2 * i, 2 * i + 2), 16) & 0xFF);
        }
        return result;
    }
}
