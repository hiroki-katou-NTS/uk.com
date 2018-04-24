package nts.uk.ctx.at.record.dom.divergencetime.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import lombok.val;
import nts.arc.i18n.I18NResources;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.record.dom.actualworkinghours.repository.AttendanceTimeRepository;
import nts.uk.ctx.at.record.dom.dailyperformanceformat.primitivevalue.BusinessTypeCode;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.converter.DailyRecordToAttendanceItemConverter;
import nts.uk.ctx.at.record.dom.divergence.time.DivergenceTime;
import nts.uk.ctx.at.record.dom.divergence.time.DivergenceTimeRepository;
import nts.uk.ctx.at.record.dom.divergence.time.history.CompanyDivergenceReferenceTimeHistoryRepository;
import nts.uk.ctx.at.record.dom.divergence.time.history.CompanyDivergenceReferenceTimeRepository;
import nts.uk.ctx.at.record.dom.divergence.time.history.DivergenceReferenceTime;
import nts.uk.ctx.at.record.dom.divergence.time.history.DivergenceReferenceTimeUsageUnitRepository;
import nts.uk.ctx.at.record.dom.divergence.time.history.DivergenceReferenceTimeValue;
import nts.uk.ctx.at.record.dom.divergence.time.history.WorkTypeDivergenceReferenceTimeHistoryRepository;
import nts.uk.ctx.at.record.dom.divergence.time.history.WorkTypeDivergenceReferenceTimeRepository;
import nts.uk.ctx.at.record.dom.divergence.time.message.DivergenceTimeErrorAlarmMessage;
import nts.uk.ctx.at.record.dom.divergence.time.message.DivergenceTimeErrorAlarmMessageRepository;
import nts.uk.ctx.at.record.dom.divergence.time.message.ErrorAlarmMessage;
import nts.uk.ctx.at.record.dom.divergence.time.message.WorkTypeDivergenceTimeErrorAlarmMessage;
import nts.uk.ctx.at.record.dom.divergence.time.message.WorkTypeDivergenceTimeErrorAlarmMessageRepository;
import nts.uk.ctx.at.record.dom.divergencetime.DivergenceReasonContent;
import nts.uk.ctx.at.record.dom.workinformation.repository.WorkInformationRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.EmployeeDailyPerError;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.ErrorAlarmWorkRecord;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.ErrorAlarmWorkRecordRepository;
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
	private WorkInformationRepository workInfoRepo;
	
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
	private I18NResources resources;
	
	public static List<String> SYSTEM_FIXED_CHECK_CODE = Arrays.asList("D001", "D002", "D003", "D004", "D005", 
			"D006", "D007", "D008", "D009", "D010", "D011", "D012", "D013", "D014", "D015", "D016", "D017", "D018", "D019", "D020");
	private final String WORKTYPE_HISTORY_ITEM = "W_HIS";
	private final String COMPANY_HISTORY_ITEM = "C_HIS";
	private final String WORKTYPE_CODE = "WTC";

	/** システム固定エラー：　乖離時間をチェックする */
	public List<EmployeeDailyPerError> divergenceTimeCheckBySystemFixed(String companyId, String employeeId, GeneralDate workingDate){
		List<EmployeeDailyPerError> checkR = new ArrayList<>(); 
		attendanceTimeRepo.find(employeeId, workingDate).ifPresent(at -> {
			val divergenTime = at.getActualWorkingTimeOfDaily().getDivTime().getDivergenceTime(); 
			checkR.addAll(check(companyId, employeeId, workingDate, divergenTime));
		});
		return checkR;
	}
	
	/** システム固定エラー：　乖離時間をチェックする */
	public List<EmployeeDailyPerError> divergenceTimeCheckBySystemFixed(String companyId, String employeeId, GeneralDate workingDate,
			List<nts.uk.ctx.at.record.dom.divergencetimeofdaily.DivergenceTime> divergenTime){
		return check(companyId, employeeId, workingDate, divergenTime);
	}

	private List<EmployeeDailyPerError> check(String companyId, String employeeId, GeneralDate workingDate,
			List<nts.uk.ctx.at.record.dom.divergencetimeofdaily.DivergenceTime> divergenTime) {
		List<EmployeeDailyPerError> checkR = new ArrayList<>(); 
		boolean isCheckByWorkType = isCheckWithWorkType(companyId);
		boolean isToday = GeneralDate.today().equals(workingDate);
		
		List<ErrorAlarmWorkRecord> erAlConditions = getErrorAlarmCheck(companyId);
		if(erAlConditions.isEmpty()) {
			return checkR;
		}
		List<Integer> divergenceCheckNos = erAlConditions.stream()
				.map(c -> getNumberFromString(c.getCode().v()) / 2).distinct().collect(Collectors.toList());

		val divergenceTimeErAls = getDivergenceTimeErAl(companyId, divergenceCheckNos);
		if(divergenceTimeErAls.isEmpty()) {
			return checkR;
		}
		val historyR = getHistory(isCheckByWorkType, employeeId, workingDate, companyId); 
		boolean isWHis = historyR.get(WORKTYPE_HISTORY_ITEM) != null;
		val historyItem = (DateHistoryItem) (isWHis ? historyR.get(WORKTYPE_HISTORY_ITEM) : historyR.get(COMPANY_HISTORY_ITEM));
		val bsCode = isWHis ? (BusinessTypeCode) historyR.get(WORKTYPE_CODE) : null; 
		erAlConditions.stream().forEach(erAl -> {
			int numberIn = getNumberFromString(erAl.getCode().v());
			boolean isAlarm = numberIn % 2 == 0;
			val divergenceTimeErAl = divergenceTimeErAls.get(numberIn / 2);
			if(divergenceTimeErAl.isDivergenceTimeUse()){
				divergenTime.stream().filter(dt -> dt.getDivTimeId() == divergenceTimeErAl.getDivergenceTimeNo()).findFirst().ifPresent(dt -> {
					int divergenceTime = 0;
					boolean isPcDivergence = dt.getDivTimeId() == 7 && isToday;
					if(isPcDivergence) {
						divergenceTime = calcCurrentDivergenceTime(employeeId, workingDate);
					} else if (dt.getDivTimeAfterDeduction() != null) {
						divergenceTime = dt.getDivTimeAfterDeduction().valueAsMinutes();
					}
					boolean valid = evaluateDivergenceTime(numberIn / 2, divergenceTime, 
							isAlarm, isWHis, historyItem, bsCode,
							divergenceTimeErAl.getErrorCancelMedthod().isReasonInputed(),
							dt.getDivReason());
					if(!valid){
						checkR.add(new EmployeeDailyPerError(companyId, employeeId, workingDate, 
								erAl.getCode(), Arrays.asList(erAl.getErrorDisplayItem().intValue()), 
								erAl.getCancelableAtr() ? 1 : 0, getMessage(isCheckByWorkType, isPcDivergence, companyId, numberIn / 2, isAlarm, bsCode)));
					}
				});
//				divergenTime.stream().forEach(dt -> {
//					boolean valid = evaluateDivergenceTime(numberIn / 2, dt.getDivTimeAfterDeduction(), 
//							isAlarm, isWHis, historyItem, bsCode);
//					if(!valid){
//						checkR.add(new EmployeeDailyPerError(companyId, employeeId, workingDate, 
//								erAl.getCode(), Arrays.asList(erAl.getErrorDisplayItem().intValue()), 
//								erAl.getCancelableAtr() ? 1 : 0));
//					}
//				});
			}
		});
		return checkR;
	}

	/** 上記の計算で求めた時間を発生した乖離時間として処理を進める */
	private int calcCurrentDivergenceTime(String employeeId, GeneralDate workingDate) {
		val timeLeave = timeLeaveRepo.findByKey(employeeId, workingDate);
		/** 勤怠項目ID　34（退勤時刻1） */
		if(timeLeave.isPresent()) {
			val valued = convertHelper.withTimeLeaving(timeLeave.get()).convert(34);
			if(valued.isPresent() && valued.get().value() != null) {
				GeneralDateTime now = GeneralDateTime.now();
				int currentTime = now.hours()*60 + now.minutes();
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
		
		val workTypeCode = getWorkInfo(isCheckByWorkType, employeeId, workingDate);
		if(workTypeCode == null){
			return getComHistory(workingDate, companyId);
		}
		DateHistoryItem history = wtDivHisRepo.findAll(companyId, workTypeCode).items()
				.stream().filter(c -> c.contains(workingDate)).findFirst().orElse(null);
		if(history == null){
			return getComHistory(workingDate, companyId);
		}
		Map<String, Object> res = new HashMap<>();
		res.put(WORKTYPE_HISTORY_ITEM, history);
		res.put(WORKTYPE_CODE, workTypeCode);
		return res;
	}

	private Map<String, Object> getComHistory(GeneralDate workingDate, String companyId) {
		Map<String, Object> res = new HashMap<>();
		DateHistoryItem history;
		val historyM = comDivHisRepo.findAll(companyId);
		history = historyM.items().stream().filter(c -> c.contains(workingDate)).findFirst().orElse(null);
		res.put(COMPANY_HISTORY_ITEM, history);
		return res;
	}
	
	private BusinessTypeCode getWorkInfo(boolean isGet, String employeeId, GeneralDate workingDate){
		if(!isGet){
			return null;
		}
		val workInfo = workInfoRepo.find(employeeId, workingDate).orElse(null);
		return workInfo == null ? null : new BusinessTypeCode(workInfo.getRecordInfo().getWorkTypeCode().v());
	}

	/** 「乖離時間」を取得する */
	private Map<Integer, DivergenceTime> getDivergenceTimeErAl(
			String companyId, List<Integer> divergenceCheckNos) {
		return diverTimeRepo.getDivTimeListByNo(companyId, divergenceCheckNos).stream()
				.collect(Collectors.toMap(c -> c.getDivergenceTimeNo(), c -> c));
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
			boolean isAlarm, boolean isCheckByWorkType, DateHistoryItem history, BusinessTypeCode bsCode,
			boolean isRemoveErrorByInputReason, DivergenceReasonContent reason){
		if(history == null){
			return true;
		}
		if(isCheckByWorkType){
			return evaluateByWorkType(divNo, history, divergenceTime, isAlarm, bsCode, isRemoveErrorByInputReason, reason);
		} else {
			return evaluateByCompany(divNo, divergenceTime, isAlarm, history, isRemoveErrorByInputReason, reason);
		}
	}

	/** 勤務種別ごとの乖離基準時間でチェックする */
	public boolean evaluateByWorkType(int divNo, DateHistoryItem history, int divergenceTime, 
			boolean isAlarm, BusinessTypeCode bsCode, boolean isRemoveErrorByInputReason, DivergenceReasonContent reason){
		val divTimeBaseByWT = wtDivRefTime.findByKey(history.identifier(), bsCode, divNo).orElse(null);
		if(divTimeBaseByWT == null || !divTimeBaseByWT.getDivergenceReferenceTimeValue().isPresent()){
			return true;
		}
		return evaluate(divergenceTime, isAlarm, divTimeBaseByWT.getNotUseAtr() == NotUseAtr.USE, 
				divTimeBaseByWT.getDivergenceReferenceTimeValue().get(), isRemoveErrorByInputReason, reason);
	}

	/** 会社の履歴項目でチェックする */
	public boolean evaluateByCompany(int divNo,  int divergenceTime, boolean isAlarm, DateHistoryItem history,
			boolean isRemoveErrorByInputReason, DivergenceReasonContent reason){
		if(history == null){
			return true;
		}
		val divTimeBaseByCom = comDivRefTime.findByKey(history.identifier(), divNo).orElse(null);
		if(divTimeBaseByCom == null || !divTimeBaseByCom.getDivergenceReferenceTimeValue().isPresent()){
			return true;
		}
		return evaluate(divergenceTime, isAlarm, divTimeBaseByCom.getNotUseAtr() == NotUseAtr.USE,
				divTimeBaseByCom.getDivergenceReferenceTimeValue().get(), isRemoveErrorByInputReason, reason);
	}

	/** 乖離時間を判定する */
	public boolean evaluate(int divergenceTime, boolean isAlarm, boolean isUse, DivergenceReferenceTimeValue standard, 
			boolean isRemoveErrorByInputReason, DivergenceReasonContent reason){
		if(!isUse || standard == null){
			return true;
		}
		DivergenceReferenceTime sdTime = isAlarm ? standard.getAlarmTime().orElse(null) : standard.getErrorTime().orElse(null);
		if(sdTime != null){
			boolean isValid = sdTime.lessThanOrEqualTo(0) || sdTime.greaterThan(divergenceTime);
			if(!isValid) {
				if(!isRemoveErrorByInputReason) {
					return false;
				}
				if(reason == null || reason.v() == null || reason.v().isEmpty()) {
					return false;
				}
			}
		}
		return true;
	}
	
	/** ドメインモデル「勤務種別ごとの乖離時間のエラーアラームメッセージ」を取得する */
	private String getMessage(boolean isByWt, boolean isWithBonusText, String comId, int divNo, boolean isAlarm, BusinessTypeCode wtCode) {
		ErrorAlarmMessage message = null;
		if(isByWt) {
			DivergenceTimeErrorAlarmMessage mes = this.divMesRepo.findByDivergenceTimeNo(new CompanyId(comId), divNo).orElse(null);
			if(mes != null) {
				message = isAlarm ? mes.getAlarmMessage().orElse(null) : mes.getErrorMessage().orElse(null);
			}
		} else {
			WorkTypeDivergenceTimeErrorAlarmMessage mes = this.wtDivMesRepo.getByDivergenceTimeNo(divNo, new CompanyId(comId), wtCode).orElse(null);
			if(mes != null) {
				message = isAlarm ? mes.getAlarmMessage().orElse(null) : mes.getErrorMessage().orElse(null);
			}
		}
		if(message != null) {
			return isWithBonusText ? StringUtils.join(message.v(), resources.localize("KDW003_108")) : message.v();
		}
		return "";
	}
	
	private int getNumberFromString(String code){
		String number = code.replaceAll(code.replaceAll("[0-9]+$", ""), "");
		if(number.isEmpty()){ throw new RuntimeException("勤務実績のエラーアラームのコードのフォーマットが正しくない：　" + code); }
		return Integer.parseInt(number);
	}
}
