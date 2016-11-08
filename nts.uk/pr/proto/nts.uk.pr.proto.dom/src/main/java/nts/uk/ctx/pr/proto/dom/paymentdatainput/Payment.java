package nts.uk.ctx.pr.proto.dom.paymentdatainput;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.proto.dom.enums.PayBonusAttribute;
import nts.uk.ctx.pr.proto.dom.enums.SparePayAttribute;

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
	
	
	
}
