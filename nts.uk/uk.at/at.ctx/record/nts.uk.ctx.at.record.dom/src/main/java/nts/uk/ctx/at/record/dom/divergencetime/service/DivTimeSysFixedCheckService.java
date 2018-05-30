package nts.uk.ctx.at.record.dom.divergencetime.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import lombok.val;
import nts.arc.i18n.I18NResources;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.record.dom.actualworkinghours.repository.AttendanceTimeRepository;
import nts.uk.ctx.at.record.dom.adapter.approvalrootstate.AppRootStateConfirmAdapter;
import nts.uk.ctx.at.record.dom.approvalmanagement.enums.ConfirmationOfManagerOrYouself;
import nts.uk.ctx.at.record.dom.approvalmanagement.repository.ApprovalProcessingUseSettingRepository;
import nts.uk.ctx.at.record.dom.approvalmanagement.repository.ApprovalStatusOfDailyPerforRepository;
import nts.uk.ctx.at.record.dom.dailyperformanceformat.businesstype.BusinessTypeOfEmployee;
import nts.uk.ctx.at.record.dom.dailyperformanceformat.businesstype.BusinessTypeOfEmployeeHistory;
import nts.uk.ctx.at.record.dom.dailyperformanceformat.businesstype.repository.BusinessTypeEmpOfHistoryRepository;
import nts.uk.ctx.at.record.dom.dailyperformanceformat.businesstype.repository.BusinessTypeOfEmployeeRepository;
import nts.uk.ctx.at.record.dom.dailyperformanceformat.primitivevalue.BusinessTypeCode;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.converter.DailyRecordToAttendanceItemConverter;
import nts.uk.ctx.at.record.dom.divergence.time.DivergenceTime;
import nts.uk.ctx.at.record.dom.divergence.time.DivergenceTimeRepository;
import nts.uk.ctx.at.record.dom.divergence.time.history.CompanyDivergenceReferenceTime;
import nts.uk.ctx.at.record.dom.divergence.time.history.CompanyDivergenceReferenceTimeHistory;
import nts.uk.ctx.at.record.dom.divergence.time.history.CompanyDivergenceReferenceTimeHistoryRepository;
import nts.uk.ctx.at.record.dom.divergence.time.history.CompanyDivergenceReferenceTimeRepository;
import nts.uk.ctx.at.record.dom.divergence.time.history.DivergenceReferenceTime;
import nts.uk.ctx.at.record.dom.divergence.time.history.DivergenceReferenceTimeUsageUnitRepository;
import nts.uk.ctx.at.record.dom.divergence.time.history.DivergenceReferenceTimeValue;
import nts.uk.ctx.at.record.dom.divergence.time.history.WorkTypeDivergenceReferenceTime;
import nts.uk.ctx.at.record.dom.divergence.time.history.WorkTypeDivergenceReferenceTimeHistoryRepository;
import nts.uk.ctx.at.record.dom.divergence.time.history.WorkTypeDivergenceReferenceTimeRepository;
import nts.uk.ctx.at.record.dom.divergence.time.message.DivergenceTimeErrorAlarmMessage;
import nts.uk.ctx.at.record.dom.divergence.time.message.DivergenceTimeErrorAlarmMessageRepository;
import nts.uk.ctx.at.record.dom.divergence.time.message.ErrorAlarmMessage;
import nts.uk.ctx.at.record.dom.divergence.time.message.WorkTypeDivergenceTimeErrorAlarmMessage;
import nts.uk.ctx.at.record.dom.divergence.time.message.WorkTypeDivergenceTimeErrorAlarmMessageRepository;
import nts.uk.ctx.at.record.dom.divergencetime.DiverdenceReasonCode;
import nts.uk.ctx.at.record.dom.divergencetime.DivergenceReasonContent;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.EmployeeDailyPerError;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.EmployeeDailyPerErrorRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.ErrorAlarmWorkRecord;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.ErrorAlarmWorkRecordRepository;
import nts.uk.ctx.at.record.dom.workrecord.errorsetting.SystemFixedErrorAlarm;
import nts.uk.ctx.at.record.dom.workrecord.identificationstatus.Identification;
import nts.uk.ctx.at.record.dom.workrecord.identificationstatus.IdentityProcessUseSet;
import nts.uk.ctx.at.record.dom.workrecord.identificationstatus.enums.SelfConfirmError;
import nts.uk.ctx.at.record.dom.workrecord.identificationstatus.repository.IdentificationRepository;
import nts.uk.ctx.at.record.dom.workrecord.identificationstatus.repository.IdentityProcessUseSetRepository;
import nts.uk.ctx.at.record.dom.worktime.TimeLeavingOfDailyPerformance;
import nts.uk.ctx.at.record.dom.worktime.repository.TimeLeavingOfDailyPerformanceRepository;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.shr.com.enumcommon.NotUseAtr;
import nts.uk.shr.com.history.DateHistoryItem;

