package nts.uk.ctx.at.record.dom.dailyprocess.calc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.persistence.OptimisticLockException;

import org.apache.commons.lang3.tuple.Pair;

import lombok.val;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.task.parallel.ManagedParallelWithContext;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.gul.error.ThrowableAnalyzer;
import nts.uk.ctx.at.record.dom.actualworkinghours.AttendanceTimeOfDailyPerformance;
import nts.uk.ctx.at.record.dom.approvalmanagement.ApprovalProcessingUseSetting;
import nts.uk.ctx.at.record.dom.approvalmanagement.repository.ApprovalProcessingUseSettingRepository;
import nts.uk.ctx.at.record.dom.daily.DailyRecordAdUpService;
import nts.uk.ctx.at.record.dom.daily.optionalitemtime.AnyItemValueOfDaily;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.CreateDailyResultDomainServiceImpl.ProcessState;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.checkprocessed.CheckProcessed;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.checkprocessed.OutputCheckProcessed;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.checkprocessed.StatusOutput;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.createdailyoneday.IntegrationOfDailyGetter;
import nts.uk.ctx.at.record.dom.organization.EmploymentHistoryImported;
import nts.uk.ctx.at.record.dom.organization.adapter.EmploymentAdapter;
import nts.uk.ctx.at.record.dom.require.RecordDomRequireService;
import nts.uk.ctx.at.record.dom.workinformation.WorkInfoOfDailyPerformance;
import nts.uk.ctx.at.record.dom.workrecord.actuallock.DetermineActualResultLock;
import nts.uk.ctx.at.record.dom.workrecord.actuallock.LockStatus;
import nts.uk.ctx.at.record.dom.workrecord.actuallock.PerformanceType;
import nts.uk.ctx.at.record.dom.workrecord.identificationstatus.IdentityProcessUseSet;
import nts.uk.ctx.at.record.dom.workrecord.identificationstatus.repository.IdentityProcessUseSetRepository;
import nts.uk.ctx.at.record.dom.workrecord.workperfor.dailymonthlyprocessing.EmpCalAndSumExeLog;
import nts.uk.ctx.at.record.dom.workrecord.workperfor.dailymonthlyprocessing.EmpCalAndSumExeLogRepository;
import nts.uk.ctx.at.record.dom.workrecord.workperfor.dailymonthlyprocessing.ErrMessageInfoRepository;
import nts.uk.ctx.at.record.dom.workrecord.workperfor.dailymonthlyprocessing.TargetPersonRepository;
import nts.uk.ctx.at.shared.dom.dailyperformanceprocessing.ErrMessageResource;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.InterimRemainDataMngRegisterDateChange;
import nts.uk.ctx.at.shared.dom.scherec.closurestatus.ClosureStatusManagement;
import nts.uk.ctx.at.shared.dom.scherec.closurestatus.ClosureStatusManagementRepository;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.CommonCompanySettingForCalc;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.CalculationState;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.ManagePerCompanySet;
import nts.uk.ctx.at.shared.dom.workrecord.workperfor.dailymonthlyprocessing.ErrMessageContent;
import nts.uk.ctx.at.shared.dom.workrecord.workperfor.dailymonthlyprocessing.ErrMessageInfo;
import nts.uk.ctx.at.shared.dom.workrecord.workperfor.dailymonthlyprocessing.enums.ExecutionContent;
import nts.uk.ctx.at.shared.dom.workrecord.workperfor.dailymonthlyprocessing.enums.ExecutionType;
import nts.uk.ctx.at.shared.dom.workrule.closure.Closure;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmployment;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmploymentRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.ClosureService;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.i18n.TextResource;

/**
 * ドメインサービス：日別計算　（社員の日別実績を計算）
 * @author keisuke_hoshina
 */
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
@Stateless
public class DailyCalculationEmployeeServiceImpl implements DailyCalculationEmployeeService {

