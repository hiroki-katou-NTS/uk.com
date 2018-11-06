package nts.uk.ctx.pr.transfer.dom.sourcebank;

import java.util.List;
import java.util.Optional;

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
public class TransferSourceBank extends AggregateRoot {

	/**
	 * 会社ID
	 */
	private String companyId;

	/**
	 * コード
	 */
	private SourceBankCode code;

	/**
	 * 名称
	 */
	private SourceBankName name;

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
	private Optional<List<EntrustorInfor>> entrustorInfor;

	/**
	 * 振込依頼者名
	 */
	private Optional<TransferRequesterName> transferRequesterName;

	/**
	 * メモ
	 */
	private Optional<Memo> memo;

	public TransferSourceBank(String companyId, String code, String name, String branchId, String accountNumber,
			int accountAtr, List<EntrustorInfor> entrustorInfor, String transferRequesterName, String memo) {
		super();
		this.companyId = companyId;
		this.code = new SourceBankCode(code);
		this.name = new SourceBankName(name);
		this.branchId = branchId;
		this.accountNumber = new AccountNumber(accountNumber);
		this.accountAtr = AccountAtr.of(accountAtr);
		this.entrustorInfor = entrustorInfor.isEmpty() ? Optional.empty() : Optional.of(entrustorInfor);
		this.transferRequesterName = transferRequesterName == null ? Optional.empty() : Optional.of(new TransferRequesterName(transferRequesterName));
		this.memo = memo == null ? Optional.empty() : Optional.of(new Memo(memo));
	}

}