@Stateless
public class DivTimeSysFixedCheckService {

	@Inject
	private CompanyDivergenceReferenceTimeRepository comDivRefTime;
	
	@Inject
	private AttendanceTimeRepository attendanceTimeRepo;
	
	@Inject
	private DivergenceTimeRepository diverTimeRepo;
	
	@Inject
	private ErrorAlarmWorkRecordRepository erAlConditionRepo;
	
	@Inject
	private DivergenceReferenceTimeUsageUnitRepository divRefUnitRepo;
	
	@Inject
	private WorkTypeDivergenceReferenceTimeHistoryRepository wtDivHisRepo;
	
	@Inject
	private WorkTypeDivergenceReferenceTimeRepository wtDivRefTime;
	
	@Inject
	private CompanyDivergenceReferenceTimeHistoryRepository comDivHisRepo;
	
	@Inject
	private TimeLeavingOfDailyPerformanceRepository timeLeaveRepo;
	
	@Inject
	private DailyRecordToAttendanceItemConverter convertHelper;
	
	@Inject
	private DivergenceTimeErrorAlarmMessageRepository divMesRepo;

	@Inject
	private WorkTypeDivergenceTimeErrorAlarmMessageRepository wtDivMesRepo;
	
	@Inject
	private IdentityProcessUseSetRepository identityPSURepo;
	
	@Inject
	private IdentificationRepository identityRepo;
	
	@Inject
	private EmployeeDailyPerErrorRepository errorRepo;
	
	@Inject 
	private ErrorAlarmWorkRecordRepository eaRecordRepo;
	
	@Inject
	private ApprovalProcessingUseSettingRepository approvalSettingRepo;
	
	@Inject
	private ApprovalStatusOfDailyPerforRepository approvalStatRepo;
	
	@Inject
	private AppRootStateConfirmAdapter appRootStateAdapter;
	
	@Inject
	private I18NResources resources;
	
	@Inject
	private BusinessTypeEmpOfHistoryRepository bteHisRepo;
	
	@Inject
	private BusinessTypeOfEmployeeRepository bteRepo;
	
	public static List<String> SYSTEM_FIXED_CHECK_CODE = Arrays.asList("D001", "D002", "D003", "D004", "D005", 
			"D006", "D007", "D008", "D009", "D010", "D011", "D012", "D013", "D014", "D015", "D016", "D017", "D018", "D019", "D020");
	private final String WORKTYPE_HISTORY_ITEM = "W_HIS";
	private final String COMPANY_HISTORY_ITEM = "C_HIS";
	private final String WORKTYPE_CODE = "WTC";
	/** ログオフ時刻をシステム時刻として計算するかチェックする */
	private final int LOGOFF_DIV_NO = 7;
	/** ・勤怠項目ID　34（退勤時刻1） */
	private final int TIME_LEAVE_ITEM = 34;
	
	private final String EMPTY_STRING = "";

	private final String PATTERN_1 = "[0-9]+$";

	private final String RUNTIME_ERROR_1 = "勤務実績のエラーアラームのコードのフォーマットが正しくない：　";

	private final String DIVERGENCE_MESSAGE_KEY = "DivergenceMessage";

	private final String KDW003_108_KEY = "KDW003_108";

	private final String COM_DIV_REF_TIME_KEY = "ComDivRefTime";

	private final String WT_DIV_REF_TIME_KEY = "WTDivRefTime";

	private final String BUSINESS_TYPE_HISTORY_KEY = "BusinessTypeOfEmployeeHistory";

