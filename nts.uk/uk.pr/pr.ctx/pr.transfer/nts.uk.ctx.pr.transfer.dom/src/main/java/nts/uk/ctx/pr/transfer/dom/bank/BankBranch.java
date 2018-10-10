package nts.uk.ctx.pr.transfer.dom.bank;

import java.util.Optional;

import lombok.Getter;
import nts.arc.error.BusinessException;
import nts.arc.layer.dom.AggregateRoot;
import nts.gul.text.IdentifierUtil;
import nts.gul.text.StringUtil;
import nts.uk.shr.com.primitive.Memo;

/**
 * 
 * @author HungTT - 銀行支店
 *
 */

@Getter
public class BankBranch extends AggregateRoot {
	/**
	 * 会社ID
	 */
	private String companyId;

	/**
	 * Branch id
	 */
	private String branchId;

	/**
	 * 銀行コード
	 */
	private BankCode bankCode;
	/**
	 * 支店コード
	 */
	private BankBranchCode bankBranchCode;
	/**
	 * 名称
	 */
	private BankBranchName bankBranchName;
	/**
	 * カナ名
	 */
	private Optional<BankBranchNameKana> bankBranchNameKana;
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
		if (this.bankBranchCode == null || StringUtil.isNullOrEmpty(this.bankBranchCode.v(), true)) {
			throw new BusinessException("ER001");
		}

		if (this.bankBranchName == null || StringUtil.isNullOrEmpty(this.bankBranchName.v(), true)) {
			throw new BusinessException("ER001");
		}
	}

	/**
	 * 
	 * @param companyCode
	 * @param branchId
	 * @param bankCode
	 * @param bankBranchCode
	 * @param bankBranchName
	 * @param bankBranchNameKana
	 * @param memo
	 */
	public BankBranch(String companyId, String branchId, BankCode bankCode, BankBranchCode bankBranchCode,
			BankBranchName bankBranchName, BankBranchNameKana bankBranchNameKana, Memo memo) {
		super();
		this.companyId = companyId;
		this.branchId = branchId;
		this.bankCode = bankCode;
		this.bankBranchCode = bankBranchCode;
		this.bankBranchName = bankBranchName;
		this.bankBranchNameKana = Optional.ofNullable(bankBranchNameKana);
		this.memo = Optional.ofNullable(memo);
	}

	/**
	 * Convert java type to domain
	 * 
	 * @param companyCode
	 * @param branchId
	 * @param bankCode
	 * @param bankBranchCode
	 * @param bankBranchName
	 * @param bankBranchNameKana
	 * @param memo
	 * @return
	 */
	public static BankBranch createFromJavaType(String companyId, String branchId, String bankCode,
			String bankBranchCode, String bankBranchName, String bankBranchNameKana, String memo) {
		return new BankBranch(companyId, branchId, new BankCode(bankCode), new BankBranchCode(bankBranchCode),
				new BankBranchName(bankBranchName), new BankBranchNameKana(bankBranchNameKana), new Memo(memo));
	}

	/**
	 * New mode: branch
	 * 
	 * @param companyCode
	 * @param bankCode
	 * @param bankBranchCode
	 * @param bankBranchName
	 * @param bankBranchNameKana
	 * @param memo
	 * @return
	 */
	public static BankBranch newBranch(String companyId, String bankCode, String bankBranchCode, String bankBranchName,
			String bankBranchNameKana, String memo) {
		String branchId = IdentifierUtil.randomUniqueId();
		return createFromJavaType(companyId, branchId.toString(), bankCode, bankBranchCode, bankBranchName,
				bankBranchNameKana, memo);
	}

}
