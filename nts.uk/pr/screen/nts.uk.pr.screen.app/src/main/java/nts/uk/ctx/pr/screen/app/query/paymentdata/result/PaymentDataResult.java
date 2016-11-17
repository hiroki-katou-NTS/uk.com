package nts.uk.ctx.pr.screen.app.query.paymentdata.result;

import java.time.LocalDate;

import lombok.Data;

@Data
public class PaymentDataResult {
	
	private  String companyCode;
	
	private  String personId;

	
	private  int processingNo;

	
	private  int payBonusAtr;

	
	private  int processingYM;

	
	private int sparePayAtr;

	
	private LocalDate standardDate;

	
	private String specificationCode;

	
	private String residenceCode;

	
	private String residenceName;

	
	private int healthInsuranceGrade;

	
	private int healthInsuranceAverageEarn;

	
	private int ageContinuationInsureAtr;

	
	private int tenureAtr;

	
	private int taxAtr;

	
	private int pensionInsuranceGrade;

	
	private int pensionAverageEarn;

	
	private int employmentInsuranceAtr;

	
	private int dependentNumber;

	
	private int workInsuranceCalculateAtr;

	
	private int insuredAtr;

	
	private int bonusTaxRate;

	
	private int calcFlag;

	
	private int makeMethodFlag;

	/*
	private List<DetailItem> detailPaymentItems;

	
	private List<DetailDeductionItem> detailDeductionItems;

	
	private List<DetailItem> detailPersonalTimeItems;

	
	private List<DetailItem> detailArticleItems;

	
	private List<PrintPositionCategory> printCategories; */
}
