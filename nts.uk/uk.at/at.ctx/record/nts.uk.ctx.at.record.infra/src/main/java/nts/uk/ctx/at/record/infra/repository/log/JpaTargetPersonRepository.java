package nts.uk.ctx.at.record.infra.repository.log;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.workrecord.log.ComplStateOfExeContents;
import nts.uk.ctx.at.record.dom.workrecord.log.TargetPerson;
import nts.uk.ctx.at.record.dom.workrecord.log.TargetPersonRepository;
import nts.uk.ctx.at.record.dom.workrecord.log.enums.EmployeeExecutionStatus;
import nts.uk.ctx.at.record.dom.workrecord.log.enums.ExecutionContent;
import nts.uk.ctx.at.record.infra.entity.log.KrcmtEmpExeTarget;

@Stateless
public class JpaTargetPersonRepository extends JpaRepository implements TargetPersonRepository {

	private final String SELECT_FROM_TARGET = "SELECT c FROM KrcmtEmpExeTarget c ";
	private final String SELECT_ALL_TARGET = SELECT_FROM_TARGET
			+ " WHERE c.krcmtEmpExeTargetPK.employeeId = :employeeId ";
	private final String SELECT_TARGET_BY_ID = SELECT_ALL_TARGET
			+ " AND c.krcmtEmpExeTargetPK.empCalAndSumExecLogID = :empCalAndSumExecLogID ";

	private final String SELECT_TARGET_PERSON = SELECT_FROM_TARGET
			+ " WHERE c.krcmtEmpExeTargetPK.empCalAndSumExecLogID = :empCalAndSumExecLogID ";


	
	private final String SELECT_BY_LOG_ID = SELECT_FROM_TARGET 
			+ "WHERE c.krcmtEmpExeTargetPK.empCalAndSumExecLogID = :empCalAndSumExecLogID";
	
	private TargetPerson toDomain(KrcmtEmpExeTarget entity) {
		return new TargetPerson(entity.krcmtEmpExeTargetPK.employeeId, entity.krcmtEmpExeTargetPK.empCalAndSumExecLogID,
				new ComplStateOfExeContents(EnumAdaptor.valueOf(entity.executionContent, ExecutionContent.class),
						EnumAdaptor.valueOf(entity.executionState, EmployeeExecutionStatus.class)));
	}

	@Override
	public List<TargetPerson> getAllTargetPerson(String employeeID) {
		List<TargetPerson> data = this.queryProxy().query(SELECT_ALL_TARGET, KrcmtEmpExeTarget.class)
				.setParameter("employeeId", employeeID).getList(c -> toDomain(c));
		return data;
	}

	@Override
	public Optional<TargetPerson> getTargetPersonByID(String employeeID, long empCalAndSumExecLogId) {
		Optional<TargetPerson> data = this.queryProxy().query(SELECT_TARGET_BY_ID, KrcmtEmpExeTarget.class)
				.setParameter("employeeId", employeeID).setParameter("empCalAndSumExecLogID", empCalAndSumExecLogId)
				.getSingle(c -> toDomain(c));
		return data;
	}

	@Override
	public List<TargetPerson> getTargetPersonById(long empCalAndSumExecLogId) {
		return this.queryProxy().query(SELECT_TARGET_PERSON, KrcmtEmpExeTarget.class)
				.setParameter("empCalAndSumExecLogID", empCalAndSumExecLogId).getList(f -> toDomain(f));
		}

	@Override
	public List<TargetPerson> getByempCalAndSumExecLogID(long empCalAndSumExecLogID) {
		List<TargetPerson> data = this.queryProxy().query(SELECT_BY_LOG_ID , KrcmtEmpExeTarget.class)
				.setParameter("empCalAndSumExecLogID", empCalAndSumExecLogID)
				.getList(c -> toDomain(c));
		return data;
	}

}
