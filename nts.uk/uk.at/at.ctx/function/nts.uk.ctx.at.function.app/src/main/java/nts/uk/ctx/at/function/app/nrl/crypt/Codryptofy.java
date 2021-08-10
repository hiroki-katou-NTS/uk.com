package nts.uk.ctx.at.function.app.nrl.crypt;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

/**
 * Codryptofy.
 * 
 * @author manhnd
 */
public class Codryptofy {
	
	/**
	 * Shift JIS charset
	 */
	public static final Charset SHIFT_JIS = Charset.forName("Shift_JIS");
	
	/**
	 * Secret key 
	 */
	private static final String SECRET_KEY = "U8Q!!m4Gg0R%sMy2";
	
	/**
	 * Decrypt data.
	 * @param encryptedData ecrypted data
	 * @return decrypted data
	 */
	public static String aesDecrypt(String encryptedData) {
		try {
			byte[] encryptedBytes = decode(encryptedData);
			Cipher cipher = initCipher(Cipher.DECRYPT_MODE);
			byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
			
			return encode(decryptedBytes);
		} catch (BadPaddingException | IllegalBlockSizeException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * Encrypt data.
	 * @param plainText plain text
	 * @return encrypted data
	 */
	public static String aesEncrypt(String plainText) {
		try {
			Cipher cipher = initCipher(Cipher.ENCRYPT_MODE);
			byte[] encryptedBytes = cipher.doFinal(decode(plainText));
			
			return bytesToHexString(encryptedBytes);
		} catch (BadPaddingException | IllegalBlockSizeException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * Encrypt stream.
	 * @param inputStream stream
	 * @param byteSize size
	 * @return encrypted stream
	 */
	public static InputStream aesEncrypt(InputStream inputStream, int byteSize) {
		try {
			Cipher cipher = initCipher(Cipher.ENCRYPT_MODE);
			byte[] encryptedBytes = new byte[byteSize];
			byte[] bf = new byte[64];
			int encryptedPos = 0;
			int read;
			byte[] output;
			while ((read = inputStream.read(bf)) != -1) {
				output = cipher.update(bf, 0, read);
				if (output != null) {
					System.arraycopy(output, 0, encryptedBytes, encryptedPos, output.length);
					encryptedPos += output.length;
				}
			}
			
			output = cipher.doFinal();
			if (output != null) {
				System.arraycopy(output, 0, encryptedBytes, encryptedPos, output.length);
			}
			return new ByteArrayInputStream(encryptedBytes);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}
	
	/**
	 * Decrypt stream.
	 * @param encryptedStream stream
	 * @param byteSize size
	 * @return decrypted stream
	 */
	public static InputStream aesDecrypt(InputStream encryptedStream, int byteSize) {
		try {
			Cipher cipher = initCipher(Cipher.DECRYPT_MODE);
			byte[] decryptedBytes = new byte[byteSize];
			byte[] bf = new byte[64];
			int decryptedPos = 0;
			int read;
			byte[] output;
			while ((read = encryptedStream.read(bf)) != -1) {
				output = cipher.update(bf, 0, read);
				if (output != null) {
					System.arraycopy(output, 0, decryptedBytes, decryptedPos, output.length);
					decryptedPos += output.length;
				}				
			}
			output = cipher.doFinal();
			if (output != null) {
				System.arraycopy(output, 0, decryptedBytes, decryptedPos, output.length);
			}
			return new ByteArrayInputStream(decryptedBytes);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}
	
	/**
	 * Init cipher in mode.
	 * @param mode mode
	 * @return cipher
	 */
	private static Cipher initCipher(int mode) {
		return initCipher(mode, SECRET_KEY);
	}
	
	/**
	 * Init cipher.
	 * @param mode cipher mode
	 * @param key key
	 * @return cipher
	 */
	private static Cipher initCipher(int mode, String key) {
		try {
			Cipher cipher = getCipher();
			byte[] keyBytes = decode(key);
			SecretKeySpec secretKey = new SecretKeySpec(keyBytes, "AES");
			cipher.init(mode, secretKey);
			return cipher;
		} catch (InvalidKeyException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * Get cipher.
	 * @return cipher
	 */
	public static Cipher getCipher() {
		Cipher cipher;
		try {
			cipher = Cipher.getInstance("AES/ECB/ZeroBytePadding");
		} catch (NoSuchAlgorithmException | NoSuchPaddingException ex) {
			throw new RuntimeException(ex);
		}
		return cipher;
	}
	
	/**
	 * Encode partial byte array.
	 * @param arr array
	 * @param from from index
	 * @param to to index
	 * @return string
	 */
	public static String encode(byte[] arr, int from, int to) {
		return encode(Arrays.copyOfRange(arr, from, to));
	}
	
	/**
	 * Encode byte array in Shift JIS.
	 * @param arr byte array
	 * @return string
	 */
	public static String encode(byte[] arr) {
		return new String(arr, SHIFT_JIS);
	}
	
	/**
	 * Encode byte array in UTF-8.
	 * @param arr byte array
	 * @return string
	 */
	public static String encodeUTF8(byte[] arr) {
		return new String(arr, StandardCharsets.UTF_8);
	}
	
	/**
	 * Decode text to byte array using Shift JIS.
	 * @param text text
	 * @return byte array
	 */
	public static byte[] decode(String text) {
		return text.getBytes(SHIFT_JIS);
	}
	
	/**
	 * Decode text to byte array using UTF-8.
	 * @param text text
	 * @return byte array
	 */
	public static byte[] decodeUTF8(String text) {
		return text.getBytes(StandardCharsets.UTF_8);
	}
	
	/**
	 * Convert byte to hex string.
	 * @param b byte
	 * @return hex string
	 */
	public static String byteToHexString(byte b) {
		return String.format("%02x", b);
	}
	
	/**
	 * Convert byte array to hex string.
	 * @param b byte array
	 * @return hex string
	 */
	public static String bytesToHexString(byte[] b) {
		return DatatypeConverter.printHexBinary(b);
	}
	
	/**
	 * Convert from hex string.
	 * @param hexText hex string
	 * @return string
	 */
	public static String fromHexString(String hexText) {
		byte[] bytes = DatatypeConverter.parseHexBinary(hexText);
		return encodeUTF8(bytes);
	}
}
