package nts.uk.ctx.at.record.infra.repository.approvalmanagement;

import java.util.List;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.approvalmanagement.ApprovalStatusOfDailyPerfor;
import nts.uk.ctx.at.record.dom.approvalmanagement.repository.ApprovalStatusOfDailyPerforRepository;
import nts.uk.ctx.at.record.infra.entity.approvalmanagement.KrcdtDaiApprovalStatus;

@Stateless
public class JpaApprovalStatusOfDailyPerforRepository extends JpaRepository
		implements ApprovalStatusOfDailyPerforRepository {

	private static final String REMOVE_BY_EMPLOYEE;

	private static final String DEL_BY_LIST_KEY;

	static {
		StringBuilder builderString = new StringBuilder();
		builderString.append("DELETE ");
		builderString.append("FROM KrcdtDaiApprovalStatus a ");
		builderString.append("WHERE a.krcdtDaiApprovalPK.employeeId = :employeeId ");
		builderString.append("AND a.krcdtDaiApprovalPK.ymd = :ymd ");
		REMOVE_BY_EMPLOYEE = builderString.toString();

		builderString = new StringBuilder();
		builderString.append("DELETE ");
		builderString.append("FROM KrcdtDaiApprovalStatus a ");
		builderString.append("WHERE a.krcdtDaiApprovalPK.employeeId IN :employeeIds ");
		builderString.append("AND a.krcdtDaiApprovalPK.ymd IN :ymds ");
		DEL_BY_LIST_KEY = builderString.toString();
	}

	@Override
	public void delete(String employeeId, GeneralDate ymd) {
		this.getEntityManager().createQuery(REMOVE_BY_EMPLOYEE).setParameter("employeeId", employeeId)
				.setParameter("ymd", ymd).executeUpdate();
		this.getEntityManager().flush();
	}

	@Override
	public void deleteByListEmployeeId(List<String> employeeIds, List<GeneralDate> ymds) {
		this.getEntityManager().createQuery(DEL_BY_LIST_KEY).setParameter("employeeIds", employeeIds)
				.setParameter("ymds", ymds).executeUpdate();
	}

	@Override
	public void insert(ApprovalStatusOfDailyPerfor approvalStatusOfDailyPerfor) {
		this.commandProxy().insert(KrcdtDaiApprovalStatus.toEntity(approvalStatusOfDailyPerfor));
		this.getEntityManager().flush();
	}

}
