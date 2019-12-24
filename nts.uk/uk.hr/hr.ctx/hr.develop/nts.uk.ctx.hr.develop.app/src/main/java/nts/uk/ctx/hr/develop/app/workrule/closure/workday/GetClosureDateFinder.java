package nts.uk.ctx.hr.develop.app.workrule.closure.workday;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.hr.develop.dom.workrule.closure.workday.ClosureDateOfEmploymentImport;
import nts.uk.ctx.hr.develop.dom.workrule.closure.workday.GetClosureDateAdaptor;

@Stateless
public class GetClosureDateFinder {

	@Inject
	private GetClosureDateAdaptor workDayAdaptor;

	public List<ClosureDateOfEmploymentImport> getClosureDateAdaptor(String companyId) {

		return this.workDayAdaptor.getClosureDate(companyId);
	}
}
