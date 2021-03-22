package nts.uk.ctx.at.function.dom.processexecution.repository;

import java.util.List;
import java.util.Optional;

import nts.uk.ctx.at.function.dom.processexecution.tasksetting.ExecutionTaskSetting;

public interface ExecutionTaskSettingRepository {
	// get list
	public Optional<ExecutionTaskSetting> getByCidAndExecCd(String companyId, String execItemCd);
	
	public List<ExecutionTaskSetting> getByCidAndExecItemCd(String companyId, List<String> execItemCds);
	
	// insert
	public void insert(ExecutionTaskSetting domain);
	
	// update
	public void update(ExecutionTaskSetting domain);
	
	// remove
	public void remove(String companyId, String execItemCd);
	
	// update enabledSetting
	public void update(String companyId, String execItemCd, boolean enabledSetting);
}
