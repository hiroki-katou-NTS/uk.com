package nts.uk.ctx.bs.employee.infra.repository.department;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.dom.department.CurrentAffiDept;
import nts.uk.ctx.bs.employee.dom.department.CurrentAffiDeptRepository;
import nts.uk.shr.com.history.DateHistoryItem;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

@Stateless
public class JpaCurrAffiDept extends JpaRepository implements CurrentAffiDeptRepository {
	private static final String SELECT_NO_WHERE = "SELECT c, h.strD, h.endD FROM BsymtCurrAffiDeptHist h"
			+ " INNER JOIN BsymtCurrAffiDept c ON c.histId = h.historyId";
	private static final String SELECT_CURR_AFF_DEPT_BY_ID = SELECT_NO_WHERE
			+ " WHERE c.bsymtCurrAffiDeptPK.affiDeptId = :affiDeptId";

	private CurrentAffiDept toDomain(List<Object[]> entity) {
		
		CurrentAffiDept currentAffiDept = new CurrentAffiDept(String.valueOf(entity.get(0)[0].toString())
				, String.valueOf(entity.get(0)[1].toString()), 
				String.valueOf(entity.get(0)[2].toString()), 
				entity.stream().map(x -> new DateHistoryItem(String.valueOf(x[3].toString()), 
						new DatePeriod(GeneralDate.fromString(String.valueOf(x[4].toString()), ""), 
								GeneralDate.fromString(String.valueOf(x[5].toString()), "")))).collect(Collectors.toList()));
		return currentAffiDept;
	}

	@Override
	public CurrentAffiDept getCurrentAffiDeptById(String currentAffiDeptById) {
		List<Object[]> currentAffiDept = this.queryProxy()
				.query(SELECT_CURR_AFF_DEPT_BY_ID, Object[].class)
				.setParameter("affiDeptId", currentAffiDeptById).getList();
		return toDomain(currentAffiDept);
	}

}
