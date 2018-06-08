package nts.uk.ctx.at.record.dom.divergencetime.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import lombok.val;
import nts.arc.diagnose.stopwatch.Stopwatches;
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
import nts.uk.ctx.at.record.dom.divergencetime.service.MasterShareBus.MasterShareContainer;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.EmployeeDailyPerError;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.ErrorAlarmWorkRecord;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.ErrorAlarmWorkRecordRepository;
import nts.uk.ctx.at.record.dom.workrecord.errorsetting.SystemFixedErrorAlarm;
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
	private IdentityProcessUseSetRepository iPSURepo;
	
	@Inject
	private IdentificationRepository identityRepo;
	
//	@Inject
//	private EmployeeDailyPerErrorRepository errorRepo;
//	
//	@Inject 
//	private ErrorAlarmWorkRecordRepository eaRecordRepo;
	
	@Inject
	private ApprovalProcessingUseSettingRepository approvalSettingRepo;
	
	@Inject
	private ApprovalStatusOfDailyPerforRepository approvalStateRepo;
	
	@Inject
	private AppRootStateConfirmAdapter appRootStateAdapter;
	
	@Inject
	private I18NResources resources;
	
	@Inject
	private BusinessTypeEmpOfHistoryRepository bteHisRepo;
	
	@Inject
	private BusinessTypeOfEmployeeRepository bteRepo;
	
	public static List<String> SYSTEM_FIXED_DIVERGENCE_CHECK_CODE = Arrays.asList("D001", "D002", "D003", "D004", "D005", 
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

	private final String SEPERATOR = "|";

	private final String RUNTIME_ERROR_1 = "勤務実績のエラーアラームのコードのフォーマットが正しくない：　";

	private final String RUNTIME_ERROR_2 = "CompanyDivergenceReferenceTimeHistory not found!! For Company: ";

	private final String RUNTIME_ERROR_3 = "本人確認処理の利用設定 not found!!!";

	private final String WORK_TYPE_SETTING = "WorkTypeSetting";

	private final String ERROR_ALARM_CHECK = "ErrorAlarmCheck";

	private final String DIVERGENCE_MESSAGE_KEY = "DivergenceMessage";

	private final String KDW003_108_KEY = "KDW003_108";

	private final String BUSINESS_TYPE_CODE_D = "BusinessTypeCode";

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
	public List<EmployeeDailyPerError> divergenceTimeCheckBySystemFixed(String comId, String empId, GeneralDate tarD){
		List<EmployeeDailyPerError> checkR = new ArrayList<>(); 
		attendanceTimeRepo.find(empId, tarD).ifPresent(at -> {
			checkR.addAll(divergenceTimeCheckBySystemFixed(comId, empId, tarD, 
					at.getActualWorkingTimeOfDaily().getDivTime().getDivergenceTime()));
		});
		return checkR;
	}
	
	/** 乖離時間（確認解除） */
	public List<EmployeeDailyPerError> divergenceTimeCheckBySystemFixed(String comId, String empId, 
			GeneralDate tarD, List<nts.uk.ctx.at.record.dom.divergencetimeofdaily.DivergenceTime> divTime){
		return divergenceTimeCheckBySystemFixed(comId, empId, tarD, divTime, null, Optional.empty());
	}
	
	/** 乖離時間（確認解除） */
	public List<EmployeeDailyPerError> divergenceTimeCheckBySystemFixed(String comId, String empId, 
			GeneralDate tarD, List<nts.uk.ctx.at.record.dom.divergencetimeofdaily.DivergenceTime> divTime, 
			Optional<TimeLeavingOfDailyPerformance> tl){
		return divergenceTimeCheckBySystemFixed(comId, empId, tarD, divTime, null, tl);
	}
	
	/** 乖離時間（確認解除） */
	public List<EmployeeDailyPerError> divergenceTimeCheckBySystemFixed(String comId, String empId, 
			GeneralDate tarD, List<nts.uk.ctx.at.record.dom.divergencetimeofdaily.DivergenceTime> divTime, 
			Optional<TimeLeavingOfDailyPerformance> tl, List<ErrorAlarmWorkRecord> erAls){
		return divergenceTimeCheckBySystemFixed(comId, empId, tarD, divTime, null, tl, erAls);
	}
	
	/** 乖離時間（確認解除） */
	public List<EmployeeDailyPerError> divergenceTimeCheckBySystemFixed(String comId, String empId, 
			GeneralDate tarD, List<nts.uk.ctx.at.record.dom.divergencetimeofdaily.DivergenceTime> divTime, 
			Optional<TimeLeavingOfDailyPerformance> tl, List<ErrorAlarmWorkRecord> erAls,
			List<DivergenceTime> divTimeErAlMs){
		return divergenceTimeCheckBySystemFixed(comId, empId, tarD, divTime, tl, erAls, divTimeErAlMs, null);
	}
	
	/** 乖離時間（確認解除） */
	public List<EmployeeDailyPerError> divergenceTimeCheckBySystemFixed(String comId, String empId, 
			GeneralDate tarD, List<nts.uk.ctx.at.record.dom.divergencetimeofdaily.DivergenceTime> divTime, 
			Optional<TimeLeavingOfDailyPerformance> tl, List<ErrorAlarmWorkRecord> erAls,
			List<DivergenceTime> divTimeErAlMs, MasterShareContainer shareContainer){
		Stopwatches.start("ERAL-Divergence");
		List<EmployeeDailyPerError> result = divergenceTimeCheckBySystemFixed(comId, empId, tarD, divTime, null, 
				tl, erAls, divTimeErAlMs, shareContainer);
		Stopwatches.stop("ERAL-Divergence");
		return result;
	}
	
	/** 乖離時間（確認解除） */
	public List<EmployeeDailyPerError> divergenceTimeCheckBySystemFixed(String comId, String empId, 
			GeneralDate tarD, List<nts.uk.ctx.at.record.dom.divergencetimeofdaily.DivergenceTime> divTime,
			IdentityProcessUseSet iPUS, Optional<TimeLeavingOfDailyPerformance> tl){
		return divergenceTimeCheckBySystemFixed(comId, empId, tarD, divTime, iPUS, tl, null);
	}
	
	/** 乖離時間（確認解除） */
	public List<EmployeeDailyPerError> divergenceTimeCheckBySystemFixed(String comId, String empId,
			GeneralDate tarD, List<nts.uk.ctx.at.record.dom.divergencetimeofdaily.DivergenceTime> divTime, 
			IdentityProcessUseSet iPUS, Optional<TimeLeavingOfDailyPerformance> tl, 
			List<ErrorAlarmWorkRecord> erAls){
		return divergenceTimeCheckBySystemFixed(comId, empId, tarD, divTime, iPUS, tl, erAls, null);
	}
	
	/** 乖離時間（確認解除） */
	public List<EmployeeDailyPerError> divergenceTimeCheckBySystemFixed(String comId, String empId,
			GeneralDate tarD, List<nts.uk.ctx.at.record.dom.divergencetimeofdaily.DivergenceTime> divTime, 
			IdentityProcessUseSet iPUS, Optional<TimeLeavingOfDailyPerformance> tl, 
			List<ErrorAlarmWorkRecord> erAls, List<DivergenceTime> divTimeErAlMs){
		MasterShareContainer shareContainer = MasterShareBus.open();
		List<EmployeeDailyPerError> result = divergenceTimeCheckBySystemFixed(comId, empId, tarD, divTime, 
				iPUS, tl, erAls, null, shareContainer);
		shareContainer.clearAll();
		return result;
	}
	
	/** 乖離時間（確認解除） */
	public List<EmployeeDailyPerError> divergenceTimeCheckBySystemFixed(String comId, String empId,
			GeneralDate tarD, List<nts.uk.ctx.at.record.dom.divergencetimeofdaily.DivergenceTime> divTime, 
			IdentityProcessUseSet iPUS, Optional<TimeLeavingOfDailyPerformance> tl, 
			List<ErrorAlarmWorkRecord> erAls, List<DivergenceTime> divTimeErAlMs,
			MasterShareContainer shareContainer){
		List<EmployeeDailyPerError> errors = check(comId, empId, tarD, divTime, tl, erAls, divTimeErAlMs, shareContainer);
		if(errors.isEmpty()) {
			return errors;
		}
		if(iPUS == null) {
			iPUS = shareContainer.getShared(join(IDENTITY_PUS_KEY, SEPERATOR, comId), () -> iPSURepo.findByKey(comId)
														.orElseThrow(() -> new RuntimeException(RUNTIME_ERROR_3)));
		}
		return removeconfirm(comId, empId, tarD, errors, iPUS, shareContainer);
	}
	
	/** 確認解除 */
	private List<EmployeeDailyPerError> removeconfirm(String comId, String empId, GeneralDate tarD, 
			List<EmployeeDailyPerError> errors, IdentityProcessUseSet iPUS, MasterShareContainer shareContainer) {
		List<EmployeeDailyPerError> divEr67 = errors.stream().filter(c -> c.getErrorAlarmWorkRecordCode() != null
				&& (c.getErrorAlarmWorkRecordCode().v().equals(SystemFixedErrorAlarm.DIVERGENCE_ERROR_6.value)
				|| c.getErrorAlarmWorkRecordCode().v().equals(SystemFixedErrorAlarm.DIVERGENCE_ERROR_7.value))
				&& c.getDate().equals(tarD) && c.getEmployeeID().equals(empId)).collect(Collectors.toList());
		
		if (!divEr67.isEmpty()) {
			if (iPUS.isUseConfirmByYourself()) {
				identityRepo.findByCode(empId, tarD).ifPresent(id -> {
					removeSelfIdentity(comId, iPUS, divEr67, empId);
				});
			}
			shareContainer.getShared(join(APPROVAL_SETTING_KEY, SEPERATOR, comId),
					() -> approvalSettingRepo.findByCompanyId(comId)).ifPresent(as -> {
				if (as.getUseDayApproverConfirm() != null && as.getUseDayApproverConfirm()
						&& as.getSupervisorConfirmErrorAtr() != null
						&& !as.getSupervisorConfirmErrorAtr().equals(ConfirmationOfManagerOrYouself.CAN_CHECK)) {
					approvalStateRepo.find(empId, tarD).ifPresent(asd -> {
						/** 承認状態をすべてクリアする */
						appRootStateAdapter.clearAppRootstate(asd.getRootInstanceID());
					});
				}
			});
		}
		return errors;
	}
	
	/** 日の本人確認を解除する */
	private void removeSelfIdentity(String comId, IdentityProcessUseSet iPUS, List<EmployeeDailyPerError> divEr67, String empId) {
		iPUS.getYourSelfConfirmError().ifPresent(sConEr -> {
			if (sConEr != SelfConfirmError.CAN_CONFIRM_WHEN_ERROR) {
				// fix remove ドメインモデル「日の本人確認」を削除する Thanh
				divEr67.stream().forEach(c -> {
					identityRepo.remove(comId, empId, c.getDate());
				});
			}
		});
	}
	
	/** システム固定エラー：　乖離時間をチェックする */
	private List<EmployeeDailyPerError> check(String comId, String empId, GeneralDate tarD,
			List<nts.uk.ctx.at.record.dom.divergencetimeofdaily.DivergenceTime> divTime, 
			Optional<TimeLeavingOfDailyPerformance> tl, List<ErrorAlarmWorkRecord> erAls,
			List<DivergenceTime> divTimeErAlMs, MasterShareContainer shareContainer) {
		List<EmployeeDailyPerError> checkR = new ArrayList<>(); 
		boolean checkByWT = shareContainer.getShared(join(WORK_TYPE_SETTING, SEPERATOR, comId),
								() -> isCheckWithWorkType(comId));
		boolean isToday = shareContainer.getShared(TODAY_KEY, () -> GeneralDate.today()).equals(tarD);
		
		if(erAls == null || erAls.isEmpty()){
			erAls = shareContainer.getShared(join(ERROR_ALARM_CHECK, SEPERATOR, comId), 
					() -> erAlConditionRepo.getListErAlByListCode(comId, SYSTEM_FIXED_DIVERGENCE_CHECK_CODE));
		}
		if(erAls.isEmpty()) {
			return checkR;
		}
		List<Integer> divCheckNos = erAls.stream().map(c -> getNo(getNumber(c.getCode().v())))
													.distinct().collect(Collectors.toList());

		List<DivergenceTime> divTimeErAls = getDivergenceTimeErAl(comId, divCheckNos, divTimeErAlMs, shareContainer);
		if(divTimeErAls.isEmpty()) {
			return checkR;
		}
		val historyR = getHistory(checkByWT, empId, tarD, comId, shareContainer); 
		boolean isWHis = historyR.get(WORKTYPE_HISTORY_ITEM) != null;
		DateHistoryItem  hisItem = getHisItem(historyR, isWHis);
		BusinessTypeCode bsCode = isWHis ? (BusinessTypeCode) historyR.get(WORKTYPE_CODE) : null; 
		if(hisItem == null){
			return checkR;
		}
		shareDivRefTime(isWHis, hisItem.identifier(), divCheckNos, bsCode, shareContainer);
		erAls.stream().forEach(erAl -> {
			int numberIn = getNumber(erAl.getCode().v()),
				divNo = getNo(numberIn);
			boolean isAlarm = numberIn % 2 == 0;
			divTimeErAls.stream().filter(d -> d.getDivergenceTimeNo() == divNo).findFirst().ifPresent(de -> {
				divTime.stream().filter(dt -> dt.getDivTimeId() == de.getDivergenceTimeNo()).findFirst().ifPresent(dt -> {
					boolean isPcLogOffDiv = dt.getDivTimeId() == LOGOFF_DIV_NO && isToday;
					// add DivResonCode to check Thanh
					if(!evaluateDivTime(divNo, isAlarm, isWHis, hisItem.identifier(), bsCode, de, shareContainer, dt,
							 			getDivTimeValue(empId, tarD, tl, dt, isPcLogOffDiv, shareContainer))){
						checkR.add(newError(comId, empId, tarD, erAl, 
											getMessage(isWHis, isPcLogOffDiv, comId, divNo, isAlarm, bsCode, shareContainer)));
					}
				});
			});
		});
		shareContainer.share("LAST_TIME_CHECK_NO", divCheckNos);
		return checkR;
	}

	private int getDivTimeValue(String empId, GeneralDate tarD, Optional<TimeLeavingOfDailyPerformance> tl,
			nts.uk.ctx.at.record.dom.divergencetimeofdaily.DivergenceTime dt, boolean isPcDivergence,
			MasterShareContainer shareContainer) {
		if(isPcDivergence) {
			return calcCurrentDivergenceTime(empId, tarD, tl, shareContainer);
		} 
		return dt.getDivTimeAfterDeduction() == null ? 0 : dt.getDivTimeAfterDeduction().valueAsMinutes();
	}

	private DateHistoryItem getHisItem(Map<String, Object> historyR, boolean isWHis) {
		return (DateHistoryItem) (isWHis ? historyR.get(WORKTYPE_HISTORY_ITEM) : historyR.get(COMPANY_HISTORY_ITEM));
	}

	/** 上記の計算で求めた時間を発生した乖離時間として処理を進める */
	private int calcCurrentDivergenceTime(String employeeId, GeneralDate workingDate, 
			Optional<TimeLeavingOfDailyPerformance> timeLeave, MasterShareContainer shareContainer) {
		if(!timeLeave.isPresent()){
			timeLeave = timeLeaveRepo.findByKey(employeeId, workingDate);
		}
		/** 勤怠項目ID　34（退勤時刻1） */
		if(timeLeave.isPresent()) {
			val valued = convertHelper.withTimeLeaving(timeLeave.get()).convert(TIME_LEAVE_ITEM);
			if(valued.isPresent() && valued.get().value() != null) {
				GeneralDateTime now = shareContainer.getShared(TIME_NOW_KEY, () -> GeneralDateTime.now());
				int currentTime = now.hours() * 60 + now.minutes();
				return currentTime - (int) valued.get().value();
			}
		}
		return 0;
	}
	
	/** 履歴項目を取得する */
	private Map<String, Object> getHistory(boolean isCheckByWT, String empId, GeneralDate tarD, String comId,
			MasterShareContainer shareContainer){
		if (!isCheckByWT) {
			return getComHistory(tarD, comId, shareContainer);
		}
		
		BusinessTypeCode workTypeCode = getWorkInfo(comId, isCheckByWT, empId, tarD, shareContainer);
		if(workTypeCode == null){
			return getComHistory(tarD, comId, shareContainer);
		}
		DateHistoryItem history = shareContainer.getShared(
					join(comId, SEPERATOR, workTypeCode.toString()), () -> wtDivHisRepo.findAll(comId, workTypeCode))
										.items().stream().filter(c -> c.contains(tarD)).findFirst().orElse(null);
		if(history == null){
			return getComHistory(tarD, comId, shareContainer);
		}
		Map<String, Object> res = new HashMap<>();
		res.put(WORKTYPE_HISTORY_ITEM, history);
		res.put(WORKTYPE_CODE, workTypeCode);
		return res;
	}

	private Map<String, Object> getComHistory(GeneralDate tarD, String comId, MasterShareContainer shareContainer) {
		Map<String, Object> res = new HashMap<>();
		CompanyDivergenceReferenceTimeHistory historyM = shareContainer.getShared(
																	join(COM_DIV_REF_TIME_HISTORY_KEY, SEPERATOR, comId), 
																	() -> comDivHisRepo.findAll(comId));
		if(historyM == null) {
			throw new RuntimeException(join(RUNTIME_ERROR_2, comId));
		}
		DateHistoryItem history = historyM.items().stream().filter(c -> c.contains(tarD)).findFirst().orElse(null);
		res.put(COMPANY_HISTORY_ITEM, history);
		return res;
	}
	
	private BusinessTypeCode getWorkInfo(String comId, boolean isGet, String empId, GeneralDate tarD,
			MasterShareContainer shareContainer){
		BusinessTypeOfEmployeeHistory bteHis = !isGet ? null 
				: shareContainer.getShared(join(BUSINESS_TYPE_HISTORY_KEY, SEPERATOR, comId, SEPERATOR, empId), 
													() -> bteHisRepo.findByEmployee(comId, empId).orElse(null));
		if(bteHis == null){
			return null;
		}
		return shareContainer.getShared(join(BUSINESS_TYPE_CODE_D, SEPERATOR, tarD.toString(), SEPERATOR, empId), 
							() -> getBusinessType(tarD, bteHis));
	}

	private BusinessTypeCode getBusinessType(GeneralDate tarD, BusinessTypeOfEmployeeHistory bteHis) {
		DateHistoryItem hisItem = bteHis.getHistory().stream().filter(c -> c.contains(tarD)).findFirst().orElse(null);
		if(hisItem == null){
			return null;
		}
		BusinessTypeOfEmployee bte = bteRepo.findByHistoryId(hisItem.identifier()).orElse(null);
		return bte == null ? null : bte.getBusinessTypeCode();
	}

	/** 「乖離時間」を取得する */
	private List<DivergenceTime> getDivergenceTimeErAl(String comId, List<Integer> divCheckNos, 
			List<DivergenceTime> divTimeErAlMs, MasterShareContainer shareContainer) {
		if(divTimeErAlMs != null && !divTimeErAlMs.isEmpty()){
			return divTimeErAlMs;
		}
		return shareContainer.getShared(join(DIVERGENCE_TIME_KEY, SEPERATOR, comId, SEPERATOR, 
						StringUtils.join(divCheckNos.toArray(), SEPERATOR)), 
				() -> {
					return diverTimeRepo.getDivTimeListByNo(comId, divCheckNos).stream()
								.filter(div -> div.isDivergenceTimeUse())
								.sorted((c1, c2) -> Integer.compare(c1.getDivergenceTimeNo(), c2.getDivergenceTimeNo()))
								.collect(Collectors.toList());
				});
	}

	/** ドメインモデル「乖離基準時間利用単位」を取得する */
	private boolean isCheckWithWorkType(String companyId) {
		val divRefUnit = divRefUnitRepo.findByCompanyId(companyId).orElse(null);
		return divRefUnit == null ? false : divRefUnit.isWorkTypeUseSet();
	}
	
	/** 乖離時間のチェック */
	private boolean evaluateDivTime(int divNo, boolean isAlarm, boolean isCheckByWorkType, String history, 
			BusinessTypeCode bsCode, DivergenceTime divTimeEr, MasterShareContainer shareContainer, 
			nts.uk.ctx.at.record.dom.divergencetimeofdaily.DivergenceTime divTime, int divergenceTime){
		
		if(history == null){
			return true;
		}
		
		if(isCheckByWorkType){
			return evaluateByWorkType(divNo, history, divergenceTime, isAlarm, bsCode, divTimeEr, divTime, shareContainer);
		} else {
			return evaluateByCompany(divNo, divergenceTime, isAlarm, history, divTimeEr, divTime, shareContainer);
		}
	}
	
	private void shareDivRefTime(boolean isBussiness, String hisId, List<Integer> divNos, BusinessTypeCode bsCode, 
			MasterShareContainer shareContainer){
		List<Integer> lastNos = shareContainer.getShared("LAST_TIME_CHECK_NO", () -> new ArrayList<>());
		if(isBussiness){
			String key = join(WT_DIV_REF_TIME_KEY, SEPERATOR, hisId, SEPERATOR, bsCode.toString());
			if(!shareContainer.isShared(key) || !lastNos.containsAll(divNos)){
				shareContainer.share(key, wtDivRefTime.findByHistoryIdAndDivergenceTimeNos(bsCode, hisId, divNos));
			}
		} else {
			String key = join(COM_DIV_REF_TIME_KEY, SEPERATOR, hisId);
			if(!shareContainer.isShared(key) || !lastNos.containsAll(divNos)){
				shareContainer.share(key, comDivRefTime.findByHistoryIdAndDivergenceTimeNos(hisId, divNos));
			}
		}
	}

	/** 勤務種別ごとの乖離基準時間でチェックする */
	private boolean evaluateByWorkType(int divNo, String history, int divergenceTime, 
			boolean isAlarm, BusinessTypeCode bsCode, DivergenceTime divTimeEr, 
			nts.uk.ctx.at.record.dom.divergencetimeofdaily.DivergenceTime divTime, 
			MasterShareContainer shareContainer){
		
		WorkTypeDivergenceReferenceTime divTimeBaseByWT = getWTDivRefTime(divNo, history, bsCode, shareContainer);
		
		if(divTimeBaseByWT == null || !divTimeBaseByWT.getDivergenceReferenceTimeValue().isPresent()){
			return true;
		}
		
		return evaluate(divergenceTime, isAlarm, divTimeBaseByWT.getNotUseAtr() == NotUseAtr.USE, 
						divTimeBaseByWT.getDivergenceReferenceTimeValue().get(), divTimeEr, divTime);
	}

	/** 会社の履歴項目でチェックする */
	private boolean evaluateByCompany(int divNo,  int divergenceTime, boolean isAlarm, String history,
			DivergenceTime divTimeEr, nts.uk.ctx.at.record.dom.divergencetimeofdaily.DivergenceTime divTime, 
			MasterShareContainer shareContainer){
		
		CompanyDivergenceReferenceTime divTimeBaseByCom = getComDivRefTime(divNo, history, shareContainer);
		if(divTimeBaseByCom == null || !divTimeBaseByCom.getDivergenceReferenceTimeValue().isPresent()){
			return true;
		}
		
		return evaluate(divergenceTime, isAlarm, divTimeBaseByCom.getNotUseAtr() == NotUseAtr.USE,
						divTimeBaseByCom.getDivergenceReferenceTimeValue().get(), divTimeEr, divTime);
	}
	
	@SuppressWarnings("unchecked")
	private WorkTypeDivergenceReferenceTime getWTDivRefTime(int divNo, String hisId, BusinessTypeCode bsCode, 
			MasterShareContainer shareContainer) {
		List<WorkTypeDivergenceReferenceTime> lst = (List<WorkTypeDivergenceReferenceTime>) 
				shareContainer.getShared(join(WT_DIV_REF_TIME_KEY, SEPERATOR, hisId, SEPERATOR, bsCode.toString()));
		
		return lst.stream().filter(d -> d.getDivergenceTimeNo() == divNo).findFirst().orElse(null);
	}

	@SuppressWarnings("unchecked")
	private CompanyDivergenceReferenceTime getComDivRefTime(int divNo, String hisId,
			MasterShareContainer shareContainer) {
		
		List<CompanyDivergenceReferenceTime> lst = (List<CompanyDivergenceReferenceTime>) 
				shareContainer.getShared(join(COM_DIV_REF_TIME_KEY, SEPERATOR, hisId));
		
		return lst.stream().filter(d -> d.getDivergenceTimeNo() == divNo).findFirst().orElse(null);
	}

	/** 乖離時間を判定する */
	private boolean evaluate(int divergenceTime, boolean isAlarm, boolean isUse, DivergenceReferenceTimeValue standard, 
			DivergenceTime divTimeEr, nts.uk.ctx.at.record.dom.divergencetimeofdaily.DivergenceTime divTime){
		if(!isUse || standard == null){
			return true;
		}
		DivergenceReferenceTime sdTime = isAlarm ? standard.getAlarmTime().orElse(null) : standard.getErrorTime().orElse(null);
		if(sdTime != null && sdTime.v() > 0){
			if(divergenceTime >= sdTime.valueAsMinutes()) {
				// パラメータ「エラーの解除方法．乖離理由が選択された場合，エラーを解除する」をチェックする
				if(divTimeEr.getErrorCancelMedthod().isReasonSelected()) {
					
					if(divTime.getDivResonCode() != null && !divTime.getDivResonCode().v().isEmpty()) {
						return true;
					}
				}
				if(divTimeEr.getErrorCancelMedthod().isReasonInputed()) {
					if(divTime.getDivReason() != null && !divTime.getDivReason().v().isEmpty()) {
						return true;
					}
				}
				return false;
			}
		}
		return true;
	}

	/** ドメインモデル「勤務種別ごとの乖離時間のエラーアラームメッセージ」を取得する */
	private String getMessage(boolean isByWt, boolean isWithBonusText, String comId, int divNo, 
			boolean isAlarm, BusinessTypeCode wtCode, MasterShareContainer shareContainer) {
		ErrorAlarmMessage message = null;
		CompanyId comIdD = shareContainer.getShared("CompanyId", () -> new CompanyId(comId));
		if(!isByWt) {
			DivergenceTimeErrorAlarmMessage mes = shareContainer.getShared(
					join(DIVERGENCE_MESSAGE_KEY, SEPERATOR, comId, SEPERATOR, String.valueOf(divNo)), 
					() -> this.divMesRepo.findByDivergenceTimeNo(comIdD, divNo).orElse(null));
			if(mes != null) {
				message = isAlarm ? mes.getAlarmMessage().orElse(null) : mes.getErrorMessage().orElse(null);
			}
		} else {
			WorkTypeDivergenceTimeErrorAlarmMessage mes = shareContainer.getShared(
					join(DIVERGENCE_MESSAGE_KEY, SEPERATOR, comId, SEPERATOR, String.valueOf(divNo), SEPERATOR, wtCode.toString()), 
					() -> this.wtDivMesRepo.getByDivergenceTimeNo(divNo, comIdD, wtCode).orElse(null));
			if(mes != null) {
				message = isAlarm ? mes.getAlarmMessage().orElse(null) : mes.getErrorMessage().orElse(null);
			}
		}
		if(message != null) {
			return !isWithBonusText ? message.v() : join(message.v(), 
					shareContainer.getShared(KDW003_108_KEY, () -> resources.localize(KDW003_108_KEY).orElse(EMPTY_STRING)));
		}
		return EMPTY_STRING;
	}
	
	private String join(String... values){
		return StringUtils.join(values);
	}

	private int getNo(int numberIn) {
		return (numberIn + 1) / 2;
	}
	
	private int getNumber(String code){
		String number = code.replace(code.replaceAll(PATTERN_1, EMPTY_STRING), EMPTY_STRING);
		if(number.isEmpty()){ throw new RuntimeException(RUNTIME_ERROR_1 + code); }
		return Integer.parseInt(number);
	}

	private EmployeeDailyPerError newError(String comId, String empId, GeneralDate tarD, ErrorAlarmWorkRecord erAl, String mes) {
		return new EmployeeDailyPerError(comId, empId, tarD, erAl.getCode(), 
										Arrays.asList(erAl.getErrorDisplayItem()), 
										erAl.getCancelableAtr() ? 1 : 0, mes);
	}
	
/** 日の本人確認を登録する */
//private void registryIdentity(String companyId, IdentityProcessUseSet identityPUS, SelfConfirmContentRegistry selfConfirm) {
//	if(!identityPUS.getYourSelfConfirmError().isPresent()) {
//		return;
//	}
//	if(identityPUS.getYourSelfConfirmError().get() != SelfConfirmError.CAN_CONFIRM_WHEN_ERROR) {
//		List<EmployeeDailyPerError> errors = new ArrayList<>();
//		errors.addAll(selfConfirm.content.stream().map(c -> errorRepo.find(selfConfirm.empId, c.ymd))
//				.flatMap(List::stream).collect(Collectors.toList()));
//		if(!errors.isEmpty()) {
//			List<ErrorAlarmWorkRecord> eaRecords = eaRecordRepo.getListErAlByListCodeError(companyId, 
//					errors.stream().map(c -> c.getErrorAlarmWorkRecordCode().v()).collect(Collectors.toList()));
//			if(!eaRecords.isEmpty()) {
//				// fix remove ドメインモデル「日の本人確認」を削除する Thanh
//				selfConfirm.content.stream().filter(c -> !c.confirmStatus).forEach(c -> {
//					identityRepo.remove(companyId, selfConfirm.empId, c.ymd);
//				});
//				GeneralDate today = GeneralDate.today();
//				selfConfirm.content.stream().filter(c -> c.confirmStatus).forEach(c -> {
//					identityRepo.insert(new Identification(companyId, selfConfirm.empId, c.ymd, today));
//				});
//				return;
//			}
//		}
//	}
//}
}
