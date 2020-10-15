package nts.uk.ctx.at.function.app.nrl.repository;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Terminal.
 * 
 * @author manhnd
 */
@Data
@AllArgsConstructor
public class Terminal {

	/**
	 *  NRLNo
	 */
	private String nrlNo;
	
	/**
	 * MAC Address
	 */
	private String macAddress;
}
