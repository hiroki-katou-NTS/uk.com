package nts.uk.ctx.at.schedule.ac.employmentstatus;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.schedule.dom.adapter.employmentstatus.EmploymentInfoImported;
import nts.uk.ctx.at.schedule.dom.adapter.employmentstatus.EmploymentStatusAdapter;
import nts.uk.ctx.at.schedule.dom.adapter.employmentstatus.EmploymentStatusImported;
import nts.uk.ctx.bs.employee.pub.employmentstatus.EmploymentStatusPub;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * 
 * @author sonnh1
 *
 */
@Stateless
public class ScEmploymentStatusImpl implements EmploymentStatusAdapter {

	@Inject
	private EmploymentStatusPub employmentStatusPub;

	@Override
	public List<EmploymentStatusImported> findListOfEmployee(List<String> employeeIds, DatePeriod period) {
		return this.employmentStatusPub.findListOfEmployee(employeeIds, period).stream()
				.map(empStatus -> new EmploymentStatusImported(empStatus.getEmployeeId(),
						empStatus.getEmploymentInfo().stream().map(employmentInfo -> {
							return new EmploymentInfoImported(employmentInfo.getStandardDate(),
									employmentInfo.getEmploymentState().value, employmentInfo.getTempAbsenceFrNo());
						}).collect(Collectors.toList())))
				.collect(Collectors.toList());
	}
}
