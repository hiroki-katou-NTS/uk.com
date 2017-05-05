package nts.uk.pr.file.infra.retirementpayment.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * @author hungnm
 *
 */
@Setter
@Getter
@Entity
@Table(name = "QREDT_RETIREMENT_PAYMENT")
public class ReportQredtRetirementPayment extends UkJpaEntity implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	protected ReportQredtRetirementPaymentPK reportQredtRetirementPaymentPK;

	@Basic(optional = false)
	@Column(name = "TRIAL_PERIOD_SET")
	private BigDecimal trialPeriodSet;

	@Basic(optional = false)
	@Column(name = "EXCLUSION_YEARS")
	private BigDecimal exclusionYears;

	@Basic(optional = false)
	@Column(name = "ADDITIONAL_BOARD_YEARS")
	private BigDecimal additionalBoardYears;

	@Basic(optional = false)
	@Column(name = "BOARD_YEARS")
	private BigDecimal boardYears;

	@Basic(optional = false)
	@Column(name = "TOTAL_PAYMENT_MNY")
	private BigDecimal totalPaymentMoney;

	@Basic(optional = false)
	@Column(name = "DEDUCTION1_MNY")
	private BigDecimal deductionMoney1;

	@Basic(optional = false)
	@Column(name = "DEDUCTION2_MNY")
	private BigDecimal deductionMoney2;

	@Basic(optional = false)
	@Column(name = "DEDUCTION3_MNY")
	private BigDecimal deductionMoney3;

	@Basic(optional = false)
	@Column(name = "OTHER_RETIREMENT_PAY_OP")
	private BigDecimal otherRetirementPayOptional;

	@Basic(optional = false)
	@Column(name = "TAX_CAL_METHOD_SET")
	private BigDecimal taxCalMethodSet;

	@Basic(optional = false)
	@Column(name = "INCOME_TAX_MNY")
	private BigDecimal incomeTaxMoney;

	@Basic(optional = false)
	@Column(name = "CITY_TAX_MNY")
	private BigDecimal cityTaxMoney;

	@Basic(optional = false)
	@Column(name = "PREFECTURE_TAX_MNY")
	private BigDecimal prefectureTaxMoney;

	@Basic(optional = false)
	@Column(name = "TOTAL_DEDUCTION_MNY")
	private BigDecimal totalDeductionMoney;

	@Basic(optional = false)
	@Column(name = "ACTUAL_RECIEVE_MNY")
	private BigDecimal actualRecieveMoney;

	@Basic(optional = false)
	@Column(name = "BANK_TRANSFER_OP1")
	private BigDecimal bankTransferOptional1;

	@Basic(optional = true)
	@Column(name = "OP1_MNY")
	private BigDecimal optionalMoney1;

	@Basic(optional = false)
	@Column(name = "BANK_TRANSFER_OP2")
	private BigDecimal bankTransferOptional2;

	@Basic(optional = true)
	@Column(name = "OP2_MNY")
	private BigDecimal optionalMoney2;

	@Basic(optional = false)
	@Column(name = "BANK_TRANSFER_OP3")
	private BigDecimal bankTransferOptional3;

	@Basic(optional = true)
	@Column(name = "OP3_MNY")
	private BigDecimal optionalMoney3;

	@Basic(optional = false)
	@Column(name = "BANK_TRANSFER_OP4")
	private BigDecimal bankTransferOptional4;

	@Basic(optional = true)
	@Column(name = "OP4_MNY")
	private BigDecimal optionalMoney4;

	@Basic(optional = false)
	@Column(name = "BANK_TRANSFER_OP5")
	private BigDecimal bankTransferOptional5;

	@Basic(optional = true)
	@Column(name = "OP5_MNY")
	private BigDecimal optionalMoney5;

	@Basic(optional = true)
	@Column(name = "WITHHOLDING_MENO")
	private String withHoldingMemo;

	@Basic(optional = true)
	@Column(name = "STATEMENT_MEMO")
	private String statementMemo;
	
	public ReportQredtRetirementPayment() {
		super();
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#getKey()
	 */
	@Override
	protected Object getKey() {
		// TODO Auto-generated method stub
		return this.reportQredtRetirementPaymentPK;
	}



	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((reportQredtRetirementPaymentPK == null) ? 0 : reportQredtRetirementPaymentPK.hashCode());
		return result;
	}



	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		ReportQredtRetirementPayment other = (ReportQredtRetirementPayment) obj;
		if (reportQredtRetirementPaymentPK == null) {
			if (other.reportQredtRetirementPaymentPK != null)
				return false;
		} else if (!reportQredtRetirementPaymentPK.equals(other.reportQredtRetirementPaymentPK))
			return false;
		return true;
	}

}
