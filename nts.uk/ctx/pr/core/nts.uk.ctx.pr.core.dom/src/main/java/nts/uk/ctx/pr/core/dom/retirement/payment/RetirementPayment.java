package nts.uk.ctx.pr.core.dom.retirement.payment;


import javax.persistence.EnumType;

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
	private BankTransferOption bankTransferOption1;
	
	@Getter
	private PaymentMoney option1Money;
	
	@Getter
	private BankTransferOption bankTransferOption2;
	
	@Getter
	private PaymentMoney option2Money;
	
	@Getter
	private BankTransferOption bankTransferOption3;
	
	@Getter
	private PaymentMoney option3Money;
	
	@Getter
	private BankTransferOption bankTransferOption4;
	
	@Getter
	private PaymentMoney option4Money;
	
	@Getter
	private BankTransferOption bankTransferOption5;
	
	@Getter
	private PaymentMoney option5Money;
	
	@Getter
	private Memo withholdingMeno;
	
	@Getter
	private Memo statementMemo;
	
	public RetirementPayment(CompanyCode companyCode, PersonId personId, GeneralDate payDate,
			TrialPeriodSet trialPeriodSet, PaymentYear exclusionYears, PaymentYear additionalBoardYears,
			PaymentYear boardYears, PaymentMoney totalPaymentMoney, PaymentMoney deduction1Money,
			PaymentMoney deduction2Money, PaymentMoney deduction3Money, RetirementPayOption retirementPayOption,
			TaxCalculationMethod taxCalculationMethod, PaymentMoney incomeTaxMoney, PaymentMoney cityTaxMoney,
			PaymentMoney prefectureTaxMoney, PaymentMoney totalDeclarationMoney, PaymentMoney actualRecieveMoney,
			BankTransferOption bankTransferOption1, PaymentMoney option1Money, BankTransferOption bankTransferOption2,
			PaymentMoney option2Money, BankTransferOption bankTransferOption3, PaymentMoney option3Money,
			BankTransferOption bankTransferOption4, PaymentMoney option4Money, BankTransferOption bankTransferOption5,
			PaymentMoney option5Money, Memo withholdingMeno, Memo statementMemo) {
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
		this.bankTransferOption1 = bankTransferOption1;
		this.option1Money = option1Money;
		this.bankTransferOption2 = bankTransferOption2;
		this.option2Money = option2Money;
		this.bankTransferOption3 = bankTransferOption3;
		this.option3Money = option3Money;
		this.bankTransferOption4 = bankTransferOption4;
		this.option4Money = option4Money;
		this.bankTransferOption5 = bankTransferOption5;
		this.option5Money = option5Money;
		this.withholdingMeno = withholdingMeno;
		this.statementMemo = statementMemo;
	}
	
	public static RetirementPayment createFromJavaType(String companyCode, String personId, GeneralDate payDate,
			int trialPeriodSet, int exclusionYears, int additionalBoardYears,
			int boardYears, int totalPaymentMoney, int deduction1Money,
			int deduction2Money, int deduction3Money, int retirementPayOption,
			int taxCalculationMethod, int incomeTaxMoney, int cityTaxMoney,
			int prefectureTaxMoney, int totalDeclarationMoney, int actualRecieveMoney,
			int bankTransferOption1, int option1Money, int bankTransferOption2,
			int option2Money, int bankTransferOption3, int option3Money,
			int bankTransferOption4, int option4Money, int bankTransferOption5,
			int option5Money, String withholdingMeno, String statementMemo) {
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
				EnumAdaptor.valueOf(bankTransferOption1, BankTransferOption.class),
				new PaymentMoney(option1Money),
				EnumAdaptor.valueOf(bankTransferOption2, BankTransferOption.class),
				new PaymentMoney(option2Money),
				EnumAdaptor.valueOf(bankTransferOption3, BankTransferOption.class),
				new PaymentMoney(option3Money),
				EnumAdaptor.valueOf(bankTransferOption4, BankTransferOption.class),
				new PaymentMoney(option4Money),
				EnumAdaptor.valueOf(bankTransferOption5, BankTransferOption.class),
				new PaymentMoney(option5Money),
				new Memo(withholdingMeno),
				new Memo(statementMemo));
	}
}
