package nts.uk.ctx.pr.proto.dom.paymentdatainput;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.proto.dom.enums.PayBonusAttribute;
import nts.uk.ctx.pr.proto.dom.enums.SparePayAttribute;
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