	//*****（未）　以下、日別実績の勤怠情報など、日別計算のデータ更新に必要なリポジトリを列記。
	/** リポジトリ：日別実績の勤怠時間 */
	@Inject
	private IntegrationOfDailyGetter integrationGetter;
	
	//ドメインサービス：計算用ストアド実行用
	@Inject
	private AdTimeAndAnyItemAdUpService adTimeAndAnyItemAdUpService; 
	
	/*日別計算　マネージャークラス*/
	@Inject
	private CalculateDailyRecordServiceCenter calculateDailyRecordServiceCenter;
	
	/*〆状態*/
	@Inject
	private ClosureStatusManagementRepository closureStatusManagementRepository;
	
	@Inject
	private CommonCompanySettingForCalc commonCompanySettingForCalc;
	
	/** リポジトリ：対象者ログ */
	@Inject
	private TargetPersonRepository targetPersonRepository;
	
	/** リポジトリ：就業計算と集計実行ログ */
	@Inject
	private EmpCalAndSumExeLogRepository empCalAndSumExeLogRepository;
	
	@Inject
	/*並列処理*/
	private ManagedParallelWithContext parallel;
	
	@Inject
	/*暫定データ登録*/
	private InterimRemainDataMngRegisterDateChange interimData;
	
	@Inject
	/*エラメッセージ情報登録*/
	private ErrMessageInfoRepository errMessageInfoRepository;
	
	@Inject
	//日別実績(WORK)更新処理まとめ
	private DailyRecordAdUpService dailyRecordAdUpService;
	
	@Inject
	private ApprovalProcessingUseSettingRepository approvalSettingRepo;
	
	@Inject
	private IdentityProcessUseSetRepository identityProcessUseRepository;
	
	@Inject
	private ClosureEmploymentRepository closureEmploymentRepository;
	
	@Inject
	private DetermineActualResultLock lockStatusService;
	
//	@Inject
//	private ClosureService closureService;
	
	@Inject
	private EmploymentAdapter employmentAdapter;
	
	@Inject
	private CheckProcessed checkProcessed;
	
	@Inject
	private RecordDomRequireService requireService;
	/**
	 * 社員の日別実績を計算
	 * @param asyncContext 同期コマンドコンテキスト
	 * @param companyId 会社ID
	 * @param employeeId 社員ID
	 * @param datePeriod 期間
	 * @param empCalAndSumExecLogID 就業計算と集計実行ログID
	 * @param executionType 実行種別　（通常、再実行）
	 */
	@SuppressWarnings("rawtypes")
	@Override
	//@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<Boolean> calculate(List<String> employeeIds,DatePeriod datePeriod, Consumer<ProcessState> counter,ExecutionType reCalcAtr, String empCalAndSumExecLogID ,Boolean isCalWhenLock) {
		
		String cid = AppContexts.user().companyId();
		List<Boolean> isHappendOptimistLockError = new ArrayList<>();
		
