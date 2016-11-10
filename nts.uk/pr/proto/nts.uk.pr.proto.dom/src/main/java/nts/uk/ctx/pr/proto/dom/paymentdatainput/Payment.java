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
import nts.uk.shr.com.primitive.PersonId;

public class Payment extends AggregateRoot {
	@Getter
	private CompanyCode companyCode;

	@Getter
	private PersonId personId;

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

	public Payment(CompanyCode companyCode, PersonId personId, ProcessingNo processingNo,
			PayBonusAttribute payBonusAttribute, int processingYM, SparePayAttribute sparePayAttribute) {
		super();
		this.companyCode = companyCode;
		this.personId = personId;
		this.processingNo = processingNo;
		this.payBonusAttribute = payBonusAttribute;
		this.processingYM = processingYM;
		this.sparePayAttribute = sparePayAttribute;
	}

}
