package nts.uk.ctx.pr.proto.dom.personalwagename;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.proto.dom.enums.CategoryAtr;

public class PersonalWageNameMaster extends AggregateRoot {

	@Getter
	private CompanyCode companyCode;

	@Getter
	private CategoryAtr categoryAtr;

	@Getter
	private PersonalWageCode personalWageCode;

	@Getter
	private PersonalWageName personalWageName;

	public PersonalWageNameMaster(CompanyCode companyCode, CategoryAtr categoryAtr, PersonalWageCode personalWageCode,
			PersonalWageName personalWageName) {
		super();
		this.companyCode = companyCode;
		this.categoryAtr = categoryAtr;
		this.personalWageCode = personalWageCode;
		this.personalWageName = personalWageName;
	}

}