		Optional<IdentityProcessUseSet> iPUSOptTemp = identityProcessUseRepository.findByKey(cid);
		Optional<ApprovalProcessingUseSetting> approvalSetTemp = approvalSettingRepo.findByCompanyId(cid);
		this.parallel.forEach(employeeIds, employeeId -> {
			
//			// 中断処理　（中断依頼が出されているかチェックする）
//			if (asyncContext.hasBeenRequestedToCancel()) {
//				counter.accept(ProcessState.INTERRUPTION);
//				return;
//			}
			Optional<EmpCalAndSumExeLog> log = empCalAndSumExeLogRepository.getByEmpCalAndSumExecLogID(empCalAndSumExecLogID);
			if(!log.isPresent()) {
				counter.accept(ProcessState.INTERRUPTION);
				return;
			}
			else {
				val executionStatus = log.get().getExecutionStatus();
				if(executionStatus.isPresent() && executionStatus.get().isStartInterruption()) {
					counter.accept(ProcessState.INTERRUPTION);
					return;
				}
			}
			//日別実績(WORK取得)
		//	List<IntegrationOfDaily> createList = createIntegrationOfDaily(employeeId,datePeriod);
			
			//締め一覧取得
		//	List<ClosureStatusManagement> closureList = getClosureList(Arrays.asList(employeeId), datePeriod);
			
			ManageProcessAndCalcStateResult afterCalcRecord =null;
			Pair<Integer, ManageProcessAndCalcStateResult> result = null;
			result = runWhenOptimistLockError(cid, employeeId, datePeriod, reCalcAtr, empCalAndSumExecLogID, afterCalcRecord, iPUSOptTemp, approvalSetTemp, false,isCalWhenLock);
			if(result.getLeft() == 0) { 
				counter.accept(ProcessState.SUCCESS);
				targetPersonRepository.updateWithContent(employeeId, empCalAndSumExecLogID, 1, 0);
				return;
			}
			
			if(result.getLeft() == 1) {  //co loi haita
				result = runWhenOptimistLockError(cid, employeeId, datePeriod, reCalcAtr, empCalAndSumExecLogID, afterCalcRecord, iPUSOptTemp, approvalSetTemp, true,isCalWhenLock);
				if(result.getLeft() == 1) { 
					isHappendOptimistLockError.add(true);
				}
			}
/*			if (createList.isEmpty()) {
				counter.accept(ProcessState.SUCCESS);
				
				//１：日別計算(ENUM)
				//0:計算完了
				targetPersonRepository.updateWithContent(employeeId, empCalAndSumExecLogID, 1, 0);
			} else {
				//計算処理を呼ぶ
				afterCalcRecord = calculateDailyRecordServiceCenter.calculateForManageState(createList,closureList,reCalcAtr,empCalAndSumExecLogID);
				//１：日別計算(ENUM)
				//0:計算完了
				targetPersonRepository.updateWithContent(employeeId, empCalAndSumExecLogID, 1, 0);

				//データ更新
				for(ManageCalcStateAndResult stateInfo : afterCalcRecord.getLst()) {
					
					try {
						//update record
						updateRecord(stateInfo.integrationOfDaily);
						clearConfirmApproval(stateInfo.integrationOfDaily,iPUSOptTemp,approvalSetTemp);
						upDateCalcState(stateInfo);
					} catch (Exception ex) {
						boolean isOptimisticLock = new ThrowableAnalyzer(ex).findByClass(OptimisticLockException.class).isPresent();
						if (!isOptimisticLock) {
							throw ex;
						}
						//create error message
						ErrMessageInfo employmentErrMes = new ErrMessageInfo(employeeId, empCalAndSumExecLogID,
								new ErrMessageResource("024"), EnumAdaptor.valueOf(1, ExecutionContent.class), 
								stateInfo.getIntegrationOfDaily().getYmd(),
								new ErrMessageContent(TextResource.localize("Msg_1541")));
						//regist error message 
						this.errMessageInfoRepository.add(employmentErrMes);
						
						
						isHappendOptimistLockError.add(true);
					}
				}
				
				//暫定データ
				interimData.registerDateChange(cid , employeeId, datePeriod.datesBetween());
				//
				counter.accept(afterCalcRecord.getPs() == ProcessState.SUCCESS?ProcessState.SUCCESS:ProcessState.INTERRUPTION);
			}*/
		});
		return isHappendOptimistLockError;
	}
	private Pair<Integer, ManageProcessAndCalcStateResult> runWhenOptimistLockError(String cid, String employeeId,
			DatePeriod datePeriod, ExecutionType reCalcAtr, String empCalAndSumExecLogID,
			ManageProcessAndCalcStateResult afterCalcRecord, Optional<IdentityProcessUseSet> iPUSOptTemp,
			Optional<ApprovalProcessingUseSetting> approvalSetTemp,boolean runOptimistLock,Boolean IsCalWhenLock) {
		//if check = 0 : createListNew : null
		//if check = 1 : has error optimistic lock (lan 1)
		//if check = 2 : done
		Integer check =2;
		
//		List<Boolean> isHappendOptimistLockError = new ArrayList<>(); 

		List<IntegrationOfDaily> createListNew = integrationGetter.getIntegrationOfDaily(employeeId, datePeriod);
		if (createListNew.isEmpty()) {
			check = 0;
			return Pair.of(check, afterCalcRecord);
		}

		// 締め一覧取得
		List<ClosureStatusManagement> closureListNew = getClosureList(Arrays.asList(employeeId), datePeriod);

		afterCalcRecord = calculateDailyRecordServiceCenter.calculateForManageState(createListNew, closureListNew,
				reCalcAtr, empCalAndSumExecLogID);
		
		List<EmploymentHistoryImported> listEmploymentHis = this.employmentAdapter.getEmpHistBySid(cid, employeeId);
		boolean checkNextEmp =false;
		// データ更新
		for (ManageCalcStateAndResult stateInfo : afterCalcRecord.getLst()) {
			if(checkNextEmp) {
				continue;
			}
			OutputCheckProcessed outputCheckProcessed = checkProcessed.getCheckProcessed(stateInfo.getIntegrationOfDaily().getYmd(), listEmploymentHis);
			if(outputCheckProcessed.getStatusOutput() == StatusOutput.NEXT_DAY) continue;
			if(outputCheckProcessed.getStatusOutput() == StatusOutput.NEXT_EMPLOYEE) {
				checkNextEmp = true;
				continue;
			}

			// 締めIDを取得する
			Optional<ClosureEmployment> closureEmploymentOptional = this.closureEmploymentRepository
					.findByEmploymentCD(cid, stateInfo.getIntegrationOfDaily().getAffiliationInfor().getEmploymentCode().v());
			
			LockStatus lockStatus = LockStatus.UNLOCK;
			if(IsCalWhenLock ==null || IsCalWhenLock == false) {
				//アルゴリズム「実績ロックされているか判定する」を実行する (Chạy xử lý)
				//実績ロックされているか判定する
				lockStatus = lockStatusService.getDetermineActualLocked(cid, 
						stateInfo.getIntegrationOfDaily().getYmd(), closureEmploymentOptional.get().getClosureId(), PerformanceType.DAILY);
			}

			if(lockStatus == LockStatus.LOCK) {
				continue;
			}
			try {
				// updater record
				updateRecord(stateInfo.integrationOfDaily);
				clearConfirmApproval(stateInfo.integrationOfDaily, iPUSOptTemp, approvalSetTemp);
				upDateCalcState(stateInfo);
			} catch (Exception ex) {
				boolean isOptimisticLock = new ThrowableAnalyzer(ex).findByClass(OptimisticLockException.class)
						.isPresent();
				if (!isOptimisticLock) {
					throw ex;
				}
				check = 1;
				if(runOptimistLock) {
					// create error message
					ErrMessageInfo employmentErrMes = new ErrMessageInfo(employeeId, empCalAndSumExecLogID,
							new ErrMessageResource("024"), EnumAdaptor.valueOf(1, ExecutionContent.class),
							stateInfo.getIntegrationOfDaily().getYmd(),
							new ErrMessageContent(TextResource.localize("Msg_1541")));
				}
			}
		}
		return Pair.of(check, afterCalcRecord);
	}
	
	/**
	 * #108941 
	 * 特定のエラーが発生している社員の確認、承認をクリアする
	 * @param integrationOfDaily
	 * @param iPUSOptTemp
	 * @param approvalSetTemp
	 */
	private void clearConfirmApproval(IntegrationOfDaily integrationOfDaily,
			Optional<IdentityProcessUseSet> iPUSOptTemp, Optional<ApprovalProcessingUseSetting> approvalSetTemp) {
		dailyRecordAdUpService.removeConfirmApproval(Arrays.asList(integrationOfDaily), iPUSOptTemp, approvalSetTemp);
	}


	private void updateRecord(IntegrationOfDaily value) {
 		// データ更新
		if(value.getAttendanceTimeOfDailyPerformance().isPresent()) {
//			employeeDailyPerErrorRepository.removeParam(value.getAttendanceTimeOfDailyPerformance().get().getEmployeeId(), 
//					value.getAttendanceTimeOfDailyPerformance().get().getYmd());
//			determineErrorAlarmWorkRecordService.createEmployeeDailyPerError(value.getEmployeeError());
			AttendanceTimeOfDailyPerformance attdTimeOfDailyPer = new AttendanceTimeOfDailyPerformance(value.getEmployeeId(),value.getYmd(),
									  value.getAttendanceTimeOfDailyPerformance().get());
			Optional<AnyItemValueOfDaily> anyItem = value.getAnyItemValue().isPresent()
					? Optional.of(new AnyItemValueOfDaily(value.getEmployeeId(), value.getYmd(),
							value.getAnyItemValue().get()))
					: Optional.empty();
			this.registAttendanceTime(value.getEmployeeId(),value.getYmd(),
					attdTimeOfDailyPer,anyItem);
		}
		
		if(value.getAffiliationInfor() != null) {
			Pair<String,GeneralDate> pair = Pair.of(value.getEmployeeId(),
					value.getYmd());
			//計算から呼ぶ場合はtrueでいいらしい。保科⇒thanh
			this.dailyRecordAdUpService.adUpEmpError(value.getEmployeeError(), Arrays.asList(pair), true);			
		}
	}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void upDateCalcState(ManageCalcStateAndResult stateInfo) {
		stateInfo.getIntegrationOfDaily().getWorkInformation().changeCalcState(stateInfo.isCalc?CalculationState.Calculated:CalculationState.No_Calculated);
//		workInformationRepository.updateByKeyFlush(stateInfo.getIntegrationOfDaily().getWorkInformation());
		WorkInfoOfDailyPerformance data = new WorkInfoOfDailyPerformance(
				stateInfo.getIntegrationOfDaily().getEmployeeId(), stateInfo.getIntegrationOfDaily().getYmd(),
				stateInfo.getIntegrationOfDaily().getWorkInformation());
		data.setVersion(stateInfo.getIntegrationOfDaily().getWorkInformation().getVer());
		dailyRecordAdUpService.adUpWorkInfo(data);
		
	}
	
	/**
	 * 全社員の〆状態リストの取得
	 * @param employeeId 社員ID一覧
	 * @param datePeriod　処理対象期間
	 * @return　〆状態リスト
	 */
	private List<ClosureStatusManagement> getClosureList(List<String> employeeId, DatePeriod datePeriod) {
		return closureStatusManagementRepository.getByIdListAndDatePeriod(employeeId, datePeriod);
	}

	@SuppressWarnings("rawtypes")
	public ProcessState calculateForOnePerson(String employeeId,DatePeriod datePeriod,Optional<Consumer<ProcessState>> counter,String executeLogId,Boolean isCalWhenLock ) {
		//実績取得
		List<IntegrationOfDaily> createList = createIntegrationList(Arrays.asList(employeeId),datePeriod);
		//実績が無かった時用のカウントアップ
		if(createList.isEmpty()) 
			return ProcessState.SUCCESS; 
		String cid = AppContexts.user().companyId();
		Optional<IdentityProcessUseSet> iPUSOptTemp = identityProcessUseRepository.findByKey(cid);
		Optional<ApprovalProcessingUseSetting> approvalSetTemp = approvalSettingRepo.findByCompanyId(cid);
		
		//締め一覧取得
		List<ClosureStatusManagement> closureList = getClosureList(Arrays.asList(employeeId),datePeriod);
		
		ManagePerCompanySet companySet =  commonCompanySettingForCalc.getCompanySetting(); 
		//計算処理
		val afterCalcRecord = calculateDailyRecordServiceCenter.calculateForclosure(createList,companySet ,closureList,executeLogId);
		List<EmploymentHistoryImported> listEmploymentHis = this.employmentAdapter.getEmpHistBySid(cid, employeeId);
		boolean checkNextEmp =false;
		val cacheCarrier = new CacheCarrier();
		//データ更新
		for(ManageCalcStateAndResult stateInfo : afterCalcRecord.getLst()) {
			// 締めIDを取得する
			if(checkNextEmp) {
				continue;
			}
			OutputCheckProcessed outputCheckProcessed = checkProcessed.getCheckProcessed(stateInfo.getIntegrationOfDaily().getYmd(), listEmploymentHis);
			if(outputCheckProcessed.getStatusOutput() == StatusOutput.NEXT_DAY) continue;
			if(outputCheckProcessed.getStatusOutput() == StatusOutput.NEXT_EMPLOYEE) {
				checkNextEmp = true;
				continue;
			}
						LockStatus lockStatus = LockStatus.UNLOCK;
						if(isCalWhenLock ==null || isCalWhenLock == false) {
							Closure closureData = ClosureService.getClosureDataByEmployee(
									requireService.createRequire(), new CacheCarrier(),
									employeeId, stateInfo.getIntegrationOfDaily().getYmd());
							//アルゴリズム「実績ロックされているか判定する」を実行する (Chạy xử lý)
							//実績ロックされているか判定する
							lockStatus = lockStatusService.getDetermineActualLocked(cid, 
									stateInfo.getIntegrationOfDaily().getYmd(),  closureData.getClosureId().value, PerformanceType.DAILY);
						}
						if(lockStatus == LockStatus.LOCK) {
							continue;
						}
			try {
				//実績登録
				updateRecord(stateInfo.integrationOfDaily); 
				clearConfirmApproval(stateInfo.getIntegrationOfDaily(),iPUSOptTemp,approvalSetTemp);
				upDateCalcState(stateInfo);
				
			} catch (Exception ex) {
				boolean isOptimisticLock = new ThrowableAnalyzer(ex).findByClass(OptimisticLockException.class).isPresent();
				if (!isOptimisticLock) {
					throw ex;
				}
				ErrMessageInfo employmentErrMes = new ErrMessageInfo(employeeId, executeLogId,
						new ErrMessageResource("024"), EnumAdaptor.valueOf(1, ExecutionContent.class), 
						stateInfo.getIntegrationOfDaily().getYmd(),
						new ErrMessageContent(TextResource.localize("Msg_1541")));
				this.errMessageInfoRepository.add(employmentErrMes);
				
			}
		}
//		//計算状態更新
//		for(ManageCalcStateAndResult stateInfo : afterCalcRecord.getLst()) {
//			upDateCalcState(stateInfo);
//		}
		return afterCalcRecord.getPs();
	}

	/**
	 * 実績データ取得
	 * @param employeeId
	 * @param datePeriod
	 * @return
	 */
	private List<IntegrationOfDaily> createIntegrationList(List<String> employeeId, DatePeriod datePeriod) {
		List<IntegrationOfDaily> returnList = new ArrayList<>();
		for(String empId:employeeId) {
			returnList.addAll(integrationGetter.getIntegrationOfDaily(empId, datePeriod));
		}
		return returnList;
	}

	/**
	 * データ更新
	 * @param attendanceTime 日別実績の勤怠時間
	 */
	private void registAttendanceTime(String empId,GeneralDate ymd,AttendanceTimeOfDailyPerformance attendanceTime, Optional<AnyItemValueOfDaily> anyItem){
		adTimeAndAnyItemAdUpService.addAndUpdate(empId,ymd,Optional.of(attendanceTime), anyItem);	
	}
	
	
}