	private final String COM_DIV_REF_TIME_HISTORY_KEY = "CompanyDivergenceReferenceTimeHistory";

	private final String TIME_NOW_KEY = "TimeNow";

	private final String TODAY_KEY = "Today";

	private final String APPROVAL_SETTING_KEY = "ApprovalSetting";

	private final String IDENTITY_PUS_KEY = "IdentityPUS";

	private final String DIVERGENCE_TIME_KEY = "DivergenceTime";

	
	
	/** 乖離時間（確認解除） */
	public List<EmployeeDailyPerError> divergenceTimeCheckBySystemFixed(String companyId, String employeeId, GeneralDate workingDate){
		List<EmployeeDailyPerError> checkR = new ArrayList<>(); 
		attendanceTimeRepo.find(employeeId, workingDate).ifPresent(at -> {
			checkR.addAll(divergenceTimeCheckBySystemFixed(companyId, employeeId, workingDate, 
					at.getActualWorkingTimeOfDaily().getDivTime().getDivergenceTime()));
		});
		return checkR;
	}
	
	/** 乖離時間（確認解除） */
	public List<EmployeeDailyPerError> divergenceTimeCheckBySystemFixed(String companyId, String employeeId, GeneralDate workingDate,
			List<nts.uk.ctx.at.record.dom.divergencetimeofdaily.DivergenceTime> divergenTime){
		return divergenceTimeCheckBySystemFixed(companyId, employeeId, workingDate, divergenTime, null, Optional.empty());
	}
	
	/** 乖離時間（確認解除） */
	public List<EmployeeDailyPerError> divergenceTimeCheckBySystemFixed(String companyId, String employeeId, GeneralDate workingDate,
			List<nts.uk.ctx.at.record.dom.divergencetimeofdaily.DivergenceTime> divergenTime, Optional<TimeLeavingOfDailyPerformance> timeLeave){
		return divergenceTimeCheckBySystemFixed(companyId, employeeId, workingDate, divergenTime, null, timeLeave);
	}
	
	/** 乖離時間（確認解除） */
	public List<EmployeeDailyPerError> divergenceTimeCheckBySystemFixed(String companyId, String employeeId, GeneralDate workingDate,
			List<nts.uk.ctx.at.record.dom.divergencetimeofdaily.DivergenceTime> divergenTime, 
			Optional<TimeLeavingOfDailyPerformance> timeLeave, List<ErrorAlarmWorkRecord> erAlConditions){
		return divergenceTimeCheckBySystemFixed(companyId, employeeId, workingDate, divergenTime, null, timeLeave, erAlConditions);
	}
	
	/** 乖離時間（確認解除） */
	public List<EmployeeDailyPerError> divergenceTimeCheckBySystemFixed(String companyId, String employeeId, GeneralDate workingDate,
			List<nts.uk.ctx.at.record.dom.divergencetimeofdaily.DivergenceTime> divergenTime, IdentityProcessUseSet identityPUS, 
			Optional<TimeLeavingOfDailyPerformance> timeLeave){
		return divergenceTimeCheckBySystemFixed(companyId, employeeId, workingDate, divergenTime, identityPUS, timeLeave, null);
	}
	
	/** 乖離時間（確認解除） */
	public List<EmployeeDailyPerError> divergenceTimeCheckBySystemFixed(String companyId, String employeeId, GeneralDate workingDate,
			List<nts.uk.ctx.at.record.dom.divergencetimeofdaily.DivergenceTime> divergenTime, IdentityProcessUseSet identityPUS, 
			Optional<TimeLeavingOfDailyPerformance> timeLeave, List<ErrorAlarmWorkRecord> erAlConditions){
		List<EmployeeDailyPerError> errors = check(companyId, employeeId, workingDate, divergenTime, timeLeave, erAlConditions);
		if(errors.isEmpty()) {
			return errors;
		}
		if(identityPUS == null) {
			identityPUS = getShared(IDENTITY_PUS_KEY, () -> identityPSURepo.findByKey(companyId).orElseThrow(() 
															-> new RuntimeException("本人確認処理の利用設定 not found!!!")));
		}
		return removeconfirm(companyId, employeeId, workingDate, errors, identityPUS);
	}
	
