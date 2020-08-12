package nts.uk.ctx.at.function.app.nrl.response;

import java.util.List;

/**
 * Mean carry.
 * 
 * @author manhnd
 */
public interface MeanCarryable {
	
	/**
	 * Put item.
	 * @param element
	 * @param data
	 */
	void putItem(String element, String data);
	
	/**
	 * Pick item.
	 * @param element
	 * @return item
	 */
	String pickItem(String element);
	
	/**
	 * Is cracked.
	 * @return cracked
	 */
	boolean isCracked();
	
	/**
	 * Payload.
	 * @param st
	 */
	void payload(String st);
	
	/**
	 * BCC.
	 * @param bcc
	 */
	void bcc(String bcc);
	
	/**
	 * Set bytes.
	 * @param bytes
	 */
	void setBBytes(byte[] bytes);
	
	/**
	 * Get bytes.
	 * @param items
	 * @return bytes
	 */
	byte[] getBytes(List<String> items);
}
