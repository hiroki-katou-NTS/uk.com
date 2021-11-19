package nts.uk.ctx.at.shared.ac.jobtitle;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.adapter.jobtitle.SyJobTitleAdapter;
import nts.uk.ctx.at.shared.dom.employmentrules.organizationmanagement.EmployeePosition;
import nts.uk.ctx.bs.employee.pub.jobtitle.EmployeeJobHistExport;
import nts.uk.ctx.bs.employee.pub.jobtitle.SyJobTitlePub;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 
 * @author rafiqul.islam
 *
 */
@Stateless
public class SyShareJobTitleAdapterImpl implements SyJobTitleAdapter {

	@Inject
	private SyJobTitlePub syJobTitlePub;

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