package nts.uk.ctx.sys.assist.ac.favoritespecify;

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
import nts.uk.ctx.sys.assist.dom.favorite.adapter.EmployeePositionAdapter;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class EmployeePositionAdapterImpl implements EmployeePositionAdapter {

	@Inject
	private SyJobTitlePub jobTitlePub;

	@Override
	public Map<String, String> getPositionBySidsAndBaseDate(List<String> sIds, GeneralDate baseDate) {
		// $職位Map ＝ new HashMap<>()
		List<EmployeeJobHistExport> jobHistory = this.jobTitlePub.findSJobHistByListSId(sIds, baseDate).stream()
				.filter(value -> sIds.contains(value.getEmployeeId())).collect(Collectors.toList());
		if (jobHistory.isEmpty()) {                                                    
			return Collections.emptyMap();
		}
		Map<String, String> jobMap = jobHistory.stream().collect(Collectors.toMap(x -> x.getEmployeeId(), x -> x.getJobTitleID()));
		return jobMap;
	}

}
