package nts.uk.ctx.at.function.app.nrl.repository;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Terminal.
 * 
 * NR通信コマンドの変値
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
	
	/**
	 * ContractCode
	 */
	private String contractCode;
}
