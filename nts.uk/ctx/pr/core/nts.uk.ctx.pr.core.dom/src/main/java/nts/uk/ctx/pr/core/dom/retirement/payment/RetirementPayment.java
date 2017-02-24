package nts.uk.ctx.pr.core.dom.retirement.payment;


import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
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
	private CompanyCode companyCode;
	
	@Getter
	private PersonId personId;
	
	@Getter
	private GeneralDate payDate;
	
	@Getter
	private TrialPeriodSet trialPeriodSet;
	
	@Getter
	private PaymentYear exclusionYears;
	
	@Getter
	private PaymentYear additionalBoardYears;
	
	@Getter
	private PaymentYear boardYears;
	
	@Getter
	private PaymentMoney totalPaymentMoney;
	
	@Getter
	private PaymentMoney deduction1Money;
	
	@Getter
	private PaymentMoney deduction2Money;
	
	@Getter
	private PaymentMoney deduction3Money;
	
	@Getter
	private RetirementPayOption retirementPayOption;
	
	@Getter
	private TaxCalculationMethod taxCalculationMethod;
	
	@Getter
	private PaymentMoney incomeTaxMoney;
	
	@Getter
	private PaymentMoney cityTaxMoney;
	
	@Getter
	private PaymentMoney prefectureTaxMoney;
	
	@Getter
	private PaymentMoney totalDeclarationMoney;
	
	@Getter
	private PaymentMoney actualRecieveMoney;
	
	@Getter
	private Memo memo;
	
	public RetirementPayment(CompanyCode companyCode, PersonId personId, GeneralDate payDate,
			TrialPeriodSet trialPeriodSet, PaymentYear exclusionYears, PaymentYear additionalBoardYears,
			PaymentYear boardYears, PaymentMoney totalPaymentMoney, PaymentMoney deduction1Money,
			PaymentMoney deduction2Money, PaymentMoney deduction3Money, RetirementPayOption retirementPayOption,
			TaxCalculationMethod taxCalculationMethod, PaymentMoney incomeTaxMoney, PaymentMoney cityTaxMoney,
			PaymentMoney prefectureTaxMoney, PaymentMoney totalDeclarationMoney, PaymentMoney actualRecieveMoney,
			Memo memo) {
		super();
		this.companyCode = companyCode;
		this.personId = personId;
		this.payDate = payDate;
		this.trialPeriodSet = trialPeriodSet;
		this.exclusionYears = exclusionYears;
		this.additionalBoardYears = additionalBoardYears;
		this.boardYears = boardYears;
		this.totalPaymentMoney = totalPaymentMoney;
		this.deduction1Money = deduction1Money;
		this.deduction2Money = deduction2Money;
		this.deduction3Money = deduction3Money;
		this.retirementPayOption = retirementPayOption;
		this.taxCalculationMethod = taxCalculationMethod;
		this.incomeTaxMoney = incomeTaxMoney;
		this.cityTaxMoney = cityTaxMoney;
		this.prefectureTaxMoney = prefectureTaxMoney;
		this.totalDeclarationMoney = totalDeclarationMoney;
		this.actualRecieveMoney = actualRecieveMoney;
		this.memo = memo;
	}
	
	public static RetirementPayment createFromJavaType(String companyCode, String personId, GeneralDate payDate,
			int trialPeriodSet, int exclusionYears, int additionalBoardYears,
			int boardYears, int totalPaymentMoney, int deduction1Money,
			int deduction2Money, int deduction3Money, int retirementPayOption,
			int taxCalculationMethod, int incomeTaxMoney, int cityTaxMoney,
			int prefectureTaxMoney, int totalDeclarationMoney, int actualRecieveMoney,
			String memo) {
		return new RetirementPayment(
				new CompanyCode(companyCode), 
				new PersonId(personId), 
				payDate,
				EnumAdaptor.valueOf(trialPeriodSet, TrialPeriodSet.class),
				new PaymentYear(exclusionYears),
				new PaymentYear(additionalBoardYears), 
				new PaymentYear(boardYears), 
				new PaymentMoney(totalPaymentMoney),
				new PaymentMoney(deduction1Money), 
				new PaymentMoney(deduction2Money), 
				new PaymentMoney(deduction3Money), 
				EnumAdaptor.valueOf(retirementPayOption, RetirementPayOption.class), 
				EnumAdaptor.valueOf(taxCalculationMethod, TaxCalculationMethod.class), 
				new PaymentMoney(incomeTaxMoney),
				new PaymentMoney(cityTaxMoney), 
				new PaymentMoney(prefectureTaxMoney), 
				new PaymentMoney(totalDeclarationMoney), 
				new PaymentMoney(actualRecieveMoney), 
				new Memo(memo));
	}
}
