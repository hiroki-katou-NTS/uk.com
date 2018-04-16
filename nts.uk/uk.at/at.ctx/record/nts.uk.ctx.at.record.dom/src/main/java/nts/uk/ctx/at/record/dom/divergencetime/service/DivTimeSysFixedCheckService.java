package nts.uk.ctx.at.record.dom.divergencetime.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.actualworkinghours.repository.AttendanceTimeRepository;
import nts.uk.ctx.at.record.dom.dailyperformanceformat.primitivevalue.BusinessTypeCode;
import nts.uk.ctx.at.record.dom.divergence.time.DivergenceTime;
import nts.uk.ctx.at.record.dom.divergence.time.DivergenceTimeRepository;
import nts.uk.ctx.at.record.dom.divergence.time.history.CompanyDivergenceReferenceTimeHistoryRepository;
import nts.uk.ctx.at.record.dom.divergence.time.history.CompanyDivergenceReferenceTimeRepository;
import nts.uk.ctx.at.record.dom.divergence.time.history.DivergenceReferenceTime;
import nts.uk.ctx.at.record.dom.divergence.time.history.DivergenceReferenceTimeUsageUnitRepository;
import nts.uk.ctx.at.record.dom.divergence.time.history.DivergenceReferenceTimeValue;
import nts.uk.ctx.at.record.dom.divergence.time.history.WorkTypeDivergenceReferenceTimeHistoryRepository;
import nts.uk.ctx.at.record.dom.divergence.time.history.WorkTypeDivergenceReferenceTimeRepository;
import nts.uk.ctx.at.record.dom.workinformation.repository.WorkInformationRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.EmployeeDailyPerError;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.ErrorAlarmWorkRecord;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.ErrorAlarmWorkRecordRepository;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
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
	
	public static List<String> SYSTEM_FIXED_CHECK_CODE = Arrays.asList("D001", "D002", "D003", "D004", "D005", 
			"D006", "D007", "D008", "D009", "D010", "D011", "D012", "D013", "D014", "D015", "D016", "D017", "D018", "D019", "D020");
	private final String WORKTYPE_HISTORY_ITEM = "W_HIS";
	private final String COMPANY_HISTORY_ITEM = "C_HIS";
	private final String WORKTYPE_CODE = "WTC";

	public List<EmployeeDailyPerError> divergenceTimeCheckBySystemFixed(String companyId, String employeeId, GeneralDate workingDate){
		List<EmployeeDailyPerError> checkR = new ArrayList<>(); 
		attendanceTimeRepo.find(employeeId, workingDate).ifPresent(at -> {
			val divergenTime = at.getActualWorkingTimeOfDaily().getDivTime().getDivergenceTime(); 
			checkR.addAll(check(companyId, employeeId, workingDate, divergenTime));
		});
		return checkR;
	}
	
	public List<EmployeeDailyPerError> divergenceTimeCheckBySystemFixed(String companyId, String employeeId, GeneralDate workingDate,
			List<nts.uk.ctx.at.record.dom.divergencetimeofdaily.DivergenceTime> divergenTime){
		return check(companyId, employeeId, workingDate, divergenTime);
	}

	private List<EmployeeDailyPerError> check(String companyId, String employeeId, GeneralDate workingDate,
			List<nts.uk.ctx.at.record.dom.divergencetimeofdaily.DivergenceTime> divergenTime) {
		List<EmployeeDailyPerError> checkR = new ArrayList<>(); 
		boolean isCheckByWorkType = isCheckWithWorkType(companyId);

		
		List<ErrorAlarmWorkRecord> erAlConditions = getErrorAlarmCheck(companyId);
		List<Integer> divergenceCheckNos = erAlConditions.stream()
				.map(c -> getNumberFromString(c.getCode().v()) / 2).distinct().collect(Collectors.toList());

		val divergenceTimeErAls = getDivergenceTimeErAl(companyId, divergenceCheckNos);
		
		val historyR = getHistory(isCheckByWorkType, employeeId, workingDate, companyId); 
		boolean isWHis = historyR.get(WORKTYPE_HISTORY_ITEM) != null;
		val historyItem = (DateHistoryItem) (isWHis ? historyR.get(WORKTYPE_HISTORY_ITEM) : historyR.get(COMPANY_HISTORY_ITEM));
		val bsCode = isWHis ? (BusinessTypeCode) historyR.get(WORKTYPE_CODE) : null; 
		erAlConditions.stream().forEach(erAl -> {
			int numberIn = getNumberFromString(erAl.getCode().v());
			boolean isAlarm = numberIn % 2 == 0;
			val divergenceTimeErAl = divergenceTimeErAls.get(numberIn / 2);
			if(divergenceTimeErAl.isDivergenceTimeUse()){
				divergenTime.stream().forEach(dt -> {
					boolean valid = evaluateDivergenceTime(numberIn / 2, dt.getDivTimeAfterDeduction(), 
							isAlarm, isWHis, historyItem, bsCode);
					if(!valid){
						checkR.add(new EmployeeDailyPerError(companyId, employeeId, workingDate, 
								erAl.getCode(), Arrays.asList(erAl.getErrorDisplayItem().intValue()), 
								erAl.getCancelableAtr() ? 1 : 0));
					}
				});
			}
		});
		return checkR;
	}
	
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
	private boolean evaluateDivergenceTime(int divNo, AttendanceTime afterdeDuctionTime, 
			boolean isAlarm, boolean isCheckByWorkType, DateHistoryItem history, BusinessTypeCode bsCode){
		if(history == null){
			return true;
		}
		if(isCheckByWorkType){
			return evaluateByWorkType(divNo, history, afterdeDuctionTime, isAlarm, bsCode);
		} else {
			return evaluateByCompany(divNo, afterdeDuctionTime, isAlarm, history);
		}
	}

	/** 勤務種別ごとの乖離基準時間でチェックする */
	public boolean evaluateByWorkType(int divNo, DateHistoryItem history, AttendanceTime targetValue, 
			boolean isAlarm, BusinessTypeCode bsCode){
		val divTimeBaseByWT = wtDivRefTime.findByKey(history.identifier(), bsCode, divNo).orElse(null);
		if(divTimeBaseByWT == null || !divTimeBaseByWT.getDivergenceReferenceTimeValue().isPresent()){
			return true;
		}
		return evaluate(targetValue, isAlarm, divTimeBaseByWT.getNotUseAtr() == NotUseAtr.USE, 
				divTimeBaseByWT.getDivergenceReferenceTimeValue().get());
	}

	/** 会社の履歴項目でチェックする */
	public boolean evaluateByCompany(int divNo,  AttendanceTime targetValue, boolean isAlarm, DateHistoryItem history){
		if(history == null){
			return true;
		}
		val divTimeBaseByCom = comDivRefTime.findByKey(history.identifier(), divNo).orElse(null);
		if(divTimeBaseByCom == null || !divTimeBaseByCom.getDivergenceReferenceTimeValue().isPresent()){
			return true;
		}
		return evaluate(targetValue, isAlarm, divTimeBaseByCom.getNotUseAtr() == NotUseAtr.USE,
				divTimeBaseByCom.getDivergenceReferenceTimeValue().get());
	}

	/** 乖離時間を判定する */
	public boolean evaluate(AttendanceTime targetValue, boolean isAlarm, boolean isUse, DivergenceReferenceTimeValue standard){
		if(!isUse || standard == null){
			return true;
		}
		DivergenceReferenceTime sdTime = isAlarm ? standard.getAlarmTime().orElse(null) : standard.getErrorTime().orElse(null);
		if(sdTime != null){
			return targetValue.lessThan(sdTime.valueAsMinutes());
		}
		return true;
	}
	
	private int getNumberFromString(String code){
		String number = code.replaceAll(code.replaceAll("[0-9]+$", ""), "");
		if(number.isEmpty()){ throw new RuntimeException("勤務実績のエラーアラームのコードのフォーマットが正しくない：　" + code); }
		return Integer.parseInt(number);
	}
}
