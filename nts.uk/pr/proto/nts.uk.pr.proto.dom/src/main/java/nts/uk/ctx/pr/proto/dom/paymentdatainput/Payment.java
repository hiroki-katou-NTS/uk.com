package nts.uk.ctx.pr.proto.dom.paymentdatainput;

import java.util.Date;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.proto.dom.enums.AgeContinuationInsureAtr;
import nts.uk.ctx.pr.proto.dom.enums.PayBonusAttribute;
import nts.uk.ctx.pr.proto.dom.enums.SparePayAttribute;
import nts.uk.ctx.pr.proto.dom.enums.TaxAtr;
import nts.uk.ctx.pr.proto.dom.enums.TenureAttribute;

public class Payment extends AggregateRoot {
	@Getter
	private CompanyCode companyCode;

	@Getter
	private int personId;

	@Getter
	private ProcessingNo processingNo;

	@Getter
	private PayBonusAttribute payBonusAttribute;

	@Getter
	private int processingYM;

	@Getter
	private SparePayAttribute sparePayAttribute;

	private Date standardDate;
	
	private SpecificationCode specificationCode;
	
	private ResidenceCode residenceCode;
	
	private ResidenceName residenceName;
	
	private HealthInsuranceGrade healthInsuranceGrade;
	
	private HealthInsuranceAverageEarn healthInsuranceAverageEarn;
	
	private AgeContinuationInsureAtr ageContinuationInsureAtr;
	
	private TenureAttribute tenureAttribute;
	
	private TaxAtr taxAtr;
	
	
	
}
