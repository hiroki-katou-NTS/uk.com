package nts.uk.ctx.hr.shared.infra.repository.stresscheck;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.DbConsts;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.hr.shared.dom.personalinfo.stresscheck.StressCheck;
import nts.uk.ctx.hr.shared.dom.personalinfo.stresscheck.StressCheckRepository;
import nts.uk.ctx.hr.shared.dom.personalinfo.stresscheck.StressCheckResults;
import nts.uk.ctx.hr.shared.infra.entity.stresscheck.PpedtStressHistory;
import nts.uk.ctx.hr.shared.infra.entity.stresscheck.PpedtStressItem;

/**
 * @author anhdt
 *
 */
@Stateless
public class JpaStressCheckRepository extends JpaRepository implements StressCheckRepository {

	private static final String SEL_BY_EMPLOYEES_AND_START_DATE = "SELECT i, h FROM PpedtStressItem i INNER JOIN PpedtStressHistory h "
			+ "ON i.ppedtStressItemPk.historyID = h.ppedtStressHistoryPk.historyID "
			+ "WHERE i.employeeID IN :employee " 
			+ "AND h.startDate >= :startDate ORDER BY h.startDate";
	
	private static final String SEL_PERSONNEL_ASSESMENT_BY_EMPLOYEES = "SELECT i FROM PpedtStressItem i "
			+ "INNER JOIN PpedtStressHistory h ON i.ppedtStressItemPk.historyID = h.ppedtStressHistoryPk.historyID "
			+ "WHERE i.employeeID = :employee " 
			+ "ORDER BY h.startDate";

	/*
	 * ドメイン [人事評価] を取得する
	 */
	@Override
	public List<StressCheckResults> getPersonnelAssessmentByEmployeeIds(List<String> employeeIds, GeneralDate startDate) {
		if (employeeIds.isEmpty()) {
			return new ArrayList<>();
		}
		List<StressCheckResults> result = new ArrayList<>();
		CollectionUtil.split(employeeIds, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, d -> {
			result.addAll(this.queryProxy().query(SEL_BY_EMPLOYEES_AND_START_DATE, Object[].class)
											.setParameter("employee", d)
											.setParameter("startDate", startDate)
											.getList(c->joinObjectToDomain(c)));
		});
		return result;
	}
	
	private StressCheckResults joinObjectToDomain(Object[] entity) {
		PpedtStressItem item = (PpedtStressItem) entity[0];
		PpedtStressHistory hist = (PpedtStressHistory) entity[1];
		return new StressCheckResults(item.companyID, item.employeeID, hist.startDate, hist.endDate, item.overallResult);
	}
	
	@Override
	public List<StressCheck> getPersonnelAssessmentByEmployeeId(String employeeId) {
		return this.queryProxy()
				.query(SEL_PERSONNEL_ASSESMENT_BY_EMPLOYEES, PpedtStressItem.class)
				.setParameter("employee", employeeId)
				.getList(e -> StressCheck.builder()
						.companyID(e.companyID)
						.employeeID(e.employeeID)
						.overallResult(e.overallResult)
						.historyID(e.ppedtStressItemPk.historyID)
						.build());
	}

}
