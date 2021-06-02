package nts.uk.ctx.at.function.dom.processexecution.createfromupdateautorunerror;

import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.function.dom.adapter.employeemanage.EmployeeManageAdapter;
import nts.uk.ctx.at.function.dom.adapter.toppagealarmpub.DeleteInfoAlarmImport;
import nts.uk.ctx.at.function.dom.adapter.toppagealarmpub.TopPageAlarmAdapter;
import nts.uk.ctx.at.function.dom.adapter.toppagealarmpub.TopPageAlarmImport;
import nts.uk.ctx.at.function.dom.processexecution.executionlog.ProcessExecutionLogManage;
import nts.uk.ctx.at.function.dom.processexecution.repository.ProcessExecutionLogManageRepository;

@AllArgsConstructor
public class DefaultRequireImpl implements CreateFromUpdateAutoRunError.Require {
	
	private ProcessExecutionLogManageRepository processExecutionLogManageRepository;
	
	private EmployeeManageAdapter employeeManageAdapter;
	
	private TopPageAlarmAdapter topPageAlarmAdapter;

	@Override
	public List<ProcessExecutionLogManage> getAutorunItemsWithErrors(String companyId) {
		return processExecutionLogManageRepository.getAutorunItemsWithErrors(companyId);
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
