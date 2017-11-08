package nts.uk.ctx.at.record.infra.repository.affiliationinformation;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.affiliationinformation.repository.AffiliationInforOfDailyPerforRepository;

@Stateless
public class JpaAffiliationInforOfDailyPerforRepository extends JpaRepository
		implements AffiliationInforOfDailyPerforRepository {
	
	private static final String REMOVE_BY_EMPLOYEE;

	static {
		StringBuilder builderString = new StringBuilder();
		builderString.append("SELECT a ");
		builderString.append("FROM KrcdtDaiAffiliationInf a ");
		builderString.append("WHERE a.krcdtDaiAffiliationInfPK.employeeId = :employeeId ");
		builderString.append("AND a.krcdtDaiAffiliationInfPK.ymd = :ymd ");
		REMOVE_BY_EMPLOYEE = builderString.toString();
	}

	@Override
	public void delete(String employeeId, GeneralDate ymd) {
		this.getEntityManager().createNamedQuery(REMOVE_BY_EMPLOYEE).setParameter("employeeId", employeeId)
		.setParameter("ymd", ymd).executeUpdate();
	}

}
