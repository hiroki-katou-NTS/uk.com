package nts.uk.ctx.pr.core.dom.payment.banktransfer;

import lombok.Value;

@Value
public class BankInfo {
	private String branchId;
	private String bankNameKana;
	private String branchNameKana;
	private int accountAtr;
	private String accountNo;
	private String accountNameKana;
}
