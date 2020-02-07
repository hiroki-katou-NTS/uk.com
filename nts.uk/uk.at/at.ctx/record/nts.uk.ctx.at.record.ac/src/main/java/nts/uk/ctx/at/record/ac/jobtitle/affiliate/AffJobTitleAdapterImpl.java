package nts.uk.ctx.at.record.ac.jobtitle.affiliate;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.jobtitle.affiliate.AffJobTitleAdapter;
import nts.uk.ctx.at.record.dom.jobtitle.affiliate.AffJobTitleSidImport;
import nts.uk.ctx.bs.employee.pub.jobtitle.EmployeeJobHistExport;
import nts.uk.ctx.bs.employee.pub.jobtitle.SyJobTitlePub;
import nts.arc.time.calendar.period.DatePeriod;

@Stateless
public class AffJobTitleAdapterImpl implements AffJobTitleAdapter {

	@Inject
	private SyJobTitlePub syJobTitlePub;

	@Override
	public Optional<AffJobTitleSidImport> findByEmployeeId(String employeeId, GeneralDate baseDate) {
		Optional<EmployeeJobHistExport> employeeJobHis = this.syJobTitlePub.findSJobHistBySId(employeeId, baseDate);
		if(!employeeJobHis.isPresent()) {
			return Optional.empty();
		}
		DatePeriod dateRange = new DatePeriod(employeeJobHis.get().getStartDate(), employeeJobHis.get().getEndDate());
		AffJobTitleSidImport affJobTitleSidImport = new AffJobTitleSidImport(employeeJobHis.get().getEmployeeId(),
				employeeJobHis.get().getJobTitleID(), dateRange);
		return Optional.of(affJobTitleSidImport);
	}

}
