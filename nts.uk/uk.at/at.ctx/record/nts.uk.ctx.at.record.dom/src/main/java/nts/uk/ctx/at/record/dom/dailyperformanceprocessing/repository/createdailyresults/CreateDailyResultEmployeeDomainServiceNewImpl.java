package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.createdailyresults;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.layer.app.command.AsyncCommandHandlerContext;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.ExecutionTypeDaily;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.checkprocessed.CheckProcessed;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.checkprocessed.OutputCheckProcessed;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.checkprocessed.StatusOutput;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.createdailyoneday.CreateDailyOneDay;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.createdailyoneday.EmbossingExecutionFlag;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.createdailyoneday.deleteworkinfor.DeleteWorkInfor;
import nts.uk.ctx.at.record.dom.organization.EmploymentHistoryImported;
import nts.uk.ctx.at.record.dom.organization.adapter.EmploymentAdapter;
import nts.uk.ctx.at.record.dom.require.RecordDomRequireService;
import nts.uk.ctx.at.record.dom.workrecord.actuallock.DetermineActualResultLock;
import nts.uk.ctx.at.record.dom.workrecord.actuallock.LockStatus;
import nts.uk.ctx.at.record.dom.workrecord.actuallock.PerformanceType;
import nts.uk.ctx.at.record.dom.workrecord.workperfor.dailymonthlyprocessing.EmpCalAndSumExeLog;
import nts.uk.ctx.at.record.dom.workrecord.workperfor.dailymonthlyprocessing.EmpCalAndSumExeLogRepository;
import nts.uk.ctx.at.record.dom.workrecord.workperfor.dailymonthlyprocessing.enums.ExeStateOfCalAndSum;
import nts.uk.ctx.at.shared.dom.adapter.generalinfo.dtoimport.EmployeeGeneralInfoImport;
import nts.uk.ctx.at.shared.dom.calculationsetting.StampReflectionManagement;
import nts.uk.ctx.at.shared.dom.dailyperformanceprocessing.ErrMessageResource;
import nts.uk.ctx.at.shared.dom.dailyperformanceprocessing.output.PeriodInMasterList;
import nts.uk.ctx.at.shared.dom.scherec.closurestatus.ClosureStatusManagement;
import nts.uk.ctx.at.shared.dom.scherec.closurestatus.ClosureStatusManagementRepository;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;
import nts.uk.ctx.at.shared.dom.workrecord.workperfor.dailymonthlyprocessing.ErrMessageContent;
import nts.uk.ctx.at.shared.dom.workrecord.workperfor.dailymonthlyprocessing.ErrorMessageInfo;
import nts.uk.ctx.at.shared.dom.workrecord.workperfor.dailymonthlyprocessing.enums.ExecutionContent;
import nts.uk.ctx.at.shared.dom.workrule.closure.Closure;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.ClosureService;
import nts.uk.shr.com.history.DateHistoryItem;
import nts.uk.shr.com.i18n.TextResource;
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
@Stateless
public class CreateDailyResultEmployeeDomainServiceNewImpl implements CreateDailyResultEmployeeDomainServiceNew {

	@Inject
	private EmploymentAdapter employmentAdapter;

	@Inject
	private ClosureStatusManagementRepository closureStatusManagementRepository;

	@Inject
	private EmpCalAndSumExeLogRepository empCalAndSumExeLogRepository;

	@Inject
	private DetermineActualResultLock lockStatusService;

//	@Inject
//	private ClosureService closureService;

	@Inject
	private CheckProcessed checkProcessed;
	
	@Inject
	private DeleteWorkInfor deleteWorkInfor;
	
	@Inject
	private CreateDailyOneDay createDailyOneDay;
	
	@Inject
	private RegisterDailyWork registerDailyWork;
	
    @Inject
    private RecordDomRequireService requireService;

