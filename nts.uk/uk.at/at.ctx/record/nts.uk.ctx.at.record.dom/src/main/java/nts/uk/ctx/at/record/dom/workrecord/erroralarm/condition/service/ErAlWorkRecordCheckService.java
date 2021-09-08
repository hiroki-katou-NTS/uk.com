package nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import lombok.val;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.auth.dom.employmentrole.EmployeeReferenceRange;
import nts.uk.ctx.at.record.dom.adapter.query.employee.EmployeeSearchInfoDto;
import nts.uk.ctx.at.record.dom.adapter.query.employee.RegulationInfoEmployeeQuery;
import nts.uk.ctx.at.record.dom.adapter.query.employee.RegulationInfoEmployeeQueryAdapter;
import nts.uk.ctx.at.record.dom.adapter.query.employee.RegulationInfoEmployeeQueryR;
import nts.uk.ctx.at.record.dom.workinformation.WorkInfoOfDailyPerformance;
import nts.uk.ctx.at.record.dom.workinformation.repository.WorkInformationRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.ErrorAlarmConditionRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.ErrorAlarmWorkRecordRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.AlCheckTargetCondition;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.ErrorAlarmCondition;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.ResultCheckWith;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.WorkRecordExtraConRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.worktype.PlanActualWorkType;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.worktype.SingleWorkType;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.otkcustomize.ContinuousHolCheckSet;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.otkcustomize.repo.ContinuousHolCheckSetRepo;
import nts.uk.ctx.at.shared.dom.dailyattdcal.converter.DailyRecordShareFinder;
import nts.uk.ctx.at.shared.dom.scherec.attendanceitem.converter.service.AttendanceItemConvertFactory;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.affiliationinfor.AffiliationInforOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class ErAlWorkRecordCheckService {
	
	public final static String CONTINUOUS_CHECK_CODE = "OTK1";

	@Inject
	private RegulationInfoEmployeeQueryAdapter employeeSearch;

	@Inject
	private DailyRecordShareFinder fullFinder;

	@Inject
	private ErrorAlarmWorkRecordRepository errorRecordRepo;

	@Inject
	private ContinuousHolCheckSetRepo checkSetting;

	@Inject
	private WorkInformationRepository workInfo;
	
	@Inject
	private ErrorAlarmConditionRepository errorAlarmConditionRepo;
	
	@Inject
	private WorkRecordExtraConRepository workRecordExtraRepo;
	
	@Inject
	private AttendanceItemConvertFactory convertFactory;

	public List<RegulationInfoEmployeeQueryR> filterEmployees(GeneralDate workingDate, Collection<String> employeeIds,
			ErrorAlarmCondition checkCondition) {
		if (checkCondition == null) {
			return new ArrayList<>();
		}
		return filterEmployees(workingDate, employeeIds, checkCondition.getCheckTargetCondtion());
	}

	public List<RegulationInfoEmployeeQueryR> filterEmployees(GeneralDate workingDate, Collection<String> employeeIds,
			String EACheckID) {
		ErrorAlarmCondition checkCondition = errorRecordRepo.findConditionByErrorAlamCheckId(EACheckID).orElse(null);
		return filterEmployees(workingDate, employeeIds, checkCondition);
	}
	
	public List<RegulationInfoEmployeeQueryR> filterEmployees(GeneralDate workingDate, Collection<String> employeeIds,
			AlCheckTargetCondition checkCondition) {
		if (checkCondition == null) {
			return new ArrayList<>();
		}
		return this.employeeSearch.search(createQueryToFilterEmployees(workingDate, checkCondition)).stream()
				.filter(e -> employeeIds.contains(e.getEmployeeId())).collect(Collectors.toList());
	}

	public Map<String, List<RegulationInfoEmployeeQueryR>> filterEmployees(GeneralDate workingDate,
			Collection<String> employeeIds, List<String> EACheckID) {
		List<ErrorAlarmCondition> checkConditions = errorRecordRepo.findConditionByListErrorAlamCheckId(EACheckID);

		if (checkConditions == null) {
			return new HashMap<>();
		}
		return checkConditions.stream().collect(Collectors.toMap(c -> c.getErrorAlarmCheckID(), 
				c -> filterEmployees(workingDate, employeeIds, c)));
	}

	public Map<String, Boolean> check(GeneralDate workingDate, Collection<String> employeeIds,
			ErrorAlarmCondition checkCondition) {
		return check(workingDate, employeeIds, checkCondition, null);
	}
	
	public Map<String, Boolean> check(GeneralDate workingDate, Collection<String> employeeIds,
			ErrorAlarmCondition checkCondition, List<IntegrationOfDaily> record) {
//		List<String> filted = this.filterEmployees(workingDate, employeeIds, checkCondition).stream()
//				.map(e -> e.getEmployeeId()).collect(Collectors.toList());

		if (employeeIds.isEmpty()) {
			return toEmptyResultMap();
		}
		/** 社員に一致する日別実績を取得する */
		if(record == null || record.isEmpty()){
			record = fullFinder.findByListEmployeeId(new ArrayList<>(employeeIds), new DatePeriod(workingDate, workingDate));	
		}

		if (record.isEmpty()) {
			return toEmptyResultMap();
		}

		return record.stream().collect(Collectors.toMap(c -> c.getEmployeeId(), c -> {
			return checkErrorAlarmCondition(c, checkCondition);
		}));
	}

	public Map<String, Boolean> check(GeneralDate workingDate, Collection<String> employeeIds, String EACheckID) {
		ErrorAlarmCondition checkCondition = errorRecordRepo.findConditionByErrorAlamCheckId(EACheckID).orElse(null);

		if (checkCondition != null) {
			return check(workingDate, employeeIds, checkCondition);
		}

		return toEmptyResultMap();
	}

	public Map<String, Map<String, Boolean>> check(GeneralDate workingDate, Collection<String> employeeIds,
			List<String> EACheckID) {
		List<ErrorAlarmCondition> checkConditions = errorRecordRepo.findConditionByListErrorAlamCheckId(EACheckID);

		if (checkConditions != null) {
			return checkConditions.stream()
					.collect(Collectors.toMap(c -> c.getErrorAlarmCheckID(), c -> check(workingDate, employeeIds, c)));
		}

		return toEmptyResultMap();
	}

	public List<EmployeeSearchInfoDto> filterAllEmployees(DatePeriod workingDate, Collection<String> employeeIds) {
		return this.employeeSearch.search(employeeIds, workingDate);
	}
	
//	public List<String> filterAllEmployees(GeneralDate workingDate, Collection<String> employeeIds, ErrorAlarmCondition condition) {
//		return filterEmployees(this.employeeSearch.search(employeeIds, new DatePeriod(workingDate, workingDate)),
//				workingDate, condition.getCheckTargetCondtion());
//	}
	
	public List<ErrorRecord> checkWithRecord(GeneralDate workingDate, Collection<String> employeeIds,
			List<String> EACheckID, List<IntegrationOfDaily> record) {
		return checkWithRecord(new DatePeriod(workingDate, workingDate), employeeIds, EACheckID, record) ;
	}
	/**
	 * 勤務種類でフィルタする
	 * @param workingDate
	 * @param employeeIds
	 * @param EACheckID
	 * @return
	 */
	
	public List<ErrorRecord> checkWithRecord(DatePeriod workingDate, Collection<String> employeeIds,
			List<String> EACheckID, List<IntegrationOfDaily> record) {
		//日別実績
		if(record.isEmpty()) {
			record = fullFinder.findByListEmployeeId(new ArrayList<>(employeeIds), workingDate);
		}
			
		if(record.isEmpty()){
			return toEmptyResultList();
		}
		//勤務実績のエラーアラームチェック
		List<ErrorAlarmCondition> checkConditions = errorRecordRepo.findConditionByListErrorAlamCheckId(EACheckID);
		
		if(checkConditions.isEmpty()){
			EACheckID = workRecordExtraRepo.getAllWorkRecordExtraConByListID(EACheckID).stream()
					.filter(c -> c.isUseAtr()).map(c -> c.getErrorAlarmCheckID()).collect(Collectors.toList());
			if(EACheckID.isEmpty()){
				return toEmptyResultList();
			}
			//勤務実績のエラーアラームチェック
			checkConditions = errorAlarmConditionRepo.findConditionByListErrorAlamCheckId(EACheckID);
			return checkWithPeriod(workingDate,
					employeeIds,
					record,
					checkConditions,
					(currentRecord, condition) -> {
				//勤務種類でフィルタする
				if(condition.getWorkTypeCondition() != null) {
					//勤務種類（単一）
					if(condition.getWorkTypeCondition() instanceof SingleWorkType) {
						SingleWorkType wtConCheck = (SingleWorkType) condition.getWorkTypeCondition();
						switch (wtConCheck.getComparePlanAndActual().value) {
						case 0://** 全て *//*
							break;
						case 1://** 選択 *//*
							currentRecord.removeIf(cr -> !wtConCheck.getTargetWorkType()
									.getLstWorkType()
									.contains(new WorkTypeCode(cr.getWorkInformation().getRecordInfo().getWorkTypeCode().v())));
							break;
						case 2://** 選択以外 *//*
							currentRecord.removeIf(cr -> wtConCheck.getTargetWorkType()
									.getLstWorkType()
									.contains(new WorkTypeCode(cr.getWorkInformation().getRecordInfo().getWorkTypeCode().v())));
							break;
						}
					} else {
						//勤務種類（予実）
						PlanActualWorkType wtConCheck = (PlanActualWorkType) condition.getWorkTypeCondition();
						switch (wtConCheck.getComparePlanAndActual().value) {
						case 0://** 全て *//*
							break;
						case 1://** 選択 *//*
							currentRecord.removeIf(cr -> !wtConCheck.getWorkTypePlan()
									.getLstWorkType()
									.contains(new WorkTypeCode(cr.getWorkInformation().getRecordInfo().getWorkTypeCode().v())));
							break;
						case 2://** 選択以外 *//*
							currentRecord.removeIf(cr -> wtConCheck.getWorkTypePlan()
									.getLstWorkType()
									.contains(new WorkTypeCode(cr.getWorkInformation().getRecordInfo().getWorkTypeCode().v())));
							break;
						}
					}
					ErrorAlarmCondition condition2 = ErrorAlarmCondition.init();
					condition2.referenceTo(condition);
					condition2.createWorkTypeCondition(false, 1);
					return condition2;
				}
				return condition;
			});
		} else {
			return checkWithPeriod(workingDate, employeeIds, record, checkConditions, (currentRecord, condition) -> condition);
		}
	}

	private List<ErrorRecord> checkWithPeriod(DatePeriod workingDate, Collection<String> employeeIds,
			List<IntegrationOfDaily> record, List<ErrorAlarmCondition> checkConditions, 
			BiFunction<List<IntegrationOfDaily>, ErrorAlarmCondition, ErrorAlarmCondition> beforeCheck) {
		return checkConditions.stream().map(c -> {
			List<IntegrationOfDaily> cdRecords = new ArrayList<>(record);
			ErrorAlarmCondition checkCondition = beforeCheck.apply(cdRecords, c);
			List<ErrorRecord> errors = new ArrayList<>();
			workingDate.datesBetween().stream().forEach(current -> {
				List<IntegrationOfDaily> currentRecords = cdRecords.stream().filter(r -> r.getYmd().equals(current)).collect(Collectors.toList());
				if (!currentRecords.isEmpty()) {
					errors.addAll(finalCheck(current, checkCondition, currentRecords, employeeIds));
				}
			});
			return errors;
		}).flatMap(List::stream).collect(Collectors.toList());
	}
	
	
	
	public List<ErrorRecord> checkWithRecord(GeneralDate workingDate, Collection<String> employeeIds,
			ErrorAlarmCondition checkCondition, List<IntegrationOfDaily> record) {
//		List<String> filted = this.filterAllEmployees(workingDate, employeeIds, checkCondition);

		if (employeeIds.isEmpty()) {
			return toEmptyResultList();
		}
		return finalCheck(workingDate, checkCondition, record, employeeIds);
	}
	
	private boolean canCheck(AffiliationInforOfDailyAttd affiliation, AlCheckTargetCondition checkCondition){
		if(isTrue(checkCondition.getFilterByBusinessType())){
			if(affiliation == null || !checkCondition.getLstBusinessTypeCode()
					.contains(!affiliation.getBusinessTypeCode().isPresent() ? null 
							: affiliation.getBusinessTypeCode().get())){
				return false;
			}
		}
		if(isTrue(checkCondition.getFilterByEmployment())){
			if(!checkCondition.getLstEmploymentCode().contains(affiliation.getEmploymentCode())){
				return false;
			}
		}
		if(isTrue(checkCondition.getFilterByClassification())){
			if(!checkCondition.getLstClassificationCode().contains(affiliation.getClsCode())){
				return false;
			}
		}
		if(isTrue(checkCondition.getFilterByJobTitle())){
			if(!checkCondition.getLstJobTitleId().contains(affiliation.getJobTitleID())){
				return false;
			}
		}
		return true;
	}

	private boolean isTrue(Boolean checkCondition) {
		return checkCondition != null && checkCondition;
	}

	private List<ErrorRecord> finalCheck(GeneralDate workingDate, ErrorAlarmCondition checkCondition,
			List<IntegrationOfDaily> record, Collection<String> filted) {
		/** 社員に一致する日別実績を取得する */
		List<IntegrationOfDaily> cRecord = record.stream().filter(c -> filted.contains(c.getEmployeeId())).collect(Collectors.toList());

		if (cRecord.isEmpty()) {
			return toEmptyResultList();
		}

		return cRecord.stream().map(c -> {
			if(checkCondition.getCheckTargetCondtion() == null){
				return null;
			}
			if(!canCheck(c.getAffiliationInfor(), checkCondition.getCheckTargetCondtion())){
				return null;
			}
			ResultCheckWith result = checkErrorAlarmConditionAndResult(c, checkCondition);
			if(result.isCheck()){
				ErrorRecord errorRecord = new ErrorRecord(workingDate, c.getEmployeeId(), checkCondition.getErrorAlarmCheckID()); 
				errorRecord.setCheckedValue(result.getResult());
				return errorRecord;
			}
			return null;
		}).filter(c -> c != null).collect(Collectors.toList());
	}

	/** 大塚用連続休暇チェック */
	public ContinuousHolidayCheckResult checkContinuousHolidays(String employeeId, DatePeriod range) {

		return checkContinuousHolidays(employeeId, range, new ArrayList<>());
	}
	
	/** 大塚用連続休暇チェック */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public ContinuousHolidayCheckResult checkContinuousHolidays(String employeeId, DatePeriod range, List<WorkInfoOfDailyPerformance> workInfos) {
		ContinuousHolidayCheckResult r = new ContinuousHolidayCheckResult();
		
		checkSetting.findSpecial(AppContexts.user().companyId()).ifPresent(setting -> {
			if(setting.isUseAtr() && setting.getMaxContinuousDays().greaterThan(0) && !setting.getTargetWorkType().isEmpty()){
				Map<GeneralDate, Integer> result = new HashMap<>();
				
				processCheckContinuous(range.start(), range, result, setting, employeeId, null, 0, true, new HashSet<>(workInfos));
				
				r.message(setting.getDisplayMessege().v());
				
				r.setErrorDate(result);
			}
		});

		return r;
	}

	private void processCheckContinuous(GeneralDate endMark, DatePeriod range, Map<GeneralDate, Integer> result,
			ContinuousHolCheckSet setting, String employeeId, GeneralDate markDate, int count,
			boolean markPreviousDate, Set<WorkInfoOfDailyPerformance> workInfos) {
		boolean finishing = false, nextCount = false;
		List<WorkInfoOfDailyPerformance> subWorkInfos = getWorkInfoInRange(range, employeeId, workInfos);

		if (subWorkInfos.isEmpty()) {
			if (count >= setting.getMaxContinuousDays().v()) {
				result.put(markDate, count);
			}
			return;	
		}
		
		for (int i = 0; i < subWorkInfos.size(); i++){
			WorkInfoOfDailyPerformance info = subWorkInfos.get(i);
			WorkTypeCode currentWTC = info.getWorkInformation().getRecordInfo().getWorkTypeCode();
			
			if(setting.getTargetWorkType().contains(currentWTC) || !markPreviousDate) {
				
				if (markPreviousDate) {
					markDate = info.getYmd();
					markPreviousDate = false;
				}
				
				if(setting.getTargetWorkType().contains(currentWTC)){
					count++;	
				}
				
				for(int j = i + 1; j < subWorkInfos.size(); j++){
					WorkInfoOfDailyPerformance info2 = subWorkInfos.get(j);
					WorkTypeCode currentWTC2 = info2.getWorkInformation().getRecordInfo().getWorkTypeCode();

					if(!setting.getTargetWorkType().contains(currentWTC2) && !setting.getIgnoreWorkType().contains(currentWTC2)){
						nextCount = true;
						break;
					}
					
					if(setting.getTargetWorkType().contains(currentWTC2)){
						count++;
					}
					
					i++;
					
					if(info2.getYmd().before(endMark.addDays(-7))){
						finishing = true;
						break;
					}
				}
			}

			if(count >= setting.getMaxContinuousDays().v()){
				result.put(markDate, count);
			}
			
			if (finishing || (count <= 0 && info.getYmd().beforeOrEquals(endMark))) {
				return;
			}
			
			if(nextCount) {
				markPreviousDate = true;
				count = 0;
				nextCount = false;
			}
			
		}
		
//		for (WorkInfoOfDailyPerformance info : subWorkInfos) {
//			WorkTypeCode currentWTC = info.getRecordInfo().getWorkTypeCode();
//			if (setting.getTargetWorkType().contains(currentWTC)) {
//				if (markPreviousDate) {
//					markDate = info.getYmd();
//					markPreviousDate = false;
//				}
//				count++;
//			} else if (setting.getIgnoreWorkType().contains(currentWTC)) {
//				if (endMark.afterOrEquals(info.getYmd())) {
//					if (count >= setting.getMaxContinuousDays().v()) {
//						result.put(markDate, count);
//					}
//					finishing = true;
//					break;
//				}
//			} else {
//				if (count >= setting.getMaxContinuousDays().v()) {
//					result.put(markDate, count);
//				}
//				markPreviousDate = true;
//				count = 0;
//				
//				if (count <= 0 && endMark.afterOrEquals(info.getYmd())) {
//					finishing = true;
//					break;
//				}
//			}
//		}

		DatePeriod perviousRange = new DatePeriod(range.start().addDays(-7), range.start().addDays(-1));
		
		workInfos.removeAll(subWorkInfos);
		
		processCheckContinuous(endMark, perviousRange, result, setting, employeeId, markDate, count, markPreviousDate, workInfos);
	}

	private List<WorkInfoOfDailyPerformance> getWorkInfoInRange(DatePeriod range, String employeeId, Set<WorkInfoOfDailyPerformance> workInfos) {
		List<GeneralDate> dateInRange = range.datesBetween();
		
		List<WorkInfoOfDailyPerformance> subWorkInfos = workInfos.stream()
																.filter(c -> dateInRange.contains(c.getYmd()) && c.getEmployeeId().equals(employeeId))
																.collect(Collectors.toList());
		
		if(dateInRange.size() > subWorkInfos.size()){
			dateInRange.removeAll(subWorkInfos.stream().map(c -> c.getYmd()).collect(Collectors.toList()));
			subWorkInfos.addAll(workInfo.findByListDate(employeeId, dateInRange));
		}
		
		return subWorkInfos.stream().sorted((s1, s2) -> s2.getYmd().compareTo(s1.getYmd())).collect(Collectors.toList());
	}

	private boolean checkErrorAlarmCondition(IntegrationOfDaily record, ErrorAlarmCondition condition) {
		if(condition.getCheckTargetCondtion() == null){
			return false;
		}
		if(!canCheck(record.getAffiliationInfor(), condition.getCheckTargetCondtion())){
			return false;
		}
		
		val converter = convertFactory.createDailyConverter();
		
		WorkInfoOfDailyPerformance workInfo = new WorkInfoOfDailyPerformance(record.getEmployeeId(),
				record.getYmd(),
				record.getWorkInformation());
		
		return condition.checkWith(workInfo, record.getSnapshot().map(c -> c), item -> {
			if (item.isEmpty()) {
				return new ArrayList<>();
			}
			
			converter.setData(record);
			
			return converter.convert(item).stream().map(iv -> getValue(iv))
					.collect(Collectors.toList());
		});
	}
	private ResultCheckWith checkErrorAlarmConditionAndResult(IntegrationOfDaily record, ErrorAlarmCondition condition) {
		if(condition.getCheckTargetCondtion() == null){
			return new ResultCheckWith(false,null);
		}
		if(!canCheck(record.getAffiliationInfor(), condition.getCheckTargetCondtion())){
			return new ResultCheckWith(false,null);
		}
		val converter = convertFactory.createDailyConverter();
		
		WorkInfoOfDailyPerformance workInfo =new WorkInfoOfDailyPerformance(record.getEmployeeId(), record.getYmd(), record.getWorkInformation());
		
		List<Double> listData = condition.getAtdItemCondition().getGroup1().getLstErAlAtdItemCon().stream().map(c->c.sumCheckTarget(item ->{
			if (item.isEmpty()) {
				return new ArrayList<>();
			}
			converter.setData(record);
			
			return converter.convert(item).stream().map(iv -> getValue(iv))
					.collect(Collectors.toList());
		})).filter(v -> v != null).collect(Collectors.toList());
		
		return new ResultCheckWith(condition.checkWith(workInfo, record.getSnapshot(), item -> {
			if (item.isEmpty()) {
				return new ArrayList<>();
			}

			converter.setData(record);
			
			return converter.convert(item).stream().map(iv -> getValue(iv))
					.collect(Collectors.toList());
		}), listData.isEmpty() ? null : listData.get(0).toString());
	}
	

	private Double getValue(ItemValue value) {
		if (value.value() == null) {
			return null;
		}
		return value.type().isDouble() ? (Double) value.value()
												: Double.valueOf((Integer) value.value());
	}

	private <T> Map<String, T> toEmptyResultMap() {
		return new HashMap<>();
	}
	
	private <T> List<T> toEmptyResultList() {
		return new ArrayList<>();
	}

	private RegulationInfoEmployeeQuery createQueryToFilterEmployees(GeneralDate workingDate,
			AlCheckTargetCondition checkCondition) {
		RegulationInfoEmployeeQuery query = new RegulationInfoEmployeeQuery();
		boolean isFixed = checkCondition == null;
		query.setBaseDate(workingDate);
		query.setReferenceRange(EmployeeReferenceRange.ALL_EMPLOYEE.value);
		query.setFilterByEmployment(isFixed ? false : checkCondition.getFilterByEmployment());
		query.setEmploymentCodes(isFixed ? new ArrayList<>() : checkCondition.getLstEmploymentCode().stream().map(c -> c.v())
				.collect(Collectors.toList()));
		query.setFilterByDepartment(false);
		// query.setDepartmentCodes(departmentCodes);
		query.setFilterByWorkplace(false);
		// query.setWorkplaceCodes(workplaceCodes);
		query.setFilterByClassification(isFixed ? false : checkCondition.getFilterByClassification());
		query.setClassificationCodes(isFixed ? new ArrayList<>() : checkCondition.getLstClassificationCode().stream()
				.map(c -> c.v()).collect(Collectors.toList()));
		query.setFilterByJobTitle(isFixed ? false : checkCondition.getFilterByJobTitle());
		query.setJobTitleCodes(isFixed ? new ArrayList<>() : checkCondition.getLstJobTitleId());
		query.setFilterByWorktype(isFixed ? false : checkCondition.getFilterByBusinessType());
		query.setWorktypeCodes(isFixed ? new ArrayList<>() : checkCondition.getLstBusinessTypeCode().stream().map(c -> c.v())
				.collect(Collectors.toList()));
		query.setPeriodStart(workingDate);
		query.setPeriodEnd(workingDate);
		query.setIncludeIncumbents(true);
		query.setIncludeWorkersOnLeave(true);
		query.setIncludeOccupancy(true);
		query.setIncludeAreOnLoan(true);
		query.setIncludeGoingOnLoan(false);
		query.setIncludeRetirees(false);
		query.setRetireStart(workingDate);
		query.setRetireEnd(workingDate);
		return query;
	}
	
	public class ErrorRecord {
		private GeneralDate date;
		private String employeeId;
		private String erAlId;
		private final boolean error = true;
		private String checkedValue;
		
		public ErrorRecord(GeneralDate date, String employeeId, String erAlId) {
			super();
			this.date = date;
			this.employeeId = employeeId;
			this.erAlId = erAlId;
		}

		public GeneralDate getDate() {
			return date;
		}

		public String getEmployeeId() {
			return employeeId;
		}

		public String getErAlId() {
			return erAlId;
		}

		public boolean isError() {
			return error;
		}
		
		public String getCheckedValue() {
			return checkedValue;
		}

		public void setCheckedValue(String checkedValue) {
			this.checkedValue = checkedValue;
		}
	}
}
