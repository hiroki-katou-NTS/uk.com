package nts.uk.ctx.pr.core.infra.entity.retirement.payment;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;
/**
 * 
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Table(name="QREDT_RETIREMENT_PAYMENT")
@Entity
public class QredtRetirementPayment extends UkJpaEntity {
	@EmbeddedId
	public QredtRetirementPaymentPK qredtRetirementPaymentPK;
	
	@Column(name="TRIAL_PERIOD_SET")
	public int trialPeriodSet;
	
	@Column(name="EXCLUSION_YEARS")
	public int exclusionYears;
	
	@Column(name="ADDITIONAL_BOARD_YEARS")
	public int additionalBoardYears;
	
	@Column(name="BOARD_YEARS")
	public int boardYears;
	
	@Column(name="TOTAL_PAYMENT_MNY")
	public BigDecimal totalPaymentMoney;
	
	@Column(name="DEDUCTION1_MNY")
	public BigDecimal deduction1Money;
	
	@Column(name="DEDUCTION2_MNY")
	public BigDecimal deduction2Money;
	
	@Column(name="DEDUCTION3_MNY")
	public BigDecimal deduction3Money;
	
	@Column(name="OTHER_RETIREMENT_PAY_OP")
	public int retirementPayOption;
	
	@Column(name="TAX_CAL_METHOD_SET")
	public int taxCalculationMethod;
	
	@Column(name="INCOME_TAX_MNY")
	public BigDecimal incomeTaxMoney;
	
	@Column(name="CITY_TAX_MNY")
	public BigDecimal cityTaxMoney;
	
	@Column(name="PREFECTURE_TAX_MNY")
	public BigDecimal prefectureTaxMoney;
	
	@Column(name="TOTAL_DEDUCTION_MNY")
	public BigDecimal totalDeclarationMoney;
	
	@Column(name="ACTUAL_RECIEVE_MNY")
	public BigDecimal actualRecieveMoney;
	
	@Column(name="BANK_TRANSFER_OP1")
	public int bankTransferOption1;
	
	@Column(name="OP1_MNY")
	public BigDecimal option1Money;
	
	@Column(name="BANK_TRANSFER_OP2")
	public int bankTransferOption2;
	
	@Column(name="OP2_MNY")
	public BigDecimal option2Money;
	
	@Column(name="BANK_TRANSFER_OP3")
	public int bankTransferOption3;
	
	@Column(name="OP3_MNY")
	public BigDecimal option3Money;
	
	@Column(name="BANK_TRANSFER_OP4")
	public int bankTransferOption4;
	
	@Column(name="OP4_MNY")
	public BigDecimal option4Money;
	
	@Column(name="BANK_TRANSFER_OP5")
	public int bankTransferOption5;
	
	@Column(name="OP5_MNY")
	public BigDecimal option5Money;
	
	@Column(name="WITHHOLDING_MENO")
	public String withholdingMeno;
	
	@Column(name="STATEMENT_MEMO")
	public String statementMemo;

	@Override
	protected Object getKey() {
		return qredtRetirementPaymentPK;
	}
}
