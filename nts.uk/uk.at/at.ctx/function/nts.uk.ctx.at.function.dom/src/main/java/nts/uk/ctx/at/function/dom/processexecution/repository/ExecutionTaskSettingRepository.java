package nts.uk.ctx.at.function.dom.processexecution.repository;

import java.util.Optional;

import nts.uk.ctx.at.function.dom.processexecution.tasksetting.ExecutionTaskSetting;

public interface ExecutionTaskSettingRepository {
	// get list
	public Optional<ExecutionTaskSetting> getByCidAndExecCd(String companyId, String execItemCd);
	
	// insert
	public void insert(ExecutionTaskSetting domain);
	
	// update
	public void update(ExecutionTaskSetting domain);
	
	// remove
	public void remove(String companyId, String execItemCd);
		
}
