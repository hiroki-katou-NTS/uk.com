package nts.uk.ctx.pr.transfer.infra.entity.bank;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.NoArgsConstructor;
import nts.uk.ctx.pr.transfer.dom.bank.Bank;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * 
 * @author HungTT - 銀行
 *
 */

@NoArgsConstructor
@Entity
@Table(name = "QXXMT_BANK")
public class QxxmtBank extends UkJpaEntity {

	@EmbeddedId
	public QxxmtBankPk pk;

	/**
	 * 名称
	 */
	@Column(name = "NAME")
	public String bankName;

	/**
	 * カナ名
	 */
	@Column(name = "KANA_NAME")
	@Basic(optional = true)
	public String bankNameKana;

	/**
	 * メモ
	 */
	@Column(name = "MEMO")
	@Basic(optional = true)
	public String memo;

	@Override
	protected Object getKey() {
		return this.pk;
	}

	public QxxmtBank(String companyId, String bankCode, String bankName, String bankNameKana, String memo) {
		super();
		this.pk = new QxxmtBankPk(companyId, bankCode);
		this.bankName = bankName;
		this.bankNameKana = bankNameKana;
		this.memo = memo;
	}

	public QxxmtBank(Bank domain) {
		super();
		this.pk = new QxxmtBankPk(domain.getCompanyId(), domain.getBankCode().v());
		this.bankName = domain.getBankName().v();
		this.bankNameKana = domain.getBankNameKana().isPresent() ? domain.getBankNameKana().get().v() : null;
		this.memo = domain.getMemo().isPresent() ? domain.getMemo().get().v() : null;
	}

	public Bank toDomain() {
		return Bank.createFromJavaType(this.pk.companyId, this.pk.bankCode, this.bankName, this.bankNameKana,
				this.memo);
	}

}