	/** 確認解除 */
	private List<EmployeeDailyPerError> removeconfirm(String companyId, String employeeId, GeneralDate workingDate, 
			List<EmployeeDailyPerError> errors, IdentityProcessUseSet identityPUS) {
		List<EmployeeDailyPerError> errorDivergence = errors.stream().filter(c -> c.getErrorAlarmWorkRecordCode() != null
				&& (c.getErrorAlarmWorkRecordCode().v().equals(SystemFixedErrorAlarm.DIVERGENCE_ERROR_6.value)
				|| c.getErrorAlarmWorkRecordCode().v().equals(SystemFixedErrorAlarm.DIVERGENCE_ERROR_7.value))
				&& c.getDate().equals(workingDate) && c.getEmployeeID().equals(employeeId)).collect(Collectors.toList());
		
		if (identityPUS.isUseConfirmByYourself()) {
			val identity = identityRepo.findByCode(employeeId, workingDate);
			if (identity.isPresent()) {
				List<SelfConfirmContent> content = errorDivergence.stream().map(c -> new SelfConfirmContent(c.getDate(), false))
						.collect(Collectors.toList());
				removeSelfIdentity(companyId, identityPUS, new SelfConfirmContentRegistry(content, employeeId));
			}
		}
		
		if (!errorDivergence.isEmpty()) {
			getShared(APPROVAL_SETTING_KEY, () -> approvalSettingRepo.findByCompanyId(companyId)).ifPresent(as -> {
				if (as.getUseDayApproverConfirm() != null && as.getUseDayApproverConfirm()
						&& as.getSupervisorConfirmErrorAtr() != null
						&& !as.getSupervisorConfirmErrorAtr().equals(ConfirmationOfManagerOrYouself.CAN_CHECK)) {
					approvalStatRepo.find(employeeId, workingDate).ifPresent(asd -> {
						/** 承認状態をすべてクリアする */
						appRootStateAdapter.clearAppRootstate(asd.getRootInstanceID());
					});
				}
			});
		}
		return errors;
	}
		
	/** 日の本人確認を登録する */
	private void registryIdentity(String companyId, IdentityProcessUseSet identityPUS, SelfConfirmContentRegistry selfConfirm) {
		if(!identityPUS.getYourSelfConfirmError().isPresent()) {
			return;
		}
		if(identityPUS.getYourSelfConfirmError().get() != SelfConfirmError.CAN_CONFIRM_WHEN_ERROR) {
			List<EmployeeDailyPerError> errors = new ArrayList<>();
			errors.addAll(selfConfirm.content.stream().map(c -> errorRepo.find(selfConfirm.empId, c.ymd))
					.flatMap(List::stream).collect(Collectors.toList()));
			if(!errors.isEmpty()) {
				List<ErrorAlarmWorkRecord> eaRecords = eaRecordRepo.getListErAlByListCodeError(companyId, 
						errors.stream().map(c -> c.getErrorAlarmWorkRecordCode().v()).collect(Collectors.toList()));
				if(!eaRecords.isEmpty()) {
					// fix remove ドメインモデル「日の本人確認」を削除する Thanh
					selfConfirm.content.stream().filter(c -> !c.confirmStatus).forEach(c -> {
						identityRepo.remove(companyId, selfConfirm.empId, c.ymd);
					});
					GeneralDate today = GeneralDate.today();
					selfConfirm.content.stream().filter(c -> c.confirmStatus).forEach(c -> {
						identityRepo.insert(new Identification(companyId, selfConfirm.empId, c.ymd, today));
					});
					return;
				}
			}
		}
	}
	
	/** 日の本人確認を解除する */
	private void removeSelfIdentity(String companyId, IdentityProcessUseSet identityPUS, SelfConfirmContentRegistry selfConfirm) {
		if(!identityPUS.getYourSelfConfirmError().isPresent()) {
			return;
		}
		if (identityPUS.getYourSelfConfirmError().get() != SelfConfirmError.CAN_CONFIRM_WHEN_ERROR) {
			// fix remove ドメインモデル「日の本人確認」を削除する Thanh
			selfConfirm.content.stream().filter(c -> !c.confirmStatus).forEach(c -> {
				identityRepo.remove(companyId, selfConfirm.empId, c.ymd);
			});
			return;
		}
	}
	
