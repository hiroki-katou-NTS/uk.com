package nts.uk.ctx.pr.core.dom.payment.banktranfer;

import lombok.Value;

@Value
public class BankInfo {
	private int accountAtr;
	private String accountNameKana;
	private String accountNo;
	private String bankCode;
	private String bankNameKana;
	private String branchCode;
	private String branchNameKana;
}
