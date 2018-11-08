package nts.uk.ctx.pr.transfer.dom.bank;

import java.util.Optional;

import lombok.Getter;
import nts.arc.error.BusinessException;
import nts.arc.layer.dom.AggregateRoot;
import nts.gul.text.StringUtil;
import nts.uk.shr.com.primitive.Memo;

/**
 * 
 * @author HungTT - 銀行
 *
 */

@Getter
public class Bank extends AggregateRoot {
	/**
	 * 会社ID
	 */
	private String companyId;

	/**
	 * コード
	 */
	private BankCode bankCode;

	/**
	 * 名称
	 */
	private BankName bankName;

	/**
	 * カナ名
	 */
	private Optional<BankNameKana> bankNameKana;

	/**
	 * メモ
	 */
	private Optional<Memo> memo;

	/**
	 * Check validate data
	 */
	@Override
	public void validate() {
		super.validate();
		if (this.bankCode == null || StringUtil.isNullOrEmpty(this.bankCode.v(), true)) {
			throw new BusinessException("ER001");
		}

		if (this.bankName == null || StringUtil.isNullOrEmpty(this.bankName.v(), true)) {
			throw new BusinessException("ER001");
		}
	}

	/**
	 * 
	 * @param companyCode
	 * @param bankCode
	 * @param bankName
	 * @param bankNameKana
	 * @param memo
	 */
	public Bank(String companyId, BankCode bankCode, BankName bankName, BankNameKana bankNameKana, Memo memo) {
		super();
		this.companyId = companyId;
		this.bankCode = bankCode;
		this.bankName = bankName;
		this.bankNameKana = Optional.ofNullable(bankNameKana);
		this.memo = Optional.ofNullable(memo);
	}

	/**
	 * Convert java type to domain
	 * 
	 * @param companyCode
	 *            company code
	 * @param bankCode
	 *            bank code
	 * @param bankName
	 *            bank name
	 * @param bankNamKana
	 *            bank name katakana
	 * @param memo
	 *            memo
	 * @return Bank
	 */
	public static Bank createFromJavaType(String companyCode, String bankCode, String bankName, String bankNameKana,
			String memo) {
		return new Bank(companyCode, new BankCode(bankCode), new BankName(bankName), new BankNameKana(bankNameKana),
				new Memo(memo));
	}
}
