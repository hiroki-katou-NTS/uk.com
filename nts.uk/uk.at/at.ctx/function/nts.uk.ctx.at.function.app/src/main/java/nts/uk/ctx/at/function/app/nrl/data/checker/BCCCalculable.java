package nts.uk.ctx.at.function.app.nrl.data.checker;

import java.nio.ByteBuffer;

/**
 * Implement to calculate BCC.
 * 
 * @author manhnd
 */
public interface BCCCalculable {

	/**
	 * Checksum.
	 * @param bytes
	 * @return sum
	 */
	public default short checkSum(byte[] bytes) {
		long sum = 0;
		long polynomial = 0x01102100;
		for (byte b : bytes) {
			sum |= ((int) b & 0xFF);
			for (int i = 0; i < 8; i++) {
				sum <<= 1;
				if ((sum & 0x01000000) > 0) {
					sum ^= polynomial;
				}
			}
		}
		
		return (short)((sum & 0x00FFFF00) >> 8);
	}
	
	/**
	 * Bytes to long.
	 * @param bytes
	 * @return long
	 */
	public default long bytesToLong(byte[] bytes) {
	    ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
	    buffer.put(bytes);
	    buffer.flip();
	    return buffer.getLong();
	}
}
