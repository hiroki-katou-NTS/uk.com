package nts.uk.ctx.pr.core.app.find.retirement.payment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 
 * @author Doan Duy Hung
 *
 */
@Data
@AllArgsConstructor
public class RetirementPaymentDto {
	private int actualRecieveMoney;
	private int additionalBoardYears;
	private int boardYears;
	private int cityTaxMoney;
	private String companyCode;
	private int deduction1Money;
	private int deduction2Money;
	private int deduction3Money;
	private int exclusionYears;
	private int incomeTaxMoney;
	private String memo;
	private String personId;
	private int prefectureTaxMoney;
	private int retirementPayOption;
	private int taxCalculationMethod;
	private int totalDeclarationMoney;
	private int totalPaymentMoney;
	private int trialPeriodSet;

}
