package nts.uk.ctx.pr.proto.app.paymentdata.command;

import java.time.LocalDate;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.pr.proto.dom.itemmaster.TaxAtr;
import nts.uk.ctx.pr.proto.dom.paymentdata.BonusTaxRate;
import nts.uk.ctx.pr.proto.dom.paymentdata.CalcFlag;
import nts.uk.ctx.pr.proto.dom.paymentdata.Comment;
import nts.uk.ctx.pr.proto.dom.paymentdata.DependentNumber;
import nts.uk.ctx.pr.proto.dom.paymentdata.MakeMethodFlag;
import nts.uk.ctx.pr.proto.dom.paymentdata.Payment;
import nts.uk.ctx.pr.proto.dom.paymentdata.SpecificationCode;
import nts.uk.ctx.pr.proto.dom.paymentdata.TenureAtr;
import nts.uk.ctx.pr.proto.dom.paymentdata.insure.AgeContinuationInsureAtr;
import nts.uk.ctx.pr.proto.dom.paymentdata.insure.EmploymentInsuranceAtr;
import nts.uk.ctx.pr.proto.dom.paymentdata.insure.HealthInsuranceAverageEarn;
import nts.uk.ctx.pr.proto.dom.paymentdata.insure.HealthInsuranceGrade;
import nts.uk.ctx.pr.proto.dom.paymentdata.insure.InsuredAtr;
import nts.uk.ctx.pr.proto.dom.paymentdata.insure.PensionAverageEarn;
import nts.uk.ctx.pr.proto.dom.paymentdata.insure.PensionInsuranceGrade;
import nts.uk.ctx.pr.proto.dom.paymentdata.insure.WorkInsuranceCalculateAtr;
import nts.uk.ctx.pr.proto.dom.paymentdata.residence.ResidenceCode;
import nts.uk.ctx.pr.proto.dom.paymentdata.residence.ResidenceName;

/**
 * 
 * @author vunv
 *
 */
@Getter
@Setter
public abstract class PaymentDataCommand {

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

	// private List<DetailItemCommand> detailPaymentItems;
	//
	// private List<DetailItemCommand> detailDeductionItems;
	//
	// private List<DetailItemCommand> detailPersonalTimeItems;
	//
	// private List<DetailItemCommand> detailArticleItems;

	/**
	 * Convert to domain object.
	 * 
	 * @return domain
	 */
	public Payment toDomain(String companyCode, String personId) {
		return Payment.createFromJavaType(
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
	}
}