	/** システム固定エラー：　乖離時間をチェックする */
	private List<EmployeeDailyPerError> check(String companyId, String employeeId, GeneralDate workingDate,
			List<nts.uk.ctx.at.record.dom.divergencetimeofdaily.DivergenceTime> divergenTime, 
			Optional<TimeLeavingOfDailyPerformance> timeLeave, List<ErrorAlarmWorkRecord> erAlConditions) {
		List<EmployeeDailyPerError> checkR = new ArrayList<>(); 
		boolean isCheckByWorkType = isCheckWithWorkType(companyId);
		boolean isToday = getShared(TODAY_KEY, () -> GeneralDate.today()).equals(workingDate);
		
		if(erAlConditions == null || erAlConditions.isEmpty()){
			erAlConditions = getErrorAlarmCheck(companyId);
		}
		if(erAlConditions.isEmpty()) {
			return checkR;
		}
		List<Integer> divergenceCheckNos = erAlConditions.stream()
				.map(c -> getNo(getNumberFromString(c.getCode().v()))).distinct().collect(Collectors.toList());

		Map<Integer, DivergenceTime> divergenceTimeErAls = getDivergenceTimeErAl(companyId, divergenceCheckNos);
		if(divergenceTimeErAls.isEmpty()) {
			return checkR;
		}
		val historyR = getHistory(isCheckByWorkType, employeeId, workingDate, companyId); 
		boolean isWHis = historyR.get(WORKTYPE_HISTORY_ITEM) != null;
		val historyItem = (DateHistoryItem) (isWHis ? historyR.get(WORKTYPE_HISTORY_ITEM) : historyR.get(COMPANY_HISTORY_ITEM));
		val bsCode = isWHis ? (BusinessTypeCode) historyR.get(WORKTYPE_CODE) : null; 
		if(historyItem == null){
			return checkR;
		}
		shareDivRefTime(isCheckByWorkType, historyItem.identifier(), divergenceCheckNos, bsCode);
		erAlConditions.stream().forEach(erAl -> {
			int numberIn = getNumberFromString(erAl.getCode().v());
			boolean isAlarm = numberIn % 2 == 0;
			val divergenceTimeErAl = divergenceTimeErAls.get(getNo(numberIn));
			if(divergenceTimeErAl != null && divergenceTimeErAl.isDivergenceTimeUse()){
				divergenTime.stream().filter(dt -> dt.getDivTimeId() == divergenceTimeErAl.getDivergenceTimeNo())
										.findFirst().ifPresent(dt -> {
					int divergenceTime = 0;
					boolean isPcDivergence = dt.getDivTimeId() == LOGOFF_DIV_NO && isToday;
					if(isPcDivergence) {
						divergenceTime = calcCurrentDivergenceTime(employeeId, workingDate, timeLeave);
					} else if (dt.getDivTimeAfterDeduction() != null) {
						divergenceTime = dt.getDivTimeAfterDeduction().valueAsMinutes();
					}
					// add DivResonCode to check Thanh
					boolean valid = evaluateDivergenceTime(getNo(numberIn), divergenceTime, 
							isAlarm, isWHis, historyItem.identifier(), bsCode,
							divergenceTimeErAl.getErrorCancelMedthod().isReasonInputed(), 
							divergenceTimeErAl.getErrorCancelMedthod().isReasonSelected(),
							dt.getDivReason(), dt.getDivResonCode());
					if(!valid){
						checkR.add(new EmployeeDailyPerError(
											companyId, employeeId, workingDate, 
											erAl.getCode(), Arrays.asList(erAl.getErrorDisplayItem()), 
											erAl.getCancelableAtr() ? 1 : 0, 
											getMessage(isWHis, isPcDivergence, companyId, getNo(numberIn), isAlarm, bsCode)));
					}
				});
			}
		});
		return checkR;
	}

	private int getNo(int numberIn) {
		return (numberIn + 1) / 2;
	}

