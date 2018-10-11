package nts.uk.ctx.pr.transfer.dom.emppaymentinfo;

import lombok.Getter;
import nts.uk.ctx.pr.transfer.dom.bank.AccountNumber;

/**
 * 
 * @author HungTT - 振込情報
 *
 */

@Getter
public class TransferInfor {

	/**
	 * 振込元銀行支店情報: 振込元銀行コード
	 */
	private String sourceBankBranchInfor;

	/**
	 * 振込先口座カナ名
	 */
	private AccountKanaName desAccountKanaName;

	/**
	 * 振込先口座名
	 */
	private AccountName desAccountName;

	/**
	 * 振込先口座番号
	 */
	private AccountNumber desAccountNumber;
	
	/**
	 * 振込先銀行支店情報: 銀行支店ID
	 */
	private String desBankBranchInfor;
	
	/**
	 * 振込先預金種別
	 */
	private AccountAtr desDepositType;

}
