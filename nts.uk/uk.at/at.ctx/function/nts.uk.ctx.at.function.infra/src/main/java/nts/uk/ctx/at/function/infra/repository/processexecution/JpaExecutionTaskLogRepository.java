package nts.uk.ctx.at.function.infra.repository.processexecution;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.function.dom.processexecution.executionlog.ExecutionTaskLog;
import nts.uk.ctx.at.function.dom.processexecution.repository.ExecutionTaskLogRepository;
import nts.uk.ctx.at.function.infra.entity.processexecution.KfnmtExecutionTaskLog;
import nts.uk.ctx.at.function.infra.entity.processexecution.KfnmtExecutionTaskLogPK;

@Stateless
public class JpaExecutionTaskLogRepository extends JpaRepository
		implements ExecutionTaskLogRepository {

	/**
	 * insert task log list
	 */
	@Override
	public void insertAll(String companyId, String execItemCd, String execId, List<ExecutionTaskLog> taskLogList) {
		List<KfnmtExecutionTaskLog> entityList =
				taskLogList
					.stream()
						.map(task -> KfnmtExecutionTaskLog.toEntity(companyId,
																	execItemCd,
																	execId,
																	task)).collect(Collectors.toList());
		this.commandProxy().insertAll(entityList);
	}

	/**
	 * update task log list
	 */
	@Override
	public void updateAll(String companyId, String execItemCd, String execId, List<ExecutionTaskLog> taskLogList) {
		List<KfnmtExecutionTaskLog> entityList =
				taskLogList
					.stream()
						.map(task -> {
							KfnmtExecutionTaskLogPK pk = new KfnmtExecutionTaskLogPK(companyId, execItemCd, execId, task.getProcExecTask().value);
							Optional<KfnmtExecutionTaskLog> entityOpt = this.queryProxy().find(pk, KfnmtExecutionTaskLog.class);
							if (entityOpt.isPresent()) {
								KfnmtExecutionTaskLog entity = entityOpt.get();
								entity.status = task.getStatus().value;
								return entity;
							}
							return null;
							
						}).collect(Collectors.toList());
																	
		this.commandProxy().updateAll(entityList);
	}
}
