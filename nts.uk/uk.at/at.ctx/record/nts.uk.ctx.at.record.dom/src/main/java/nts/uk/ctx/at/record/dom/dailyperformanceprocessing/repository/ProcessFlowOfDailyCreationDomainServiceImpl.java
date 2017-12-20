package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.AsyncCommandHandlerContext;
import nts.arc.task.data.TaskDataSetter;
import nts.uk.ctx.at.record.dom.adapter.person.PersonInfoAdapter;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.output.ExecutionAttr;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.CreateDailyResultDomainServiceImpl.ProcessState;
import nts.uk.ctx.at.record.dom.workrecord.log.EmpCalAndSumExeLog;
import nts.uk.ctx.at.record.dom.workrecord.log.EmpCalAndSumExeLogRepository;
import nts.uk.ctx.at.record.dom.workrecord.log.ErrMessageInfo;
import nts.uk.ctx.at.record.dom.workrecord.log.ErrMessageInfoRepository;
import nts.uk.ctx.at.record.dom.workrecord.log.ExecutionLog;
import nts.uk.ctx.at.record.dom.workrecord.log.TargetPerson;
import nts.uk.ctx.at.record.dom.workrecord.log.TargetPersonRepository;
import nts.uk.ctx.at.record.dom.workrecord.log.enums.ErrorPresent;
import nts.uk.ctx.at.record.dom.workrecord.log.enums.ExeStateOfCalAndSum;
import nts.uk.ctx.at.record.dom.workrecord.log.enums.ExecutionContent;
import nts.uk.ctx.at.record.dom.workrecord.log.enums.ExecutionStatus;
import nts.uk.ctx.at.record.dom.workrecord.log.enums.ExecutionType;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

@Stateless
public class ProcessFlowOfDailyCreationDomainServiceImpl implements ProcessFlowOfDailyCreationDomainService{
	
	@Inject
	private EmpCalAndSumExeLogRepository empCalAndSumExeLogRepository;
	
	@Inject
	private TargetPersonRepository targetPersonRepository;
	
	@Inject
	private CreateDailyResultDomainService createDailyResultDomainService;
	
	@Inject
	private ErrMessageInfoRepository errMessageInfoRepository;
	
//	@Inject
//	private PersonInfoAdapter personInfoAdapter;

	@Override
	public <C> void processFlowOfDailyCreation(AsyncCommandHandlerContext<C> asyncContext, ExecutionAttr executionAttr, DatePeriod periodTime,
			String empCalAndSumExecLogID) {
		
		TaskDataSetter dataSetter = asyncContext.getDataSetter();
		dataSetter.setData("dailyCreateCount", 0);
		dataSetter.setData("dailyCreateStatus", ExecutionStatus.PROCESSING.nameId);
		dataSetter.setData("dailyCreateHasError", " ");
		
		LoginUserContext login = AppContexts.user();
		String companyId = login.companyId();
//		String employeeID = AppContexts.user().employeeId();

		//①実行方法を取得する
		Optional<EmpCalAndSumExeLog> empCalAndSumExeLog = this.empCalAndSumExeLogRepository.getByEmpCalAndSumExecLogID(empCalAndSumExecLogID);
		
		Optional<ExecutionLog> executionLog = empCalAndSumExeLog.get().getExecutionLogs().stream().filter(item -> item.getExecutionContent() == ExecutionContent.DAILY_CREATION).findFirst();
		
		//②対象者を取得する
		List<TargetPerson> targetPersons = new ArrayList<>();
		//パラメータ「実行区分」＝手動　の場合 (Manual)
		if(executionAttr == ExecutionAttr.MANUAL){
			targetPersons = this.targetPersonRepository.getTargetPersonById(empCalAndSumExecLogID);
		} 
		//パラメータ「実行区分」＝自動　の場合 (Auto)
		else if(executionAttr == ExecutionAttr.AUTO){
			// アルゴリズム「社員IDから個人社員基本情報を取得」を実行する
			//this.personInfoAdapter.getListPersonInfo(listSid);
		}
		
		// get List personId
		List<String> employeeIdList = targetPersons.stream().map(f -> {
			return f.getEmployeeId();
		}).collect(Collectors.toList());
		
		dataSetter.setData("dailyCreateTotal", targetPersons.size());
		
		ProcessState finalStatus = ProcessState.SUCCESS;
		
		//各処理の実行		
		finalStatus = this.createDailyResultDomainService.createDailyResult(asyncContext, employeeIdList, periodTime, executionAttr, companyId, empCalAndSumExecLogID, executionLog);
		
//		if (finalStatus == ProcessState.SUCCESS) {
//			// next Step : 日別実績の計算
//			// fake status
//			finalStatus = ProcessState.SUCCESS;
//			if(finalStatus == ProcessState.SUCCESS){
//				// next Step : 申請の反映
//			}
//			
//		}
		
		// ドメインモデル「就業計算と修正実行ログ」を更新する
		// 就業計算と集計実行ログ．実行状況　←　実行中止
		if (finalStatus == ProcessState.INTERRUPTION) {
			this.empCalAndSumExeLogRepository.updateStatus(empCalAndSumExecLogID, ExeStateOfCalAndSum.STOPPING.value);
		} else {
			// 完了処理 (Xử lý hoàn thành)
			ExeStateOfCalAndSum executionStatus = this.updateExecutionState(dataSetter, empCalAndSumExecLogID);
			this.empCalAndSumExeLogRepository.updateStatus(empCalAndSumExecLogID, executionStatus.value);
		}
		
		dataSetter.updateData("dailyCreateStatus", ExecutionStatus.DONE.nameId);
		
	}
	
	private ExeStateOfCalAndSum updateExecutionState(TaskDataSetter dataSetter, String empCalAndSumExecLogID){
		
		// 0 : 完了
		// 1 : 完了（エラーあり）
		ExeStateOfCalAndSum executionStatus = ExeStateOfCalAndSum.DONE;
		
		List<ErrMessageInfo> errMessageInfos = this.errMessageInfoRepository.getAllErrMessageInfoByEmpID(empCalAndSumExecLogID);
		List<String> errorMessage = errMessageInfos.stream().map(error -> {
			return error.getMessageError().v();
		}).collect(Collectors.toList());
		if (errorMessage.isEmpty()) {
			executionStatus = ExeStateOfCalAndSum.DONE;
			dataSetter.updateData("dailyCreateHasError", ErrorPresent.NO_ERROR.nameId);

		} else {
			executionStatus = ExeStateOfCalAndSum.DONE_WITH_ERROR;
			dataSetter.updateData("dailyCreateHasError", ErrorPresent.HAS_ERROR.nameId);
		}
		
		return executionStatus;
	}

	
	
}