	/** 上記の計算で求めた時間を発生した乖離時間として処理を進める */
	private int calcCurrentDivergenceTime(String employeeId, GeneralDate workingDate, 
			Optional<TimeLeavingOfDailyPerformance> timeLeave) {
		if(!timeLeave.isPresent()){
			timeLeave = timeLeaveRepo.findByKey(employeeId, workingDate);
		}
		/** 勤怠項目ID　34（退勤時刻1） */
		if(timeLeave.isPresent()) {
			val valued = convertHelper.withTimeLeaving(timeLeave.get()).convert(TIME_LEAVE_ITEM);
			if(valued.isPresent() && valued.get().value() != null) {
				GeneralDateTime now = getShared(TIME_NOW_KEY, () -> GeneralDateTime.now());
				int currentTime = now.hours() * 60 + now.minutes();
				int divergenceTime = currentTime - (int) valued.get().value();
				return divergenceTime;
			}
		}
		return 0;
	}
	
	/** 履歴項目を取得する */
	private Map<String, Object> getHistory(boolean isCheckByWorkType, String employeeId, GeneralDate workingDate, String companyId){
		if (!isCheckByWorkType) {
			return getComHistory(workingDate, companyId);
		}
		
		val workTypeCode = getWorkInfo(companyId, isCheckByWorkType, employeeId, workingDate);
		if(workTypeCode == null){
			return getComHistory(workingDate, companyId);
		}
		DateHistoryItem history = null;	
		history = getShared(companyId + workTypeCode, () -> wtDivHisRepo.findAll(companyId, workTypeCode))
						.items().stream().filter(c -> c.contains(workingDate)).findFirst().orElse(null);
		if(history == null){
			return getComHistory(workingDate, companyId);
		}
		Map<String, Object> res = new HashMap<>();
		res.put(WORKTYPE_HISTORY_ITEM, history);
		res.put(WORKTYPE_CODE, workTypeCode);
		return res;
	}

	@SuppressWarnings("unchecked")
	private <T> T getShared(String key, Supplier<T> getData) {
		T history;
		if(DivCheckSharedData.isShared(key)){
			history = (T) DivCheckSharedData.getShared(key);
		} else {
			history = getData.get();
			DivCheckSharedData.share(key, history);
		}
		return history;
	}

	private Map<String, Object> getComHistory(GeneralDate workingDate, String companyId) {
		Map<String, Object> res = new HashMap<>();
		CompanyDivergenceReferenceTimeHistory historyM = getShared(COM_DIV_REF_TIME_HISTORY_KEY, 
																	() -> comDivHisRepo.findAll(companyId));
		DateHistoryItem history = historyM.items().stream().filter(c -> c.contains(workingDate)).findFirst().orElse(null);
		res.put(COMPANY_HISTORY_ITEM, history);
		return res;
	}
	
	private BusinessTypeCode getWorkInfo(String companyId, boolean isGet, String employeeId, GeneralDate workingDate){
		if(!isGet){
			return null;
		}
		BusinessTypeOfEmployeeHistory bteHis = getShared(BUSINESS_TYPE_HISTORY_KEY, 
													() -> bteHisRepo.findByEmployee(companyId, employeeId).orElse(null));
		if(bteHis == null){
			return null;
		}
		DateHistoryItem hisItem = bteHis.getHistory().stream().filter(c -> c.contains(workingDate)).findFirst().orElse(null);
		if(hisItem == null){
			return null;
		}
		BusinessTypeOfEmployee bte = bteRepo.findByHistoryId(hisItem.identifier()).orElse(null);
		return bte == null ? null : bte.getBusinessTypeCode();
	}

	/** 「乖離時間」を取得する */
	private Map<Integer, DivergenceTime> getDivergenceTimeErAl(
			String companyId, List<Integer> divergenceCheckNos) {
		Map<Integer, DivergenceTime> divergenceTimeErAls = getShared(DIVERGENCE_TIME_KEY, () -> {
					return diverTimeRepo.getDivTimeListByNo(companyId, divergenceCheckNos).stream()
								.collect(Collectors.toMap(c -> c.getDivergenceTimeNo(), c -> c));
				});
		return divergenceTimeErAls;
	}

	/** 「勤務実績のエラーアラーム」を取得する */
	private List<ErrorAlarmWorkRecord> getErrorAlarmCheck(String companyId) {
		return erAlConditionRepo.getListErAlByListCode(companyId, SYSTEM_FIXED_CHECK_CODE);
	}

