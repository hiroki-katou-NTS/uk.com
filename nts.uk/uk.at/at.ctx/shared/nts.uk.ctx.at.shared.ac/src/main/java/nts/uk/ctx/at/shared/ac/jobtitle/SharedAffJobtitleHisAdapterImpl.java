package nts.uk.ctx.at.shared.ac.jobtitle;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.adapter.jobtitle.SharedAffJobTitleHisImport;
import nts.uk.ctx.at.shared.dom.adapter.jobtitle.SharedAffJobtitleHisAdapter;
import nts.uk.ctx.bs.employee.pub.jobtitle.EmployeeJobHistExport;
import nts.uk.ctx.bs.employee.pub.jobtitle.SyJobTitlePub;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

@Stateless
public class SharedAffJobtitleHisAdapterImpl implements SharedAffJobtitleHisAdapter{
	
	@Inject
	private SyJobTitlePub syJobTitlePub;

	@Override
	public Optional<SharedAffJobTitleHisImport> findAffJobTitleHis(String employeeId, GeneralDate processingDate) {
		Optional<EmployeeJobHistExport> employeeJobHis = this.syJobTitlePub.findSJobHistBySId(employeeId, processingDate);
		
		if(!employeeJobHis.isPresent()) {
			return Optional.empty();
		}
		DatePeriod dateRange = new DatePeriod(employeeJobHis.get().getStartDate(), employeeJobHis.get().getEndDate());
		SharedAffJobTitleHisImport affJobTitleSidImport = new SharedAffJobTitleHisImport(employeeJobHis.get().getEmployeeId(),
				employeeJobHis.get().getJobTitleID(), dateRange, employeeJobHis.get().getJobTitleName());
		return Optional.of(affJobTitleSidImport);
	}

}
