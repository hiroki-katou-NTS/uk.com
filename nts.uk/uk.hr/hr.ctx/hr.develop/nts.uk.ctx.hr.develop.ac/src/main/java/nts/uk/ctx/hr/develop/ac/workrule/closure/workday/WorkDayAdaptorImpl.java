package nts.uk.ctx.hr.develop.ac.workrule.closure.workday;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.pub.workrule.closure.workday.IWorkDayPub;
import nts.uk.ctx.hr.develop.dom.workrule.closure.workday.ClosureDateOfEmploymentImport;
import nts.uk.ctx.hr.develop.dom.workrule.closure.workday.WorkDayAdaptor;

@Stateless
public class WorkDayAdaptorImpl implements WorkDayAdaptor {

	@Inject
	private IWorkDayPub workPub;

	@Override
	public List<ClosureDateOfEmploymentImport> getClosureDate(String companyId) {

		return this.workPub
				.getClosureDate(companyId).stream().map(x -> ClosureDateOfEmploymentImport.builder()
						.employmentCd(x.getEmploymentCd()).closureDay(x.getClosureDay()).build())
				.collect(Collectors.toList());
	}

}
