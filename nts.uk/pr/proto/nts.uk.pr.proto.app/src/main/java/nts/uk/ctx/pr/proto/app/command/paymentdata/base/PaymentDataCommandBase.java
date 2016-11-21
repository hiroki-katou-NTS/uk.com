package nts.uk.ctx.pr.proto.app.command.paymentdata.base;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.Setter;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.pr.proto.dom.enums.CategoryAtr;
import nts.uk.ctx.pr.proto.dom.itemmaster.DeductionAtr;
import nts.uk.ctx.pr.proto.dom.itemmaster.ItemCode;
import nts.uk.ctx.pr.proto.dom.paymentdata.Payment;
import nts.uk.ctx.pr.proto.dom.paymentdata.dataitem.CorrectFlag;
import nts.uk.ctx.pr.proto.dom.paymentdata.dataitem.DetailDeductionItem;
import nts.uk.ctx.pr.proto.dom.paymentdata.dataitem.DetailItem;
import nts.uk.ctx.pr.proto.dom.paymentdata.dataitem.InsuranceAtr;

/**
 * 
 * @author vunv
 *
 */
@Getter
@Setter
public abstract class PaymentDataCommandBase {

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

	private List<DetailItemCommandBase> detailPaymentItems;

	private List<DetailItemCommandBase> detailDeductionItems;

	private List<DetailItemCommandBase> detailPersonalTimeItems;

	private List<DetailItemCommandBase> detailArticleItems;

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
	
	/**
	 * convert data from command to value object deduction detail item
	 * @param items
	 * @return
	 */
	public List<DetailDeductionItem> setDudectionDetailItems(List<DetailItemCommandBase> items) {
		return items.stream().map(c-> {
			return new DetailDeductionItem(
				new ItemCode(c.getItemCode()), 
				c.getValue(), 
				EnumAdaptor.valueOf(c.getCorrectFlag(), CorrectFlag.class), 
				EnumAdaptor.valueOf(c.getSocialInsuranceAtr(), InsuranceAtr.class), 
				EnumAdaptor.valueOf(c.getLaborInsuranceAtr(), InsuranceAtr.class),
				EnumAdaptor.valueOf(c.getDeductionAtr(), DeductionAtr.class),
				EnumAdaptor.valueOf(c.getCategoryAtr(), CategoryAtr.class)
					);
				}
		).collect(Collectors.toList());
	}
}
