package nts.uk.ctx.pr.proto.dom.paymentdata;

import java.util.Date;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.proto.dom.itemmaster.TaxAtr;
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
import nts.uk.shr.com.primitive.PersonId;

/**
 * 明細データ
 * 
 * @author vunv
 *
 */
public class Payment extends AggregateRoot {
	@Getter
	private CompanyCode companyCode;

	@Getter
	private PersonId personId;

	@Getter
	private ProcessingNo processingNo;

	@Getter
	private PayBonusAtr payBonusAttribute;

	@Getter
	private int processingYM;

	@Getter
	private SparePayAtr sparePayAttribute;

	private Date standardDate;

	private SpecificationCode specificationCode;

	private ResidenceCode residenceCode;

	private ResidenceName residenceName;

	private HealthInsuranceGrade healthInsuranceGrade;

	private HealthInsuranceAverageEarn healthInsuranceAverageEarn;

	private AgeContinuationInsureAtr ageContinuationInsureAtr;

	private TenureAtr tenureAttribute;

	private TaxAtr taxAttribute;

	private PensionInsuranceGrade pensionInsuranceGrade;

	private PensionAverageEarn pensionAverageEarn;

	private EmploymentInsuranceAtr employmentInsuranceAtr;

	private DependentNumber dependentNumber;

	private WorkInsuranceCalculateAtr workInsuranceCalculateAtr;

	private InsuredAtr insuredAttribute;
	
	private BonusTaxRate bonusTaxRate;
	
	private CalcFlag calcFlag;
	
	private MakeMethodFlag makeMethodFlag;
	
	

	public Payment(CompanyCode companyCode, PersonId personId, ProcessingNo processingNo, PayBonusAtr payBonusAttribute,
			int processingYM, SparePayAtr sparePayAttribute) {
		super();
		this.companyCode = companyCode;
		this.personId = personId;
		this.processingNo = processingNo;
		this.payBonusAttribute = payBonusAttribute;
		this.processingYM = processingYM;
		this.sparePayAttribute = sparePayAttribute;
	}

}