	@Override
	public OutputCreateDailyResult createDailyResultEmployee(AsyncCommandHandlerContext asyncContext, String employeeId,
			DatePeriod periodTimes, String companyId, Optional<EmpCalAndSumExeLog> empCalAndSumExeLog,
			boolean reCreateWorkType, boolean reCreateWorkPlace, boolean reCreateRestTime,
			EmployeeGeneralInfoImport employeeGeneralInfoImport,
			Optional<StampReflectionManagement> stampReflectionManagement,
			Map<String, Map<String, WorkingConditionItem>> mapWorkingConditionItem,
			Map<String, Map<String, DateHistoryItem>> mapDateHistoryItem, PeriodInMasterList periodInMasterList,
			ExecutionTypeDaily executionType, Optional<Boolean> checkLock) {
		// 空のエラー一覧を作る
		List<ErrorMessageInfo> listErrorMessageInfo = new ArrayList<>();

		// 取得した「集計期間」．開始日を処理日にする
		GeneralDate processingDate = periodTimes.start();

		// Imported（就業）「所属雇用履歴」を取得する
		List<EmploymentHistoryImported> listEmploymentHis = this.employmentAdapter.getEmpHistBySid(companyId,
				employeeId);
		if (listEmploymentHis.isEmpty()) {
			listErrorMessageInfo.add(
					new ErrorMessageInfo(companyId, employeeId, processingDate, ExecutionContent.DAILY_CREATION,
							new ErrMessageResource("010"), new ErrMessageContent(TextResource.localize("Msg_426"))));

			return new OutputCreateDailyResult(ProcessState.SUCCESS, listErrorMessageInfo);
		}
		// ドメインモデル「締め状態管理」を取得する
		Optional<ClosureStatusManagement> closureStatusManagement = this.closureStatusManagementRepository
				.getLatestByEmpId(employeeId);
//		if (!closureStatusManagement.isPresent()) {
//			listErrorMessageInfo.add(
//					new ErrorMessageInfo(companyId, employeeId, periodTimes.start(), ExecutionContent.DAILY_CREATION,
//							new ErrMessageResource("010"), new ErrMessageContent(TextResource.localize("Msg_426"))));
//
//			return new OutputCreateDailyResult(ProcessState.SUCCESS, listErrorMessageInfo);
//		}
		List<GeneralDate> listDayBetween = periodTimes.datesBetween();
		for (GeneralDate day : listDayBetween) {
			// 処理すべきかをチェックする
			OutputCheckProcessed outputCheckProcessed = checkProcessed.getCheckProcessed(day, listEmploymentHis);
			if (outputCheckProcessed.getStatusOutput() == StatusOutput.NEXT_DAY)
				continue;
			if (outputCheckProcessed.getStatusOutput() == StatusOutput.NEXT_EMPLOYEE) {
				return new OutputCreateDailyResult(ProcessState.SUCCESS, listErrorMessageInfo);
			}
//			String employmentCode = outputCheckProcessed.getEmploymentHistoryImported().get().getEmploymentCode();
			// 締めIDを取得する
//			Optional<ClosureEmployment> closureEmploymentOptional = this.closureEmploymentRepository
//					.findByEmploymentCD(companyId, employmentCode);

			//処理する日が締められているかチェックする
			if (!closureStatusManagement.isPresent() || 
					!closureStatusManagement.get().getPeriod().contains(day)) {
				
				LockStatus lockStatus = LockStatus.UNLOCK;
                //「ロック中の計算/集計する」の値をチェックする
                if(!checkLock.isPresent() || checkLock.get() == false) {
                    Closure closureData = ClosureService.getClosureDataByEmployee(
                    		requireService.createRequire(), new CacheCarrier(), employeeId, day);
                    //アルゴリズム「実績ロックされているか判定する」を実行する (Chạy xử lý)
                    lockStatus = lockStatusService.getDetermineActualLocked(companyId, 
                            day, closureData.getClosureId().value, PerformanceType.DAILY);
                }
                if(lockStatus == LockStatus.LOCK) {
                    continue;
                }
                EmbossingExecutionFlag flag = EmbossingExecutionFlag.NOT_REFECT_ONLY;
                //「実行タイプ」をチェックする
                //実行タイプが「実績を削除する」の場合
                if( executionType  == ExecutionTypeDaily.DELETE_ACHIEVEMENTS) {
                	//日別実績の前データを削除する
                	deleteWorkInfor.deleteWorkInfor(companyId, employeeId, day);
                	flag = EmbossingExecutionFlag.ALL;
                }
                //一日の日別実績の作成処理（New）
                OutputCreateDailyOneDay outputCreateDailyOneDay = createDailyOneDay.createDailyOneDay(companyId, employeeId, day,
						reCreateWorkType, reCreateWorkPlace, reCreateRestTime, executionType,
						flag, employeeGeneralInfoImport, periodInMasterList);
                if(!outputCreateDailyOneDay.getListErrorMessageInfo().isEmpty()) {
                	//エラー一覧にエラー入れる
    				listErrorMessageInfo.addAll(outputCreateDailyOneDay.getListErrorMessageInfo());
                }else {
                	if(outputCreateDailyOneDay.getIntegrationOfDaily() != null) {
                		//登録する (Đăng ký) 
                		registerDailyWork.register(outputCreateDailyOneDay.getIntegrationOfDaily(), outputCreateDailyOneDay.getListStamp());
                		
                	}
                	
                }
                if(empCalAndSumExeLog.isPresent()) {
	                //ドメインモデル「就業計算と集計実行ログ」を取得し、実行状況を確認する
					Optional<EmpCalAndSumExeLog> logOptional = this.empCalAndSumExeLogRepository
							.getByEmpCalAndSumExecLogID(empCalAndSumExeLog.get().getEmpCalAndSumExecLogID());
					// 実行状況.中断開始 の場合
					if (logOptional.isPresent() && logOptional.get().getExecutionStatus().isPresent()
							&& logOptional.get().getExecutionStatus().get() == ExeStateOfCalAndSum.START_INTERRUPTION) {
						asyncContext.finishedAsCancelled();
						return new OutputCreateDailyResult(ProcessState.INTERRUPTION, listErrorMessageInfo);
					}
                }

			}
		}
		return new OutputCreateDailyResult(ProcessState.SUCCESS, listErrorMessageInfo);
	}

}
