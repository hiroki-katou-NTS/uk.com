package nts.uk.ctx.at.record.infra.repository.workrecord.identificationstatus;

import java.util.List;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.workrecord.identificationstatus.repository.IdentificationRepository;

@Stateless
public class JpaIdentificationRepository extends JpaRepository implements IdentificationRepository {

	private static final String REMOVE_BY_EMPLOYEE;
	
	private static final String DEL_BY_LIST_KEY;

	static {
		StringBuilder builderString = new StringBuilder();
		builderString.append("SELECT a ");
		builderString.append("FROM KrcdtIdentificationStatus a ");
		builderString.append("WHERE a.krcdtIdentificationStatusPK.employeeId = :employeeId ");
		builderString.append("AND a.processingYmd = :ymd ");
		REMOVE_BY_EMPLOYEE = builderString.toString();
		
		builderString = new StringBuilder();
		builderString.append("DELETE ");
		builderString.append("FROM KrcdtIdentificationStatus a ");
		builderString.append("WHERE WHERE a.krcdtIdentificationStatusPK.employeeId IN :employeeIds ");
		builderString.append("AND a.processingYmd IN :processingYmds ");
		DEL_BY_LIST_KEY = builderString.toString();
	}

	@Override
	public void delete(String employeeId, GeneralDate processingYmd) {
		this.getEntityManager().createQuery(REMOVE_BY_EMPLOYEE).setParameter("employeeId", employeeId)
				.setParameter("ymd", processingYmd).executeUpdate();
	}

	@Override
	public void deleteByListEmployeeId(List<String> employeeIds, List<GeneralDate> processingYmds) {
		this.getEntityManager().createQuery(DEL_BY_LIST_KEY).setParameter("employeeIds", employeeIds)
		.setParameter("processingYmds", processingYmds).executeUpdate();
	}

}
