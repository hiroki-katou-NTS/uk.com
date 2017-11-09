package nts.uk.ctx.at.record.infra.repository.approvalmanagement;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.approvalmanagement.repository.ApprovalStatusOfDailyPerforRepository;

@Stateless
public class JpaApprovalStatusOfDailyPerforRepository extends JpaRepository
		implements ApprovalStatusOfDailyPerforRepository {

	private static final String REMOVE_BY_EMPLOYEE;

	static {
		StringBuilder builderString = new StringBuilder();
		builderString.append("SELECT a ");
		builderString.append("FROM KrcdtDaiApproval a ");
		builderString.append("WHERE a.krcdtDaiApprovalPK.employeeId = :employeeId ");
		builderString.append("AND a.krcdtDaiApprovalPK.ymd = :ymd ");
		REMOVE_BY_EMPLOYEE = builderString.toString();
	}

	@Override
	public void delete(String employeeId, GeneralDate ymd) {
		this.getEntityManager().createQuery(REMOVE_BY_EMPLOYEE).setParameter("employeeId", employeeId)
				.setParameter("ymd", ymd).executeUpdate();
	}

}
