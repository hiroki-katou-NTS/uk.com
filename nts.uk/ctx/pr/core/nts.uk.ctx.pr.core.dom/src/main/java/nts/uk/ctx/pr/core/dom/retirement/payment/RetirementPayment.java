package nts.uk.ctx.pr.core.dom.retirement.payment;


import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.shr.com.primitive.Memo;
import nts.uk.shr.com.primitive.PersonId;

/**
 * 退職金明細データ
 * @author Doan Duy Hung
 *
 */

public class RetirementPayment extends AggregateRoot {
	@Getter
	private PaymentMoney actualRecieveMoney;
	@Getter
	private PaymentYear additionalBoardYears;
	@Getter
	private PaymentYear boardYears;
	@Getter
	private PaymentMoney cityTaxMoney;
	@Getter
	private CompanyCode companyCode;
	@Getter
	private PaymentMoney deduction1Money;
	@Getter
	private PaymentMoney deduction2Money;
	@Getter
	private PaymentMoney deduction3Money;
	@Getter
	private PaymentYear exclusionYears;
	@Getter
	private PaymentMoney incomeTaxMoney;
	@Getter
	private Memo memo;
	/*private ? payDate;*/
	@Getter
	private PersonId personId;
	@Getter
	private PaymentMoney prefectureTaxMoney;
	@Getter
	private RetirementPayOption retirementPayOption;
	@Getter
	private TaxCalculationMethod taxCalculationMethod;
	@Getter
	private PaymentMoney totalDeclarationMoney;
	@Getter
	private PaymentMoney totalPaymentMoney;
	@Getter
	private TrialPeriodSet trialPeriodSet;

	public RetirementPayment(PaymentMoney actualRecieveMoney, PaymentYear additionalBoardYears, PaymentYear boardYears,
			PaymentMoney cityTaxMoney, CompanyCode companyCode, PaymentMoney deduction1Money,
			PaymentMoney deduction2Money, PaymentMoney deduction3Money, PaymentYear exclusionYears,
			PaymentMoney incomeTaxMoney, Memo memo, PersonId personId, PaymentMoney prefectureTaxMoney,
			RetirementPayOption retirementPayOption, TaxCalculationMethod taxCalculationMethod,
			PaymentMoney totalDeclarationMoney, PaymentMoney totalPaymentMoney, TrialPeriodSet trialPeriodSet) {
		super();
		this.actualRecieveMoney = actualRecieveMoney;
		this.additionalBoardYears = additionalBoardYears;
		this.boardYears = boardYears;
		this.cityTaxMoney = cityTaxMoney;
		this.companyCode = companyCode;
		this.deduction1Money = deduction1Money;
		this.deduction2Money = deduction2Money;
		this.deduction3Money = deduction3Money;
		this.exclusionYears = exclusionYears;
		this.incomeTaxMoney = incomeTaxMoney;
		this.memo = memo;
		this.personId = personId;
		this.prefectureTaxMoney = prefectureTaxMoney;
		this.retirementPayOption = retirementPayOption;
		this.taxCalculationMethod = taxCalculationMethod;
		this.totalDeclarationMoney = totalDeclarationMoney;
		this.totalPaymentMoney = totalPaymentMoney;
		this.trialPeriodSet = trialPeriodSet;
	}
	
	public static RetirementPayment createFromJavaType(int actualRecieveMoney, int additionalBoardYears, int boardYears,
			int cityTaxMoney, String companyCode, int deduction1Money,
			int deduction2Money, int deduction3Money, int exclusionYears,
			int incomeTaxMoney, String memo, String personId, int prefectureTaxMoney,
			int retirementPayOption, int taxCalculationMethod,
			int totalDeclarationMoney, int totalPaymentMoney, int trialPeriodSet) {
		return new RetirementPayment(
				new PaymentMoney(actualRecieveMoney), 
				new PaymentYear(additionalBoardYears), 
				new PaymentYear(boardYears), 
				new PaymentMoney(cityTaxMoney), 
				new CompanyCode(companyCode), 
				new PaymentMoney(deduction1Money), 
				new PaymentMoney(deduction2Money), 
				new PaymentMoney(deduction3Money), 
				new PaymentYear(exclusionYears), 
				new PaymentMoney(incomeTaxMoney), 
				new Memo(memo), 
				new PersonId(personId), 
				new PaymentMoney(prefectureTaxMoney), 
				EnumAdaptor.valueOf(retirementPayOption, RetirementPayOption.class), 
				EnumAdaptor.valueOf(taxCalculationMethod, TaxCalculationMethod.class), 
				new PaymentMoney(totalDeclarationMoney), 
				new PaymentMoney(totalPaymentMoney), 
				EnumAdaptor.valueOf(trialPeriodSet, TrialPeriodSet.class));
	}
}
