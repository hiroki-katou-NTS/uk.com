package nts.uk.ctx.hr.develop.infra.repository.empregulationhistory;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.hr.develop.dom.empregulationhistory.algorithm.EmploymentRegulationHistoryInterface;

@Stateless
public class EmploymentRegulationHistoryInterfaceImpl extends JpaRepository implements EmploymentRegulationHistoryInterface {

	private static final String SELECT_HIS_ID_BY_DATE = "SELECT c.historyId FROM JshmtEmpRegHistory c "
			+ "WHERE c.startDate <= :baseDate "
			+ "AND c.endDate >= :baseDate "
			+ "AND c.cId = :cId ";
	
	@Override
	public Optional<String> getHistoryIdByDate(String cId, GeneralDate baseDate) {
		return this.queryProxy().query(SELECT_HIS_ID_BY_DATE, String.class)
			.setParameter("cId", cId)
			.setParameter("baseDate", baseDate)
			.getSingle();
	}

}
