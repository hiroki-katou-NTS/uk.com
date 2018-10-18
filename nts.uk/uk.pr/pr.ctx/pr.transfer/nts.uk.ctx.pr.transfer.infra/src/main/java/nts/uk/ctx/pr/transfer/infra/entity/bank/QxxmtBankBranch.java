package nts.uk.ctx.pr.transfer.infra.entity.bank;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.NoArgsConstructor;
import nts.uk.ctx.pr.transfer.dom.bank.BankBranch;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * 
 * @author HungTT - 銀行支店
 *
 */

@NoArgsConstructor
@Entity
@Table(name = "CBKMT_BANK_BRANCH")
public class QxxmtBankBranch extends UkJpaEntity {

	/**
	 * Branch id
	 */
	@Id
	@Column(name = "ID")
	public String branchId;

	/**
	 * 会社ID
	 */
	@Column(name = "CID")
	public String companyId;

	/**
	 * 銀行コード
	 */
	@Column(name = "BANK_CD")
	public String bankCode;
	/**
	 * 支店コード
	 */
	@Column(name = "CD")
	public String bankBranchCode;
	/**
	 * 名称
	 */
	@Column(name = "NAME")
	public String bankBranchName;
	/**
	 * カナ名
	 */
	@Column(name = "KANA_NAME")
	@Basic(optional = true)
	public String bankBranchNameKana;
	/**
	 * メモ
	 */
	@Column(name = "MEMO")
	@Basic(optional = true)
	public String memo;

	@Override
	protected Object getKey() {
		return this.branchId;
	}

	public QxxmtBankBranch(String branchId, String companyId, String bankCode, String bankBranchCode,
			String bankBranchName, String bankBranchNameKana, String memo) {
		super();
		this.branchId = branchId;
		this.companyId = companyId;
		this.bankCode = bankCode;
		this.bankBranchCode = bankBranchCode;
		this.bankBranchName = bankBranchName;
		this.bankBranchNameKana = bankBranchNameKana;
		this.memo = memo;
	}

	public QxxmtBankBranch(BankBranch domain) {
		super();
		this.branchId = domain.getBranchId();
		this.companyId = domain.getCompanyId();
		this.bankCode = domain.getBankCode().v();
		this.bankBranchCode = domain.getBankBranchCode().v();
		this.bankBranchName = domain.getBankBranchName().v();
		this.bankBranchNameKana = domain.getBankBranchNameKana().isPresent() ? domain.getBankBranchNameKana().get().v()
				: null;
		this.memo = domain.getMemo().isPresent() ? domain.getMemo().get().v() : null;
	}

	public BankBranch toDomain() {
		return BankBranch.createFromJavaType(companyId, branchId, bankCode, bankBranchCode, bankBranchName,
				bankBranchNameKana, memo);
	}

}
