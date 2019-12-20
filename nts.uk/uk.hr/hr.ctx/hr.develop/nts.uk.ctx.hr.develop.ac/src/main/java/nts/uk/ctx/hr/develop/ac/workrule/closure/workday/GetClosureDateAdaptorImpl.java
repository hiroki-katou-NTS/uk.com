package nts.uk.ctx.hr.develop.ac.workrule.closure.workday;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.pub.workrule.closure.workday.IWorkDayPub;
import nts.uk.ctx.hr.develop.dom.workrule.closure.workday.ClosureDateOfEmploymentImport;
import nts.uk.ctx.hr.develop.dom.workrule.closure.workday.GetClosureDateAdaptor;

@Stateless
public class GetClosureDateAdaptorImpl implements GetClosureDateAdaptor {

	@Inject
	private IWorkDayPub workPub;

	@Override
	public List<ClosureDateOfEmploymentImport> getClosureDate(String companyId) {

		return this.workPub
				.getClosureDate(companyId).stream().map(x -> ClosureDateOfEmploymentImport.builder()
						.employmentCd(x.getEmploymentCd()).closureDay(x.getClosureDate()).build())
				.collect(Collectors.toList());
	}

}
