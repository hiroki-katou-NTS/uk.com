package nts.uk.ctx.at.function.dom.processexecution.createfromupdateexcecerror;

import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.function.dom.adapter.employeemanage.EmployeeManageAdapter;
import nts.uk.ctx.at.function.dom.adapter.toppagealarmpub.DeleteInfoAlarmImport;
import nts.uk.ctx.at.function.dom.adapter.toppagealarmpub.TopPageAlarmAdapter;
import nts.uk.ctx.at.function.dom.adapter.toppagealarmpub.TopPageAlarmImport;
import nts.uk.ctx.at.function.dom.processexecution.ProcessExecutionService;
import nts.uk.ctx.at.function.dom.processexecution.UpdateProcessAutoExecution;
import nts.uk.ctx.at.function.dom.processexecution.executionlog.ProcessExecutionLogManage;
import nts.uk.ctx.at.function.dom.processexecution.repository.ExecutionTaskSettingRepository;
import nts.uk.ctx.at.function.dom.processexecution.repository.ProcessExecutionLogManageRepository;
import nts.uk.ctx.at.function.dom.processexecution.tasksetting.ExecutionTaskSetting;

@AllArgsConstructor
public class DefaultRequireImpl implements CreateFromUpdateExecError.Require {
	
	private ProcessExecutionLogManageRepository processExecutionLogManageRepository;
	
	private ExecutionTaskSettingRepository executionTaskSettingRepository;
	
	private ProcessExecutionService processExecutionService;
	
	private EmployeeManageAdapter employeeManageAdapter;
	
	private TopPageAlarmAdapter topPageAlarmAdapter;
	
	@Override
	public List<ProcessExecutionLogManage> getUpdateExecItemsWithErrors(String companyId) {
		return processExecutionLogManageRepository.getUpdateExecItemsWithErrors(companyId);
	}

	@Override
	public List<ExecutionTaskSetting> getByCid(String cid) {
		return executionTaskSettingRepository.getByCid(cid);
	}

	@Override
	public boolean isPassAverageExecTimeExceeded(String companyId, UpdateProcessAutoExecution updateProcessAutoExec,
			GeneralDateTime execStartDateTime) {
		return processExecutionService.isPassAverageExecTimeExceeded(companyId, updateProcessAutoExec, execStartDateTime);
	}

	@Override
	public GeneralDateTime processNextExecDateTimeCreation(ExecutionTaskSetting execTaskSet) {
		return processExecutionService.processNextExecDateTimeCreation(execTaskSet);
	}

	@Override
	public List<String> getListEmpID(String companyID, GeneralDate referenceDate) {
		return employeeManageAdapter.getListEmpID(companyID, referenceDate);
	}

	@Override
	public void createAlarmData(String companyId, List<TopPageAlarmImport> alarmInfos,
			Optional<DeleteInfoAlarmImport> delInfoOpt) {
		topPageAlarmAdapter.create(companyId, alarmInfos, delInfoOpt);
	}

}
