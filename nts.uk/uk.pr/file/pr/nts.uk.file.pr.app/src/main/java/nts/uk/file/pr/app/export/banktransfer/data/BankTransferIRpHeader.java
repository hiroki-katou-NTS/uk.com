package nts.uk.file.pr.app.export.banktransfer.data;

import lombok.Data;

@Data
public class BankTransferIRpHeader {
	private int dataPartition = 1;
	private int typeCode = 21;
	private int codeDivision = 0;
	/**
	 * G_SEL_002
	 */
	private int consignorCode; // 10 ky tu
	/**
	 * G_SEL_001
	 */
	private String payeeName; // 40 ky tu
	/**
	 * G_INP_001
	 */
	private int transferDate; // 4 ky tu MMdd 
	
	/**
	 * G_SEL_001
	 */
	private int bankCode; // 4
	
	/**
	 * G_SEL_001
	 */
	private String bankName; // 15
	
	/**
	 * G_SEL_001
	 */
	private int branchCode; // 3
	
	/**
	 * G_SEL_001
	 */
	private String branchName; // 15
	
	/**
	 * G_LBL_010
	 */
	private int accountAtr; // 1
	
	/**
	 * G_LBL_012
	 */
	private int accountNo; // 7
	
	/**
	 * fix 17 space
	 */
	private String dumSpace = "                "; // 17
}
