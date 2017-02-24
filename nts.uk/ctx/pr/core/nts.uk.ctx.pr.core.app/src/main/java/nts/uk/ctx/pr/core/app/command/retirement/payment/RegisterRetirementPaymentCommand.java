package nts.uk.ctx.pr.core.app.command.retirement.payment;



import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
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
	private GeneralDate payDate;
	private int trialPeriodSet;
	private int exclusionYears;
	private int additionalBoardYears;
	private int boardYears;
	private int totalPaymentMoney;
	private int deduction1Money;
	private int deduction2Money;
	private int deduction3Money;
	private int retirementPayOption;
	private int taxCalculationMethod;
	private int incomeTaxMoney;
	private int cityTaxMoney;
	private int prefectureTaxMoney;
	private int totalDeclarationMoney;
	private int actualRecieveMoney;
	private String memo;
}
