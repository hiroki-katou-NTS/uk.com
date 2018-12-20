package nts.uk.ctx.pr.transfer.infra.entity.sourcebank;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.NoArgsConstructor;
import nts.uk.ctx.pr.transfer.dom.sourcebank.EntrustorInfor;
import nts.uk.ctx.pr.transfer.dom.sourcebank.TransferSourceBank;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * 
 * @author HungTT - 振込元銀行
 *
 */

@NoArgsConstructor
@Entity
@Table(name = "QBTMT_TRF_SRC_BANK")
public class QbtmtTrfSrcBank extends UkJpaEntity {

	@EmbeddedId
	public QbtmtTrfSrcBankPk pk;

	/**
	 * 名称
	 */
	@Column(name = "NAME")
	public String name;

	/**
	 * 支店ID
	 */
	@Column(name = "BRANCH_ID")
	public String branchId;

	/**
	 * 口座番号
	 */
	@Column(name = "ACCOUNT_NUM")
	public String accountNumber;

	/**
	 * 口座区分
	 */
	@Column(name = "ACCOUNT_ATR")
	public int accountAtr;

	/**
	 * 振込依頼者名
	 */
	@Column(name = "TRF_REQ_NAME")
	@Basic(optional = true)
	public String transferRequesterName;

	/**
	 * メモ
	 */
	@Column(name = "MEMO")
	@Basic(optional = true)
	public String memo;

	@Column(name = "ENTRUSTOR_CD_1")
	@Basic(optional = true)
	public String entrustorCode1;

	@Column(name = "ENTRUSTOR_USE_1")
	@Basic(optional = true)
	public String entrustorUse1;

	@Column(name = "ENTRUSTOR_CD_2")
	@Basic(optional = true)
	public String entrustorCode2;

	@Column(name = "ENTRUSTOR_USE_2")
	@Basic(optional = true)
	public String entrustorUse2;

	@Column(name = "ENTRUSTOR_CD_3")
	@Basic(optional = true)
	public String entrustorCode3;

	@Column(name = "ENTRUSTOR_USE_3")
	@Basic(optional = true)
	public String entrustorUse3;

	@Column(name = "ENTRUSTOR_CD_4")
	@Basic(optional = true)
	public String entrustorCode4;

	@Column(name = "ENTRUSTOR_USE_4")
	@Basic(optional = true)
	public String entrustorUse4;

	@Column(name = "ENTRUSTOR_CD_5")
	@Basic(optional = true)
	public String entrustorCode5;

	@Column(name = "ENTRUSTOR_USE_5")
	@Basic(optional = true)
	public String entrustorUse5;

	@Override
	protected Object getKey() {
		return this.pk;
	}

	public TransferSourceBank toDomain() {
		List<EntrustorInfor> entrustorInfor = new ArrayList<>();
		if (this.entrustorCode1 != null || this.entrustorUse1 != null)
			entrustorInfor.add(new EntrustorInfor(this.entrustorCode1, 1, this.entrustorUse1));
		if (this.entrustorCode2 != null || this.entrustorUse2 != null)
			entrustorInfor.add(new EntrustorInfor(this.entrustorCode2, 2, this.entrustorUse2));
		if (this.entrustorCode3 != null || this.entrustorUse3 != null)
			entrustorInfor.add(new EntrustorInfor(this.entrustorCode3, 3, this.entrustorUse3));
		if (this.entrustorCode4 != null || this.entrustorUse4 != null)
			entrustorInfor.add(new EntrustorInfor(this.entrustorCode4, 4, this.entrustorUse4));
		if (this.entrustorCode5 != null || this.entrustorUse5 != null)
			entrustorInfor.add(new EntrustorInfor(this.entrustorCode5, 5, this.entrustorUse5));
		return new TransferSourceBank(this.pk.companyId, this.pk.code, this.name, this.branchId, this.accountNumber,
				this.accountAtr, entrustorInfor, this.transferRequesterName, this.memo);
	}

	public QbtmtTrfSrcBank(TransferSourceBank domain) {
		this.pk = new QbtmtTrfSrcBankPk(domain.getCompanyId(), domain.getCode().v());
		this.name = domain.getName().v();
		this.branchId = domain.getBranchId();
		this.accountAtr = domain.getAccountAtr().value;
		this.accountNumber = domain.getAccountNumber().v();
		this.transferRequesterName = domain.getTransferRequesterName().isPresent()
				? domain.getTransferRequesterName().get().v() : null;
		this.memo = domain.getMemo().isPresent() ? domain.getMemo().get().v() : null;
		List<EntrustorInfor> entrustorInfors = domain.getEntrustorInfor().orElse(Collections.emptyList());
		for (EntrustorInfor infor : entrustorInfors) {
			switch (infor.getEntrustorNo()) {
			case 1:
				this.entrustorCode1 = infor.getCode().v();
				this.entrustorUse1 = infor.getUse().v();
				break;
			case 2:
				this.entrustorCode2 = infor.getCode().v();
				this.entrustorUse2 = infor.getUse().v();
				break;
			case 3:
				this.entrustorCode3 = infor.getCode().v();
				this.entrustorUse3 = infor.getUse().v();
				break;
			case 4:
				this.entrustorCode4 = infor.getCode().v();
				this.entrustorUse4 = infor.getUse().v();
				break;
			default:
				this.entrustorCode5 = infor.getCode().v();
				this.entrustorUse5 = infor.getUse().v();
				break;
			}
		}
	}

}
