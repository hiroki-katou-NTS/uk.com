package nts.uk.ctx.at.record.app.command.workrecord.monthlyresult.company;

import nts.uk.ctx.at.record.dom.workrecord.monthlyresult.LegalAggrSetOfIrgNew;
import nts.uk.ctx.at.record.dom.workrecord.monthlyresult.company.ComLaborDeforSetMonthlyGetMemento;
import nts.uk.ctx.at.shared.dom.common.CompanyId;

public class UpdateCompanyLaborDeforSetMonthlyCommand implements ComLaborDeforSetMonthlyGetMemento{
	
	/** The company id. */
	/* 会社ID. */
	private CompanyId companyId;

	/** The legal aggr set of irg new. */
	/* 集計設定. */
	private LegalAggrSetOfIrgNew legalAggrSetOfIrgNew;

	@Override
	public CompanyId getCompanyId() {
		
		return null;
	}

	@Override
	public LegalAggrSetOfIrgNew getLegalAggrSetOfIrgNew() {
		// TODO Auto-generated method stub
		return null;
	}

}
