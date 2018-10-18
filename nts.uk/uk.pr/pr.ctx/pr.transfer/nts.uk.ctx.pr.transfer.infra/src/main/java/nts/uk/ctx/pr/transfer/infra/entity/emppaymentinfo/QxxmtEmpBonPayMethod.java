package nts.uk.ctx.pr.transfer.infra.entity.emppaymentinfo;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.NoArgsConstructor;
import nts.uk.ctx.pr.transfer.dom.emppaymentinfo.EmployeeBonusPaymentMethod;
import nts.uk.ctx.pr.transfer.dom.emppaymentinfo.PaymentMethodDetail;
import nts.uk.ctx.pr.transfer.dom.emppaymentinfo.TransferInfor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * 
 * @author HungTT - 社員給与支払方法
 *
 */

@NoArgsConstructor
@Entity
@Table(name = "QXXMT_EMP_BON_PAY_METHOD")
public class QxxmtEmpBonPayMethod extends UkJpaEntity {

	@EmbeddedId
	public QxxmtEmpPayMethodPk pk;

	@Column(name = "INDIVIDUAL_SETTING_ATR")
	public int individualSettingAtr;

	@Column(name = "USE_ATR")
	public Integer useAtr;

	@Column(name = "PAY_PROPORTION_ATR")
	@Basic(optional = true)
	public Integer paymentProportionAtr;

	@Column(name = "PAY_METHOD")
	@Basic(optional = true)
	public Integer paymentMethod;

	@Column(name = "PAY_RATE")
	@Basic(optional = true)
	public Integer paymentRate;

	@Column(name = "PAY_AMOUNT")
	@Basic(optional = true)
	public Long paymentAmount;

	@Column(name = "PAY_PRIORITY")
	@Basic(optional = true)
	public Integer paymentPriority;

	@Column(name = "SRC_BANK_CD")
	@Basic(optional = true)
	public String sourceBankCode;

	@Column(name = "DES_ACCOUNT_NUMBER")
	@Basic(optional = true)
	public String desAccountNumber;

	@Column(name = "DES_ACCOUNT_NAME")
	@Basic(optional = true)
	public String desAccountName;

	@Column(name = "DES_ACCOUNT_KANA_NAME")
	@Basic(optional = true)
	public String desAccountKanaName;

	@Column(name = "DES_BANK_BRANCH_ID")
	@Basic(optional = true)
	public String desBranchId;

	@Column(name = "DES_DEPOSIT_TYPE")
	@Basic(optional = true)
	public Integer desDepositType;

	@Override
	protected Object getKey() {
		return this.pk;
	}

	public QxxmtEmpBonPayMethod(String historyId, int paymentMethodNo, int individualSettingAtr, Integer useAtr,
			Integer paymentProportionAtr, Integer paymentMethod, Integer paymentRate, Long paymentAmount,
			Integer paymentPriority, String sourceBankCode, String desAccountNumber, String desAccountName,
			String desAccountKanaName, String desBranchId, Integer desDepositType) {
		super();
		this.pk = new QxxmtEmpPayMethodPk(historyId, paymentMethodNo);
		this.individualSettingAtr = individualSettingAtr;
		this.useAtr = useAtr;
		this.paymentProportionAtr = paymentProportionAtr;
		this.paymentMethod = paymentMethod;
		this.paymentRate = paymentRate;
		this.paymentAmount = paymentAmount;
		this.paymentPriority = paymentPriority;
		this.sourceBankCode = sourceBankCode;
		this.desAccountNumber = desAccountNumber;
		this.desAccountName = desAccountName;
		this.desAccountKanaName = desAccountKanaName;
		this.desBranchId = desBranchId;
		this.desDepositType = desDepositType;
	}

	public QxxmtEmpBonPayMethod(String historyId, boolean individualSettingAtr,
			PaymentMethodDetail paymentMethodDetail) {
		super();
		if (individualSettingAtr) {
			this.pk = new QxxmtEmpPayMethodPk(historyId, paymentMethodDetail.getPaymentMethodNo());
			this.individualSettingAtr = 1;
			this.useAtr = paymentMethodDetail.getUseAtr().value ? 1 : 0;
			if (paymentMethodDetail.getUseAtr().value) {
				this.paymentMethod = paymentMethodDetail.getPaymentMethod().get().value ? 1 : 0;
				if (paymentMethodDetail.getPaymentMethod().get().value) {
					this.sourceBankCode = paymentMethodDetail.getTransferInfor().get().getSourceBankBranchInfor().v();
					this.desAccountNumber = paymentMethodDetail.getTransferInfor().get().getDesAccountNumber().v();
					this.desAccountName = paymentMethodDetail.getTransferInfor().get().getDesAccountName().v();
					this.desAccountKanaName = paymentMethodDetail.getTransferInfor().get().getDesAccountKanaName().v();
					this.desBranchId = paymentMethodDetail.getTransferInfor().get().getDesBankBranchInfor();
					this.desDepositType = paymentMethodDetail.getTransferInfor().get().getDesDepositType().value;
				}
				this.paymentProportionAtr = paymentMethodDetail.getPaymentProportionAtr().get().value;
				this.paymentPriority = paymentMethodDetail.getPaymentPriority().get().value;
				switch (paymentProportionAtr) {
				case 0: // 定率 - FIXED_RATE
					this.paymentRate = paymentMethodDetail.getPaymentRate().get().v();
					break;
				case 1: // 定額 - FIXED_AMOUNT
					this.paymentAmount = paymentMethodDetail.getPaymentAmount().get().v();
					break;
				default: // 全額 - FULL_AMOUNT
					break;
				}
			}
		} else {
			this.pk = new QxxmtEmpPayMethodPk(historyId, 0);
			this.individualSettingAtr = 0;
		}
	}

	public static EmployeeBonusPaymentMethod toDomain(List<QxxmtEmpBonPayMethod> listEntity) {
		if (listEntity.isEmpty())
			return null;
		if (listEntity.size() == 1 && listEntity.get(0).individualSettingAtr == 0) {
			return new EmployeeBonusPaymentMethod(listEntity.get(0).pk.historyId, false, Collections.emptyList());
		} else {
			List<PaymentMethodDetail> listPaymentMethod = listEntity.stream()
					.map(m -> new PaymentMethodDetail(m.useAtr == 1 ? true : false, m.pk.paymentMethodNo,
							m.paymentMethod == 1 ? new TransferInfor(m.sourceBankCode, m.desAccountKanaName,
									m.desAccountName, m.desAccountNumber, m.desBranchId, m.desDepositType) : null,
							m.paymentProportionAtr, m.paymentMethod == 1 ? true : false, m.paymentRate, m.paymentAmount,
							m.paymentPriority))
					.collect(Collectors.toList());
			return new EmployeeBonusPaymentMethod(listEntity.get(0).pk.historyId, true, listPaymentMethod);
		}
	}

	public static List<QxxmtEmpBonPayMethod> fromDomain(EmployeeBonusPaymentMethod domain) {
		if (domain.getSettingAtr().value) {
			return domain.getListPaymentMethod().get().stream()
					.map(m -> new QxxmtEmpBonPayMethod(domain.getHistoryId(), true, m)).collect(Collectors.toList());
		} else {
			return Arrays.asList(new QxxmtEmpBonPayMethod(domain.getHistoryId(), false, null));
		}
	}

}
