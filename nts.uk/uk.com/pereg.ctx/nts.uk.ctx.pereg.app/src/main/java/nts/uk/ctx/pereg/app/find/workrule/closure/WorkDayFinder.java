package nts.uk.ctx.pereg.app.find.workrule.closure;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.pereg.dom.workrule.closure.ClosureDateOfEmploymentImport;
import nts.uk.ctx.pereg.dom.workrule.closure.WorkDayAdaptor;

@Stateless
public class WorkDayFinder {

	@Inject
	private WorkDayAdaptor adaptor;

	public List<ClosureDateOfEmploymentImport> getClosureDate(String companyId) {

		return this.adaptor.getClosureDate(companyId);
	}
}
