package nts.uk.ctx.at.schedule.ac.jobtitle;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.commons.lang3.tuple.Pair;

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

	@Override
	public Map<Pair<String, GeneralDate>, Pair<String,String>> getJobTitleMapIdBaseDateName(String companyId, List<String> jobIds,
			List<GeneralDate> baseDates) {
		return this.syJobTitlePub.getJobTitleMapIdBaseDateName(companyId, jobIds, baseDates);
	}
}
