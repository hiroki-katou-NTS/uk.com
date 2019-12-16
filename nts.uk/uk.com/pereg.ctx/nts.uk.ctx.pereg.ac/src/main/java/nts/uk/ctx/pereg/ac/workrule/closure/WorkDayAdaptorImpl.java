package nts.uk.ctx.pereg.ac.workrule.closure;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.pub.workrule.closure.workday.IWorkDayPub;
import nts.uk.ctx.pereg.dom.workrule.closure.ClosureDateOfEmploymentImport;
import nts.uk.ctx.pereg.dom.workrule.closure.WorkDayAdaptor;

@Stateless
public class WorkDayAdaptorImpl implements WorkDayAdaptor {

	@Inject
	private IWorkDayPub pub;

	@Override
	public List<ClosureDateOfEmploymentImport> getClosureDate(String companyId) {
		return this.pub
				.getClosureDate(companyId).stream().map(x -> ClosureDateOfEmploymentImport.builder()
						.employmentCd(x.getEmploymentCd()).closureDate(x.getClosureDate()).build())
				.collect(Collectors.toList());
	}
}
