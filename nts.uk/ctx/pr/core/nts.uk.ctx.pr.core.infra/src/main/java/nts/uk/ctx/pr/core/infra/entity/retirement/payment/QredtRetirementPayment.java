package nts.uk.ctx.pr.core.infra.entity.retirement.payment;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.TableEntity;
/**
 * 
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Table(name="QREDT_RETIREMENT_PAYMENT")
@Entity
public class QredtRetirementPayment extends TableEntity {
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
	public int totalPaymentMoney;
	
	@Column(name="DEDUCTION1_MNY")
	public int deduction1Money;
	
	@Column(name="DEDUCTION2_MNY")
	public int deduction2Money;
	
	@Column(name="DEDUCTION3_MNY")
	public int deduction3Money;
	
	@Column(name="OTHER_RETIREMENT_PAY_OP")
	public int retirementPayOption;
	
	@Column(name="TAX_CAL_METHOD_SET")
	public int taxCalculationMethod;
	
	@Column(name="INCOME_TAX_MNY")
	public int incomeTaxMoney;
	
	@Column(name="CITY_TAX_MNY")
	public int cityTaxMoney;
	
	@Column(name="PREFECTURE_TAX_MNY")
	public int prefectureTaxMoney;
	
	@Column(name="TOTAL_DEDUCTION_MNY")
	public int totalDeclarationMoney;
	
	@Column(name="ACTUAL_RECIEVE_MNY")
	public int actualRecieveMoney;
	
	@Column(name="MEMO")
	public String memo;
}
