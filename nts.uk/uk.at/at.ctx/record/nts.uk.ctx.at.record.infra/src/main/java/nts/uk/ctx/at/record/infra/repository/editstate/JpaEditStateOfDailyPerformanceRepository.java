package nts.uk.ctx.at.record.infra.repository.editstate;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.editstate.repository.EditStateOfDailyPerformanceRepository;

@Stateless
public class JpaEditStateOfDailyPerformanceRepository extends JpaRepository
		implements EditStateOfDailyPerformanceRepository {

	private static final String REMOVE_BY_EMPLOYEE;

	static {
		StringBuilder builderString = new StringBuilder();
		builderString.append("SELECT a ");
		builderString.append("FROM KrcdtDailyRecEditSet a ");
		builderString.append("WHERE a.krcdtDailyRecEditSetPK.employeeId = :employeeId ");
		builderString.append("AND a.krcdtDailyRecEditSetPK.processingYmd = :ymd ");
		REMOVE_BY_EMPLOYEE = builderString.toString();
	}

	@Override
	public void delete(String employeeId, GeneralDate ymd) {
		this.getEntityManager().createQuery(REMOVE_BY_EMPLOYEE).setParameter("employeeId", employeeId)
				.setParameter("ymd", ymd).executeUpdate();
	}

}
