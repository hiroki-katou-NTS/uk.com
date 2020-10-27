package nts.uk.ctx.at.record.infra.repository.workrecord.worktime;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.worktime.repository.TimeLeavingWorkRepository;

@Stateless
public class JpaTimeLeavingWorkRepository extends JpaRepository implements TimeLeavingWorkRepository {

	private static final String REMOVE_BY_EMPLOYEE;

	static {
		StringBuilder builderString = new StringBuilder();
		builderString.append("DELETE a ");
		builderString.append("FROM KrcdtDayTsAtdStmp a ");
		builderString.append("WHERE a.krcdtDayTsAtdStmpPK.employeeId = :employeeId ");
		builderString.append("AND a.krcdtDayTsAtdStmpPK.ymd = :ymd ");
		REMOVE_BY_EMPLOYEE = builderString.toString();
	}

	@Override
	public void delete(String employeeId, GeneralDate ymd) {
		this.getEntityManager().createQuery(REMOVE_BY_EMPLOYEE).setParameter("employeeId", employeeId)
				.setParameter("ymd", ymd).executeUpdate();
	}

}
