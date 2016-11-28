package nts.uk.ctx.pr.proto.app.command.paymentdata.base;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.pr.proto.dom.paymentdata.Payment;

/**
 * 
 * @author vunv
 *
 */
@Getter
@Setter
public abstract class PaymentHeaderCommandBase {

	private String personId;
	
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

	private String comment;

	/**
	 * Convert to domain object.
	 * 
	 * @return domain
	 */
	public Payment toDomain(String companyCode) {
		Payment payment =  Payment.createFromJavaType(
				companyCode, 
				personId, 
				this.processingNo, 
				this.payBonusAtr, 
				this.processingYM, 
				this.sparePayAtr, 
				this.standardDate, 
				this.specificationCode, 
				this.residenceCode, 
				this.residenceName, 
				this.healthInsuranceGrade, 
				this.healthInsuranceAverageEarn, 
				this.ageContinuationInsureAtr, 
				this.tenureAtr, 
				this.taxAtr, 
				this.pensionInsuranceGrade, 
				this.pensionAverageEarn, 
				this.employmentInsuranceAtr, 
				this.dependentNumber, 
				this.workInsuranceCalculateAtr, 
				this.insuredAtr, 
				this.bonusTaxRate, 
				this.calcFlag, 
				this.makeMethodFlag,
				this.comment);
		
		return payment;
	}
}
