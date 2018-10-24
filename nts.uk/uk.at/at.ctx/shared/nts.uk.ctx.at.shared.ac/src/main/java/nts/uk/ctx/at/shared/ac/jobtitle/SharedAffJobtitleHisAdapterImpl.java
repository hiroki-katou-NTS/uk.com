package nts.uk.ctx.at.shared.ac.jobtitle;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

	@Override
	public List<SharedAffJobTitleHisImport> findAffJobTitleHisByListSid(List<String> employeeIds,
			GeneralDate processingDate) {
		return this.syJobTitlePub.findSJobHistByListSIdV2(employeeIds, processingDate).stream().map(c->convertToExport(c)).collect(Collectors.toList());
	}
	
	private SharedAffJobTitleHisImport convertToExport (EmployeeJobHistExport export) {
		DatePeriod dateRange = new DatePeriod(export.getStartDate(), export.getEndDate());
		return new  SharedAffJobTitleHisImport(
				export.getEmployeeId(),
				export.getJobTitleID(), 
				dateRange, 
				export.getJobTitleName());
	}

}
