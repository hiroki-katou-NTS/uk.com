package nts.uk.ctx.at.schedule.ac.jobtitle;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.commons.lang3.tuple.Pair;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.adapter.jobtitle.EmployeeJobHistImported;
import nts.uk.ctx.at.schedule.dom.adapter.jobtitle.PositionImport;
import nts.uk.ctx.at.schedule.dom.adapter.jobtitle.SyJobTitleAdapter;
import nts.uk.ctx.at.schedule.dom.employeeinfo.employeesort.EmployeePosition;
import nts.uk.ctx.bs.employee.pub.jobtitle.EmployeeJobHistExport;
import nts.uk.ctx.bs.employee.pub.jobtitle.JobTitleExport;
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

	@Override
	public List<PositionImport> findAll(String companyId, GeneralDate baseDate) {
		
		List<JobTitleExport> data = syJobTitlePub.findAll(companyId, baseDate);
		if (data.isEmpty()) {
			return new ArrayList<PositionImport>();
		}
		return data.stream().map(m -> {
			
			return new PositionImport(m.getJobTitleId(), m.getJobTitleCode(), m.getJobTitleName());
		}).collect(Collectors.toList());
	}

	@Override
	public List<EmployeePosition> findSJobHistByListSIdV2(List<String> employeeIds, GeneralDate baseDate) {
		List<EmployeeJobHistExport> listdata =  syJobTitlePub.findSJobHistByListSIdV2(employeeIds, baseDate);
		if (listdata.isEmpty()) {
			return new ArrayList<EmployeePosition>();
		}
		return listdata.stream().map(m -> {
			
			return new EmployeePosition(m.getEmployeeId(), m.getJobTitleID(), m.getJobTitleCode());
		}).collect(Collectors.toList());
	}
}
