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
	public void updateExecutionDate(String empCalAndSumExecLogID, GeneralDateTime executionStartDate,
			GeneralDateTime executionEndDate, GeneralDateTime dailyCreateStartTime, GeneralDateTime dailyCreateEndTime,
			GeneralDateTime dailyCalculateStartTime, GeneralDateTime dailyCalculateEndTime,
			GeneralDateTime reflectApprovalStartTime, GeneralDateTime reflectApprovalEndTime,
			GeneralDateTime monthlyAggregateStartTime, GeneralDateTime monthlyAggregateEndTime , int stopped) {
		
		List<KrcdtExecutionLog> krcdtExecutionLogs = this.queryProxy().query(SELECT_BY_CAL_AND_SUM_EXE_ID, KrcdtExecutionLog.class)
		.setParameter("empCalAndSumExecLogID", empCalAndSumExecLogID).getList();
		
		Optional<KrcdtExecutionLog> createLog = krcdtExecutionLogs.stream().filter(item -> item.krcdtExecutionLogPK.executionContent == 0).findFirst();
		Optional<KrcdtExecutionLog> calculateLog = krcdtExecutionLogs.stream().filter(item -> item.krcdtExecutionLogPK.executionContent == 1).findFirst();
		Optional<KrcdtExecutionLog> log = krcdtExecutionLogs.stream().filter(item -> item.krcdtExecutionLogPK.executionContent == 2).findFirst();
		Optional<KrcdtExecutionLog> monthlyLog = krcdtExecutionLogs.stream().filter(item -> item.krcdtExecutionLogPK.executionContent == 3).findFirst();

		if (createLog.isPresent()) {
			if (dailyCreateStartTime != null) {
				createLog.get().executionStartDate = dailyCreateStartTime;				
			} else {
				createLog.get().executionStartDate = executionEndDate;
			}
			
			if (dailyCreateEndTime != null) {
				createLog.get().executionEndDate = dailyCreateEndTime;
			} else {
				createLog.get().executionEndDate = executionEndDate;
			}
			
//			if (stopped == 1) {
//				createLog.get().executionEndDate = executionEndDate;
//				dailyCreateEndTime = executionEndDate;
//			}
			
			this.commandProxy().update(createLog.get());
			this.getEntityManager().flush();
		}
		
		if (calculateLog.isPresent()) {
			
			if (dailyCalculateStartTime != null) {
				calculateLog.get().executionStartDate = dailyCalculateStartTime;
			} else {
				calculateLog.get().executionStartDate = executionEndDate;
			}
			
			if (dailyCalculateEndTime != null) {
				calculateLog.get().executionEndDate = dailyCalculateEndTime;
			} else {
				calculateLog.get().executionEndDate = executionEndDate;
			}
			
//			if (stopped == 1 && dailyCalculateStartTime == null) {
//				calculateLog.get().executionStartDate = dailyCreateEndTime;
//				calculateLog.get().executionEndDate = dailyCreateEndTime;
//			}
			
			this.commandProxy().update(calculateLog.get());
			this.getEntityManager().flush();
		}

		if (log.isPresent()) {
			if (reflectApprovalStartTime != null) {
				log.get().executionStartDate = reflectApprovalStartTime;
			} else {
				log.get().executionStartDate = executionEndDate;
			}
			
			if (reflectApprovalEndTime != null) {
				log.get().executionEndDate = reflectApprovalEndTime;
			} else {
				log.get().executionEndDate = executionEndDate;
			}
			
//			if (stopped == 1 &&  reflectApprovalStartTime == null) {
//				if (dailyCalculateEndTime != null) {
//					log.get().executionStartDate = dailyCalculateEndTime;
//					log.get().executionEndDate = dailyCalculateEndTime;
//				} else if (!calculateLog.isPresent() && createLog.isPresent()) {
//					log.get().executionStartDate = dailyCreateEndTime;
//					log.get().executionEndDate = dailyCreateEndTime;
//				}
//			}			
			
			this.commandProxy().update(log.get());
			this.getEntityManager().flush();
		}
		
		if (monthlyLog.isPresent()) {
			if (monthlyAggregateStartTime != null) {
				monthlyLog.get().executionStartDate = monthlyAggregateStartTime;
			} else {
				monthlyLog.get().executionStartDate = executionEndDate;
			}
			
			if (monthlyAggregateEndTime != null) {
				monthlyLog.get().executionEndDate = monthlyAggregateEndTime;
			} else {
				monthlyLog.get().executionEndDate = executionEndDate;
			}
			
//			if (stopped == 1 && monthlyAggregateStartTime == null) {
//				if (reflectApprovalEndTime != null) {
//					monthlyLog.get().executionStartDate = reflectApprovalEndTime;
//					monthlyLog.get().executionEndDate = reflectApprovalEndTime;
//				} else if(reflectApprovalEndTime == null && dailyCalculateEndTime != null){
//					monthlyLog.get().executionStartDate = dailyCalculateEndTime;
//					monthlyLog.get().executionEndDate = dailyCalculateEndTime;
//				} else if(reflectApprovalEndTime == null && dailyCalculateEndTime == null){
//					monthlyLog.get().executionStartDate = dailyCreateEndTime;
//					monthlyLog.get().executionEndDate = dailyCreateEndTime;
//				}
//			}
			
			this.commandProxy().update(monthlyLog.get());
			this.getEntityManager().flush();
		}
	}

}
