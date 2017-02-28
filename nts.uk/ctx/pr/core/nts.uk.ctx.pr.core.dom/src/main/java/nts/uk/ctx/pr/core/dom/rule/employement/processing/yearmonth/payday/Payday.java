package nts.uk.ctx.pr.core.dom.rule.employement.processing.yearmonth;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.core.dom.company.CompanyCode;

public class Payday extends AggregateRoot {	
	@Getter
	private CompanyCode companyCode;

}
