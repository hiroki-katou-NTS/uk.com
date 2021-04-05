package nts.uk.ctx.office.ac.favoritespecify;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.pub.jobtitle.EmployeeJobHistExport;
import nts.uk.ctx.bs.employee.pub.jobtitle.SyJobTitlePub;
import nts.uk.ctx.office.dom.favorite.adapter.EmployeeJobHistImport;
import nts.uk.ctx.office.dom.favorite.adapter.EmployeePositionAdapter;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class EmployeePositionAdapterImpl implements EmployeePositionAdapter {

	@Inject
	private SyJobTitlePub jobTitlePub;

	@Override
	public Map<String, EmployeeJobHistImport> getPositionBySidsAndBaseDate(List<String> sIds, GeneralDate baseDate) {
		// $職位Map ＝ new HashMap<>()
		List<EmployeeJobHistExport> jobHistory = this.jobTitlePub.findSJobHistByListSIdV2(sIds, baseDate).stream()
				.filter(value -> sIds.contains(value.getEmployeeId())).collect(Collectors.toList());
		if (jobHistory.isEmpty()) {
			return Collections.emptyMap();
		}
		Map<String, EmployeeJobHistImport> jobMap = jobHistory.stream()
				.collect(Collectors.toMap(x -> x.getEmployeeId(),
						x -> EmployeeJobHistImport.builder()
							.employeeId(x.getEmployeeId())
							.jobTitleID(x.getJobTitleID())
							.sequenceCode(x.getSequenceCode())
							.jobTitleName(x.getJobTitleName())
							.startDate(x.getStartDate())
							.endDate(x.getEndDate())
							.jobTitleCode(x.getJobTitleCode())
							.build()));
		return jobMap;
	}
}