	/** ドメインモデル「乖離基準時間利用単位」を取得する */
	private boolean isCheckWithWorkType(String companyId) {
		val divRefUnit = divRefUnitRepo.findByCompanyId(companyId).orElse(null);
		return divRefUnit == null ? false : divRefUnit.isWorkTypeUseSet();
	}
	
	/** 乖離時間のチェック */
	private boolean evaluateDivergenceTime(int divNo, int divergenceTime, 
			boolean isAlarm, boolean isCheckByWorkType, String history, BusinessTypeCode bsCode,
			boolean isRemoveErrorByInputReason, boolean isRemoveErrorBySelectReasonCode, 
			DivergenceReasonContent reason,  DiverdenceReasonCode reasonCode){
		if(history == null){
			return true;
		}
		if(isCheckByWorkType){
			return evaluateByWorkType(divNo, history, divergenceTime, isAlarm, bsCode, 
					isRemoveErrorByInputReason, isRemoveErrorBySelectReasonCode, reason, reasonCode);
		} else {
			return evaluateByCompany(divNo, divergenceTime, isAlarm, history, 
					isRemoveErrorByInputReason, isRemoveErrorBySelectReasonCode, reason, reasonCode);
		}
	}
	
	private void shareDivRefTime(boolean isBussiness, String hisId, List<Integer> divNos, BusinessTypeCode bsCode){
		if(isBussiness){
			DivCheckSharedData.share(WT_DIV_REF_TIME_KEY + hisId + bsCode, 
					wtDivRefTime.findByHistoryIdAndDivergenceTimeNos(bsCode, hisId, divNos));
		} else {
			DivCheckSharedData.share(COM_DIV_REF_TIME_KEY + hisId, comDivRefTime.findByHistoryIdAndDivergenceTimeNos(hisId, divNos));
		}
	}

	/** 勤務種別ごとの乖離基準時間でチェックする */
	private boolean evaluateByWorkType(int divNo, String history, int divergenceTime, 
			boolean isAlarm, BusinessTypeCode bsCode, boolean isRemoveErrorByInputReason,
			boolean isRemoveErrorBySelectReasonCode, DivergenceReasonContent reason, DiverdenceReasonCode reasonCode){
		WorkTypeDivergenceReferenceTime divTimeBaseByWT = getWTDivRefTime(divNo, history, bsCode);
		if(divTimeBaseByWT == null || !divTimeBaseByWT.getDivergenceReferenceTimeValue().isPresent()){
			return true;
		}
		return evaluate(divergenceTime, isAlarm, divTimeBaseByWT.getNotUseAtr() == NotUseAtr.USE, 
						divTimeBaseByWT.getDivergenceReferenceTimeValue().get(), isRemoveErrorByInputReason,
						isRemoveErrorBySelectReasonCode, reason, reasonCode);
	}

	/** 会社の履歴項目でチェックする */
	private boolean evaluateByCompany(int divNo,  int divergenceTime, boolean isAlarm, String history,
			boolean isRemoveErrorByInputReason, boolean isRemoveErrorBySelectReasonCode, 
			DivergenceReasonContent reason, DiverdenceReasonCode reasonCode){
		CompanyDivergenceReferenceTime divTimeBaseByCom = getComDivRefTime(divNo, history);
		if(divTimeBaseByCom == null || !divTimeBaseByCom.getDivergenceReferenceTimeValue().isPresent()){
			return true;
		}
		return evaluate(divergenceTime, isAlarm, divTimeBaseByCom.getNotUseAtr() == NotUseAtr.USE,
						divTimeBaseByCom.getDivergenceReferenceTimeValue().get(), isRemoveErrorByInputReason, 
						isRemoveErrorBySelectReasonCode, reason, reasonCode);
	}
	
	@SuppressWarnings("unchecked")
	private WorkTypeDivergenceReferenceTime getWTDivRefTime(int divNo, String history, BusinessTypeCode bsCode) {
		List<WorkTypeDivergenceReferenceTime> lst = (List<WorkTypeDivergenceReferenceTime>) 
												DivCheckSharedData.getShared(WT_DIV_REF_TIME_KEY + history + bsCode);
		return lst.stream().filter(d -> d.getDivergenceTimeNo() == divNo).findFirst().orElse(null);
	}

