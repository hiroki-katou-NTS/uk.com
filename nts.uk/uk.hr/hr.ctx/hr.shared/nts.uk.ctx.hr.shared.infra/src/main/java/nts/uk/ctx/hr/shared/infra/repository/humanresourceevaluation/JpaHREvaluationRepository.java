package nts.uk.ctx.hr.shared.infra.repository.humanresourceevaluation;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.DbConsts;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.hr.shared.dom.personalinfo.humanresourceevaluation.HumanResourceEvaluationRepository;
import nts.uk.ctx.hr.shared.dom.personalinfo.humanresourceevaluation.PersonnelAssessment;
import nts.uk.ctx.hr.shared.dom.personalinfo.humanresourceevaluation.PersonnelAssessmentResults;
import nts.uk.ctx.hr.shared.infra.entity.humanresourceevaluation.PeedtPerAssessmentHist;
import nts.uk.ctx.hr.shared.infra.entity.humanresourceevaluation.PpedtPerAssessmentItem;

/**
 * @author anhdt
 *
 */
@Stateless
public class JpaHREvaluationRepository extends JpaRepository implements HumanResourceEvaluationRepository {

	private static final String SEL_BY_EMPLOYEES_AND_START_DATE = "SELECT i, h FROM PpedtPerAssessmentItem i INNER JOIN PeedtPerAssessmentHist h "
			+ "ON i.ppedtPerAssessmentItemPk.historyID = h.peedtPerAssessmentHistPk.historyID "
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
	public List<PersonnelAssessmentResults> getPersonnelAssessmentByEmployeeIds(List<String> employeeIds, GeneralDate startDate) {
		
		if (employeeIds.isEmpty()) {
			return new ArrayList<>();
		}
		
		List<PersonnelAssessmentResults> result = new ArrayList<>();
		
		CollectionUtil.split(employeeIds, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, d -> {
			result.addAll(this.queryProxy().query(SEL_BY_EMPLOYEES_AND_START_DATE, Object[].class)
											.setParameter("employee", d)
											.setParameter("startDate", startDate)
											.getList(c->joinObjectToDomain(c)));
		});
		
		return result;
	}
	
	private PersonnelAssessmentResults joinObjectToDomain(Object[] entity) {
		PpedtPerAssessmentItem item = (PpedtPerAssessmentItem) entity[0];
		PeedtPerAssessmentHist hist = (PeedtPerAssessmentHist) entity[1];
		return new PersonnelAssessmentResults(item.companyID, item.employeeID, hist.startDate, hist.endDate, item.comprehensiveEvaluation);
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
