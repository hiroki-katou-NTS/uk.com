package nts.uk.ctx.pr.core.infra.entity.retirement.payment;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
/**
 * 
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Table(name="QREDT_RETIREMENT_PAYMENT")
public class QredtRetirementPayment {
	@EmbeddedId
	public QredtRetirementPaymentPK qredtRetirementPaymentPK;
	@Column(name="ACTUAL_RECIEVE_MNY")
	public int actualRecieveMoney;
	@Column(name="ADDITIONAL_BOARD_YEARS")
	public int additionalBoardYears;
	@Column(name="BOARD_YEARS")
	public int boardYears;
	@Column(name="CITY_TAX_MNY")
	public int cityTaxMoney;
	@Column(name="DEDUCTION1_MNY")
	public int deduction1Money;
	@Column(name="DEDUCTION2_MNY")
	public int deduction2Money;
	@Column(name="DEDUCTION3_MNY")
	public int deduction3Money;
	@Column(name="EXCLUSION_YEARS")
	public int exclusionYears;
	@Column(name="INCOME_TAX_MNY")
	public int incomeTaxMoney;
	@Column(name="MEMO")
	public String memo;
	@Column(name="PREFECTURE_TAX_MNY")
	public int prefectureTaxMoney;
	@Column(name="OTHER_RETIREMENT_PAY_OP")
	public int retirementPayOption;
	@Column(name="TAX_CAL_METHOD_SET")
	public int taxCalculationMethod;
	@Column(name="TOTAL_DEDUCTION_MNY")
	public int totalDeclarationMoney;
	@Column(name="TOTAL_PAYMENT_MNY")
	public int totalPaymentMoney;
	@Column(name="TRIAL_PERIOD_SET")
	public int trialPeriodSet;
}
