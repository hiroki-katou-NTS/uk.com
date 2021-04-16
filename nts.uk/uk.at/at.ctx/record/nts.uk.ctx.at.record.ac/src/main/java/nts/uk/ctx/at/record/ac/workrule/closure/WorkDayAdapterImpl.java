package nts.uk.ctx.at.record.ac.workrule.closure;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.adapter.workrule.closure.ClosureDateOfEmploymentImport;
import nts.uk.ctx.at.record.dom.adapter.workrule.closure.WorkDayAdapter;
import nts.uk.ctx.at.shared.pub.workrule.closure.workday.IWorkDayPub;

@Stateless
public class WorkDayAdapterImpl implements WorkDayAdapter {
	@Inject
	private IWorkDayPub workDayPub;
	
	@Override
	public List<ClosureDateOfEmploymentImport> getClosureDate(String companyId) {
		// TODO Auto-generated method stub
		return workDayPub.getClosureDate(companyId).stream()
				.map(x -> new ClosureDateOfEmploymentImport(x.getEmploymentCd(), x.getClosureDate()))
				.collect(Collectors.toList());
	}

}
