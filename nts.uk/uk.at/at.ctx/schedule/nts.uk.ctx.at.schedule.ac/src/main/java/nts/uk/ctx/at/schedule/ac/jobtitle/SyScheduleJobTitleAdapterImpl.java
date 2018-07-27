package nts.uk.ctx.at.schedule.ac.jobtitle;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.adapter.jobtitle.EmployeeJobHistImported;
import nts.uk.ctx.at.schedule.dom.adapter.jobtitle.SyJobTitleAdapter;
import nts.uk.ctx.bs.employee.pub.jobtitle.SyJobTitlePub;

/**
 * 
 * @author sonnh1
 *
 */
@Stateless
public class SyScheduleJobTitleAdapterImpl implements SyJobTitleAdapter {

	@Inject
	private SyJobTitlePub syJobTitlePub;

	@Override
	public Optional<EmployeeJobHistImported> findBySid(String employeeId, GeneralDate baseDate) {
		return this.syJobTitlePub.findBySid(employeeId, baseDate)
				.map(x -> new EmployeeJobHistImported(x.getEmployeeId(), x.getJobTitleID(), x.getJobTitleName(),
						x.getStartDate(), x.getEndDate()));
	}
}
