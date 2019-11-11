package nts.uk.ctx.hr.develop.infra.repository.stresscheck;

import java.util.List;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.hr.develop.dom.personalinfo.stresscheck.StressCheck;
import nts.uk.ctx.hr.develop.dom.personalinfo.stresscheck.StressCheckRepository;
import nts.uk.ctx.hr.develop.infra.entity.stresscheck.PpedtStressItem;

/**
 * @author anhdt
 *
 */
@Stateless
public class JpaStressCheckRepository extends JpaRepository implements StressCheckRepository {

	private static final String SEL_PERSONNEL_ASSESMENT_BY_EMPLOYEES_AND_START_DATE = "SELECT i FROM PpedtStressItem i "
			+ "INNER JOIN PpedtStressHistory h ON i.ppedtStressItemPk.historyID = h.ppedtStressHistoryPk.historyID "
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
	public List<StressCheck> getPersonnelAssessmentByEmployeeIds(List<String> employeeIds,
			GeneralDate startDate) {
		return this.queryProxy()
				.query(SEL_PERSONNEL_ASSESMENT_BY_EMPLOYEES_AND_START_DATE, PpedtStressItem.class)
				.setParameter("employee", employeeIds)
				.setParameter("startDate", startDate)
				.getList(e -> StressCheck.builder()
						.companyID(e.companyID)
						.employeeID(e.employeeID)
						.overallResult(e.overallResult)
						.historyID(e.ppedtStressItemPk.historyID)
						.build());
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
