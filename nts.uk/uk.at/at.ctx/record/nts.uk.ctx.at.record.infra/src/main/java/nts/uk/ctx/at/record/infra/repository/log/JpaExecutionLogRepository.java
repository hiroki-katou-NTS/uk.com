package nts.uk.ctx.at.record.infra.repository.log;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.record.dom.workrecord.workperfor.dailymonthlyprocessing.ExecutionLog;
import nts.uk.ctx.at.record.dom.workrecord.workperfor.dailymonthlyprocessing.ExecutionLogRepository;
import nts.uk.ctx.at.record.infra.entity.log.KrcdtExecutionLog;

@Stateless
public class JpaExecutionLogRepository extends JpaRepository implements ExecutionLogRepository {

	private static final String SELECT_BY_CAL_AND_SUM_EXE_ID = "SELECT el FROM KrcdtExecutionLog el "
			+ " WHERE el.krcdtExecutionLogPK.empCalAndSumExecLogID = :empCalAndSumExecLogID";

	private static final String SELECT_BY_EXECUTION_LOG = "SELECT el FROM KrcdtExecutionLog el "
			+ " WHERE el.krcdtExecutionLogPK.empCalAndSumExecLogID = :empCalAndSumExecLogID AND el.krcdtExecutionLogPK.executionContent = :executionContent";

	@Override
	public Optional<ExecutionLog> getByExecutionContent(String empCalAndSumExecLogID, int executionContent) {
		Optional<ExecutionLog> optional = this.queryProxy().query(SELECT_BY_EXECUTION_LOG, KrcdtExecutionLog.class)
				.setParameter("empCalAndSumExecLogID", empCalAndSumExecLogID)
				.setParameter("executionContent", executionContent).getSingle(f -> f.toDomain());

		return optional;
	}

	@Override
	public void updateLogInfo(String empCalAndSumExecLogID, int executionContent, int processStatus) {
		Optional<KrcdtExecutionLog> krcdtExecutionLog = this.queryProxy()
				.query(SELECT_BY_EXECUTION_LOG, KrcdtExecutionLog.class)
				.setParameter("empCalAndSumExecLogID", empCalAndSumExecLogID)
				.setParameter("executionContent", executionContent).getSingle();
		if (krcdtExecutionLog.isPresent()) {
			krcdtExecutionLog.get().processStatus = processStatus;

			this.commandProxy().update(krcdtExecutionLog.get());
			this.getEntityManager().flush();

		}
	}

	@Override
	public List<ExecutionLog> getExecutionLogs(String empCalAndSumExecLogID) {
		return this.queryProxy().query(SELECT_BY_CAL_AND_SUM_EXE_ID, KrcdtExecutionLog.class)
				.setParameter("empCalAndSumExecLogID", empCalAndSumExecLogID).getList(c -> c.toDomain());

	}

	@Override
	public void addExecutionLog(ExecutionLog executionLog) {
		this.commandProxy().insert(KrcdtExecutionLog.toEntity(executionLog));
	}

	@Override
	public void addAllExecutionLog(List<ExecutionLog> listExecutionLog) {
		List<KrcdtExecutionLog> lstKrcdtExecutionLog = listExecutionLog.stream().map(c -> KrcdtExecutionLog.toEntity(c))
				.collect(Collectors.toList());
		this.commandProxy().insertAll(lstKrcdtExecutionLog);
	}

	@Override
	public void updateExecutionDate(String empCalAndSumExecLogID, GeneralDateTime executionStartDate, GeneralDateTime executionEndDate) {
		
		List<KrcdtExecutionLog> krcdtExecutionLogs = this.queryProxy().query(SELECT_BY_CAL_AND_SUM_EXE_ID, KrcdtExecutionLog.class)
		.setParameter("empCalAndSumExecLogID", empCalAndSumExecLogID).getList();
		
		Optional<KrcdtExecutionLog> createLog = krcdtExecutionLogs.stream().filter(item -> item.krcdtExecutionLogPK.executionContent == 0).findFirst();
		Optional<KrcdtExecutionLog> calculateLog = krcdtExecutionLogs.stream().filter(item -> item.krcdtExecutionLogPK.executionContent == 1).findFirst();
		Optional<KrcdtExecutionLog> log = krcdtExecutionLogs.stream().filter(item -> item.krcdtExecutionLogPK.executionContent == 2).findFirst();
		Optional<KrcdtExecutionLog> monthlyLog = krcdtExecutionLogs.stream().filter(item -> item.krcdtExecutionLogPK.executionContent == 3).findFirst();

		if (createLog.isPresent()) {
			createLog.get().executionStartDate = executionStartDate;
			createLog.get().executionEndDate = executionEndDate;
			
			this.commandProxy().update(createLog.get());
			this.getEntityManager().flush();
		}
		
		if (calculateLog.isPresent()) {
			calculateLog.get().executionStartDate = executionStartDate;
			calculateLog.get().executionEndDate = executionEndDate;
			
			this.commandProxy().update(calculateLog.get());
			this.getEntityManager().flush();
		}

		if (log.isPresent()) {
			log.get().executionStartDate = executionStartDate;
			log.get().executionEndDate = executionEndDate;
			
			this.commandProxy().update(log.get());
			this.getEntityManager().flush();
		}
		
		if (monthlyLog.isPresent()) {
			monthlyLog.get().executionStartDate = executionStartDate;
			monthlyLog.get().executionEndDate = executionEndDate;
			
			this.commandProxy().update(monthlyLog.get());
			this.getEntityManager().flush();
		}
	}

}