	@SuppressWarnings("unchecked")
	private CompanyDivergenceReferenceTime getComDivRefTime(int divNo, String history) {
		List<CompanyDivergenceReferenceTime> lst = (List<CompanyDivergenceReferenceTime>) 
												DivCheckSharedData.getShared(COM_DIV_REF_TIME_KEY + history);
		return lst.stream().filter(d -> d.getDivergenceTimeNo() == divNo).findFirst().orElse(null);
	}

	/** 乖離時間を判定する */
	private boolean evaluate(int divergenceTime, boolean isAlarm, boolean isUse, DivergenceReferenceTimeValue standard, 
			boolean isRemoveErrorByInputReason, boolean isRemoveErrorBySelectReasonCode, 
			DivergenceReasonContent reason, DiverdenceReasonCode reasonCode){
		if(!isUse || standard == null){
			return true;
		}
		DivergenceReferenceTime sdTime = isAlarm ? standard.getAlarmTime().orElse(null) : standard.getErrorTime().orElse(null);
		if(sdTime != null && sdTime.v() > 0){
			if(divergenceTime >= sdTime.valueAsMinutes()) {
				// パラメータ「エラーの解除方法．乖離理由が選択された場合，エラーを解除する」をチェックする
				if(isRemoveErrorBySelectReasonCode) {
					if(reasonCode != null && !reasonCode.v().isEmpty()) {
						return true;
					}
				}
				if(isRemoveErrorByInputReason) {
					if(reason != null && !reason.v().isEmpty()) {
						return true;
					}
				}
				return false;
			}
		}
		return true;
	}
	
	/** ドメインモデル「勤務種別ごとの乖離時間のエラーアラームメッセージ」を取得する */
	private String getMessage(boolean isByWt, boolean isWithBonusText, String comId, int divNo, boolean isAlarm, BusinessTypeCode wtCode) {
		ErrorAlarmMessage message = null;
		if(!isByWt) {
			DivergenceTimeErrorAlarmMessage mes = getShared(DIVERGENCE_MESSAGE_KEY + comId + divNo, 
					() -> this.divMesRepo.findByDivergenceTimeNo(new CompanyId(comId), divNo).orElse(null));
			if(mes != null) {
				message = isAlarm ? mes.getAlarmMessage().orElse(null) : mes.getErrorMessage().orElse(null);
			}
		} else {
			WorkTypeDivergenceTimeErrorAlarmMessage mes = getShared(DIVERGENCE_MESSAGE_KEY + comId + divNo + wtCode, 
					() -> this.wtDivMesRepo.getByDivergenceTimeNo(divNo, new CompanyId(comId), wtCode).orElse(null));
			if(mes != null) {
				message = isAlarm ? mes.getAlarmMessage().orElse(null) : mes.getErrorMessage().orElse(null);
			}
		}
		if(message != null) {
			return !isWithBonusText ? message.v() : StringUtils.join(message.v(), 
									getShared(KDW003_108_KEY, () -> resources.localize(KDW003_108_KEY).orElse(EMPTY_STRING)));
		}
		return EMPTY_STRING;
	}
	
	private int getNumberFromString(String code){
		String number = code.replace(code.replaceAll(PATTERN_1, EMPTY_STRING), EMPTY_STRING);
		if(number.isEmpty()){ throw new RuntimeException(RUNTIME_ERROR_1 + code); }
		return Integer.parseInt(number);
	}
	
	private class SelfConfirmContentRegistry {
		private List<SelfConfirmContent> content;
		
		private String empId;

		public SelfConfirmContentRegistry(List<SelfConfirmContent> content, String empId) {
			super();
			this.content = content;
			this.empId = empId;
		}
	}
	
	private class SelfConfirmContent {
		private GeneralDate ymd;
		
		private boolean confirmStatus;

		public SelfConfirmContent(GeneralDate ymd, boolean confirmStatus) {
			super();
			this.ymd = ymd;
			this.confirmStatus = confirmStatus;
		}
	}
}
