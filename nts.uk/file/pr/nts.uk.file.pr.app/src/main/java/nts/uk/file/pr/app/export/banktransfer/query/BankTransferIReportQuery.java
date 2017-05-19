package nts.uk.file.pr.app.export.banktransfer.query;

import lombok.Data;
import nts.arc.time.GeneralDate;

@Data
public class BankTransferIReportQuery {
	/**
	 * G_SEL_002
	 */
	private String consignorCode; // 10 ky tu
	/**
	 * G_SEL_001
	 */
	private String payeeName; // 40 ky tu
	/**
	 * G_INP_001
	 */
	private GeneralDate transferDate; // 4 ky tu MMdd 
	
	/**
	 * G_SEL_001
	 */
	private String bankCode; // 4
	
	/**
	 * G_SEL_001
	 */
	private String bankName; // 15
	
	/**
	 * G_SEL_001
	 */
	private String branchCode; // 3
	
	/**
	 * G_SEL_001
	 */
	private String branchName; // 15
	
	/**
	 * G_LBL_010
	 */
	private String accountAtr; // 1
	
	/**
	 * G_LBL_012
	 */
	private String accountNo; // 7
	
	private int processingNo;
	private int processingYm;
	private int sparePayAtr;
	private String branchId;
}
