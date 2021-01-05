package nts.uk.ctx.sys.assist.ac.favoritespecify;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
		Map<String, String> jobMap = new HashMap<>();
		List<EmployeeJobHistExport> jobHistory = this.jobTitlePub.findSJobHistByListSId(sIds, baseDate);
		if (jobHistory.isEmpty()) {
			return Collections.emptyMap();
		}
		for (String sid : sIds) {
			for (EmployeeJobHistExport hist : jobHistory) {
				if (sid.equals(hist.getEmployeeId())) {
					jobMap.put(sid, hist.getJobTitleID());
				}
			}
		}
		return jobMap;
	}

}
