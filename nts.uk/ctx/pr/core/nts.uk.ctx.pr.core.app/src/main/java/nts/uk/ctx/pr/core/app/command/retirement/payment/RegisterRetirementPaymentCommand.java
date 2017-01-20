package nts.uk.ctx.pr.core.app.command.retirement.payment;



import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * 
 * @author Doan Duy Hung
 *
 */
@Data
@NoArgsConstructor
public class RegisterRetirementPaymentCommand {
	private String companyCode;
	private String personId;
	private int actualRecieveMoney;
	private int additionalBoardYears;
	private int boardYears;
	private int cityTaxMoney;
	private int deduction1Money;
	private int deduction2Money;
	private int deduction3Money;
	private int exclusionYears;
	private int incomeTaxMoney;
	private String memo;
	private int prefectureTaxMoney;
	private int retirementPayOption;
	private int taxCalculationMethod;
	private int totalDeclarationMoney;
	private int totalPaymentMoney;
	private int trialPeriodSet;
}
