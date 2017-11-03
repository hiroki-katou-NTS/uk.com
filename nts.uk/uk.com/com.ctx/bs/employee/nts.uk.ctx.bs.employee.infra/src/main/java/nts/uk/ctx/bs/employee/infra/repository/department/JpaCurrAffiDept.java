package nts.uk.ctx.bs.employee.infra.repository.department;

import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.bs.employee.dom.department.CurrentAffiDept;
import nts.uk.ctx.bs.employee.dom.department.CurrentAffiDeptRepository;
import nts.uk.ctx.bs.employee.infra.entity.department.BsymtCurrAffiDept;
import nts.uk.shr.com.history.DateHistoryItem;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

@Stateless
public class JpaCurrAffiDept extends JpaRepository implements CurrentAffiDeptRepository {

	private static final String SELECT_CURR_AFF_DEPT_BY_ID = "SELECT c BsymtCurrAffiDept c"
			+ " WHERE c.bsymtCurrAffiDeptPK.affiDeptId = :affiDeptId";

	private CurrentAffiDept toDomain(BsymtCurrAffiDept entity) {
		return null;
//		return new CurrentAffiDept(entity.getSid(), entity.affiDeptId, entity.depId,
//				entity.lstBsymtAssiWorkplaceHist.stream()
//						.map(x -> new DateHistoryItem(x.getHistoryId(), new DatePeriod(x.getStrD(), x.getEndD())))
//						.collect(Collectors.toList()));
	}

	@Override
	public CurrentAffiDept getCurrentAffiDeptById(String currentAffiDeptById) {
		Optional<CurrentAffiDept> currentAffiDept = this.queryProxy()
				.query(SELECT_CURR_AFF_DEPT_BY_ID, BsymtCurrAffiDept.class)
				.setParameter("affiDeptId", currentAffiDeptById).getSingle(x -> toDomain(x));
		return currentAffiDept.isPresent() ? currentAffiDept.get() : null;
	}

}
