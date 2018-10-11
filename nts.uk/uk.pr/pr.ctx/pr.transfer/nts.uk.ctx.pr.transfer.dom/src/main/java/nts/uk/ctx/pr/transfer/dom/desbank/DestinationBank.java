package nts.uk.ctx.pr.transfer.dom.desbank;

import java.util.List;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.pr.transfer.dom.bank.AccountNumber;
import nts.uk.ctx.pr.transfer.dom.emppaymentinfo.AccountAtr;
import nts.uk.shr.com.primitive.Memo;

/**
 * 
 * @author HungTT - 振込元銀行
 *
 */

@Getter
public class DestinationBank extends AggregateRoot {

	/**
	 * 会社ID
	 */
	private String companyId;

	/**
	 * コード
	 */
	private DesBankCode code;

	/**
	 * 名称
	 */
	private DesBankName name;

	/**
	 * 支店ID
	 */
	private String branchId;

	/**
	 * 口座番号
	 */
	private AccountNumber accountNumber;

	/**
	 * 口座区分
	 */
	private AccountAtr accountAtr;

	/**
	 * 委託者情報
	 */
	private List<EntrustorInfor> entrustorInfor;

	/**
	 * 振込依頼者名
	 */
	private TransferRequesterName transferRequesterName;

	/**
	 * メモ
	 */
	private Memo memo;

	public DestinationBank(String companyId, String code, String name, String branchId, String accountNumber,
			int accountAtr, List<EntrustorInfor> entrustorInfor, String transferRequesterName, String memo) {
		super();
		this.companyId = companyId;
		this.code = new DesBankCode(code);
		this.name = new DesBankName(name);
		this.branchId = branchId;
		this.accountNumber = new AccountNumber(accountNumber);
		this.accountAtr = AccountAtr.of(accountAtr);
		this.entrustorInfor = entrustorInfor;
		this.transferRequesterName = new TransferRequesterName(transferRequesterName);
		this.memo = new Memo(memo);
	}

}
