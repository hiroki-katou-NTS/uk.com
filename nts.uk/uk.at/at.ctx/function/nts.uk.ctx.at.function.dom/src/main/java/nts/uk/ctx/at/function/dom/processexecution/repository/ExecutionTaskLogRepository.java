package nts.uk.ctx.at.function.dom.processexecution.repository;

import java.util.List;

import nts.uk.ctx.at.function.dom.processexecution.executionlog.ExecutionTaskLog;

public interface ExecutionTaskLogRepository {
	// get list
	public List<ExecutionTaskLog> getAllByCidExecCdExecId(String companyId, String execItemCd, String execId);
	
	// insert
	public void insertAll(String companyId, String execItemCd, String execId, List<ExecutionTaskLog> taskLogList);
	
	// update
	public void updateAll(String companyId, String execItemCd, String execId, List<ExecutionTaskLog> taskLogList);
	
	// remove
	public void removeAll(String companyId, String execItemCd, String execId, List<ExecutionTaskLog> taskLogList);
}
