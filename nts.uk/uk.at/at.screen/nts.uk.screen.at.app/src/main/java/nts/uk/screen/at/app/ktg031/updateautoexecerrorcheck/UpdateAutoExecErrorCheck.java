package nts.uk.screen.at.app.ktg031.updateautoexecerrorcheck;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.function.dom.adapter.employeemanage.EmployeeManageAdapter;
import nts.uk.ctx.at.function.dom.adapter.toppagealarmpub.TopPageAlarmAdapter;
import nts.uk.ctx.at.function.dom.dailyworkschedule.scrA.RoleExportRepoAdapter;
import nts.uk.ctx.at.function.dom.processexecution.ProcessExecutionService;
import nts.uk.ctx.at.function.dom.processexecution.createfromupdateexcecerror.CreateFromUpdateExecError;
import nts.uk.ctx.at.function.dom.processexecution.createfromupdateexcecerror.DefaultRequireImpl;
import nts.uk.ctx.at.function.dom.processexecution.repository.ExecutionTaskSettingRepository;
import nts.uk.ctx.at.function.dom.processexecution.repository.ProcessExecutionLogManageRepository;

/**
 * UKDesign.UniversalK.就業.KTG_ウィジェット.KTG031_トップページアラーム.トップページアラームver4.A:トップページアラーム.メニュー別OCD.更新処理自動実行異常チェックを起動.更新処理自動実行異常チェックを起動
 */
@Stateless
public class UpdateAutoExecErrorCheck {

	@Inject
	private RoleExportRepoAdapter roleExportRepoAdapter;

	@Inject
	private ProcessExecutionLogManageRepository processExecutionLogManageRepository;

	@Inject
	private ExecutionTaskSettingRepository executionTaskSettingRepository;

	@Inject
	private ProcessExecutionService processExecutionService;

	@Inject
	private EmployeeManageAdapter employeeManageAdapter;

	@Inject
	private TopPageAlarmAdapter topPageAlarmAdapter;

	public void check(String cid) {
		// 1: get() 就業担当者か（boolean)
		if (roleExportRepoAdapter.getRoleWhetherLogin().isEmployeeCharge()) {
			// [就業担当者か＝true]: <call>()
			DefaultRequireImpl rq = new DefaultRequireImpl(processExecutionLogManageRepository,
					executionTaskSettingRepository, processExecutionService, employeeManageAdapter,
					topPageAlarmAdapter);
			CreateFromUpdateExecError.create(rq, cid);
		}

	}

}
