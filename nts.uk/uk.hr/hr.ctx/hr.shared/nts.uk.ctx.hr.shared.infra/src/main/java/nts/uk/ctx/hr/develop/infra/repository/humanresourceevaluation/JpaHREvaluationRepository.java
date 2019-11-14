package nts.uk.ctx.hr.develop.infra.repository.humanresourceevaluation;

import java.util.List;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.hr.develop.dom.personalinfo.humanresourceevaluation.HumanResourceEvaluationRepository;
import nts.uk.ctx.hr.develop.dom.personalinfo.humanresourceevaluation.PersonnelAssessment;
import nts.uk.ctx.hr.develop.infra.entity.humanresourceevaluation.PpedtPerAssessmentItem;

/**
 * @author anhdt
 *
 */
@Stateless
public class JpaHREvaluationRepository extends JpaRepository implements HumanResourceEvaluationRepository {

	private static final String SEL_PERSONNEL_ASSESMENT_BY_EMPLOYEES_AND_START_DATE = "SELECT i FROM PpedtPerAssessmentItem i "
			+ "INNER JOIN PeedtPerAssessmentHist h ON i.ppedtPerAssessmentItemPk.historyID = h.peedtPerAssessmentHistPk.historyID "
			+ "WHERE i.employeeID IN :employee " 
			+ "AND h.startDate >= :startDate ORDER BY h.startDate";
	
	private static final String SEL_PERSONNEL_ASSESMENT_BY_EMPLOYEES = "SELECT i FROM PpedtPerAssessmentItem i "
			+ "INNER JOIN PeedtPerAssessmentHist h ON i.ppedtPerAssessmentItemPk.historyID = h.peedtPerAssessmentHistPk.historyID "
			+ "WHERE i.employeeID = :employee " 
			+ "ORDER BY h.startDate";

	/*
	 * ドメイン [人事評価] を取得する
	 */
	@Override
	public List<PersonnelAssessment> getPersonnelAssessmentByEmployeeIds(List<String> employeeIds,
			GeneralDate startDate) {
		return this.queryProxy()
				.query(SEL_PERSONNEL_ASSESMENT_BY_EMPLOYEES_AND_START_DATE, PpedtPerAssessmentItem.class)
				.setParameter("employee", employeeIds)
				.setParameter("startDate", startDate)
				.getList(e -> PersonnelAssessment.builder()
						.companyID(e.companyID)
						.employeeID(e.employeeID)
						.comprehensiveEvaluation(e.comprehensiveEvaluation)
						.historyID(e.ppedtPerAssessmentItemPk.historyID)
						.build());
	}

	@Override
	public List<PersonnelAssessment> getPersonnelAssessmentByEmployeeId(String employeeId) {
		return this.queryProxy()
				.query(SEL_PERSONNEL_ASSESMENT_BY_EMPLOYEES, PpedtPerAssessmentItem.class)
				.setParameter("employee", employeeId)
				.getList(e -> PersonnelAssessment.builder()
						.companyID(e.companyID)
						.employeeID(e.employeeID)
						.comprehensiveEvaluation(e.comprehensiveEvaluation)
						.historyID(e.ppedtPerAssessmentItemPk.historyID)
						.build());
	}

}
