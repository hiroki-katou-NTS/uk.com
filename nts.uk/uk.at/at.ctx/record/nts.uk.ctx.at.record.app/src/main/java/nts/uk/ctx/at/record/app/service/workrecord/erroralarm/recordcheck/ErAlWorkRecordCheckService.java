package nts.uk.ctx.at.record.app.service.workrecord.erroralarm.recordcheck;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.auth.dom.employmentrole.EmployeeReferenceRange;
import nts.uk.ctx.at.record.app.find.dailyperform.DailyRecordDto;
import nts.uk.ctx.at.record.app.find.dailyperform.DailyRecordWorkFinder;
import nts.uk.ctx.at.record.dom.adapter.query.employee.RegulationInfoEmployeeQuery;
import nts.uk.ctx.at.record.dom.adapter.query.employee.RegulationInfoEmployeeQueryAdapter;
import nts.uk.ctx.at.record.dom.adapter.query.employee.RegulationInfoEmployeeQueryR;
import nts.uk.ctx.at.record.dom.workinformation.WorkInfoOfDailyPerformance;
import nts.uk.ctx.at.record.dom.workinformation.repository.WorkInformationRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.EmployeeDailyPerError;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.ErrorAlarmWorkRecordRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.algorithm.ConditionAlarmError;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.AlCheckTargetCondition;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.ErrorAlarmCondition;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.otkcustomize.ContinuousHolCheckSet;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.otkcustomize.repo.ContinuousHolCheckSetRepo;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.primitivevalue.ErrorAlarmWorkRecordCode;
import nts.uk.ctx.at.shared.dom.attendance.util.AttendanceItemUtil;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ItemValue;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ValueType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

@Stateless
public class ErAlWorkRecordCheckService {

	@Inject
	private RegulationInfoEmployeeQueryAdapter employeeSearch;

	@Inject
	private DailyRecordWorkFinder fullFinder;

	@Inject
	private ErrorAlarmWorkRecordRepository errorRecordRepo;

	@Inject
	private ContinuousHolCheckSetRepo checkSetting;

	@Inject
	private WorkInformationRepository workInfo;

	@Inject
	private ConditionAlarmError conditionAlarmError;

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
		return checkConditions.stream().collect(
				Collectors.toMap(c -> c.getErrorAlarmCheckID(), c -> filterEmployees(workingDate, employeeIds, c)));
	}

	public Map<String, Boolean> check(GeneralDate workingDate, Collection<String> employeeIds,
			ErrorAlarmCondition checkCondition) {
		List<String> filted = this.filterEmployees(workingDate, employeeIds, checkCondition).stream()
				.map(e -> e.getEmployeeId()).collect(Collectors.toList());

		return checkWithCondition(workingDate, checkCondition, filted, null);
	}

	private Map<String, Boolean> checkWithCondition(GeneralDate workingDate, ErrorAlarmCondition checkCondition,
			List<String> filted, List<DailyRecordDto> record) {
		if (filted.isEmpty()) {
			return toEmptyResultMap();
		}

		/** 社員に一致する日別実績を取得する */
		if (record == null || record.isEmpty()) {
			record = fullFinder.find(filted, new DatePeriod(workingDate, workingDate));
		}

		if (record.isEmpty()) {
			return toEmptyResultMap();
		}

		return record.stream().collect(Collectors.toMap(c -> c.employeeId(), c -> {
			return checkErrorAlarmCondition(c, checkCondition);
		}));
	}

	public List<EmployeeDailyPerError> checkWith(String companyId, GeneralDate workingDate,
			Collection<String> employeeIds, List<DailyRecordDto> record) {
		List<Map<String, Object>> lstErrorAlarm = conditionAlarmError.getErAlConditonByComID(companyId);
		if (!lstErrorAlarm.isEmpty()) {
			List<RegulationInfoEmployeeQueryR> employees = this.employeeSearch.search(createFilterAllQuery(workingDate));
			lstErrorAlarm.stream().map(erAl -> {
				ErrorAlarmCondition condition = (ErrorAlarmCondition) erAl.get("ErrorAlarmCondition");
				AlCheckTargetCondition filterC = condition.getCheckTargetCondtion();
				List<String> filtedE = employees.stream().filter(em -> {
					if (!employeeIds.contains(em.getEmployeeId())) {
						return false;
					}
					return isUseAndContains(filterC.getFilterByBusinessType(), filterC.getLstBusinessTypeCode(),
							em.getBussinessTypeCode())
							&& isUseAndContains(filterC.getFilterByClassification(), filterC.getLstClassificationCode(),
									em.getClassificationCode())
							&& isUseAndContains(filterC.getFilterByEmployment(), filterC.getLstEmploymentCode(),
									em.getEmploymentCode())
							&& isUseAndContains(filterC.getFilterByJobTitle(), filterC.getLstJobTitleId(),
									em.getJobTitle());
				}).map(em -> em.getEmployeeId()).collect(Collectors.toList());
				Map<String,Boolean> checkResult = checkWithCondition(workingDate, condition, filtedE, record);
				
				return filtedE.stream().filter(e -> isTrue(checkResult.get(e))).map(e -> {
					return new EmployeeDailyPerError(companyId, e, workingDate,
							new ErrorAlarmWorkRecordCode((String) erAl.get("Code")),
							Arrays.asList((int) erAl.get("ErrorDisplayItem")));
				}).collect(Collectors.toList());
			}).flatMap(List::stream).collect(Collectors.toList());
		}
		return new ArrayList<>();
	}

	private <T> boolean isUseAndContains(Boolean isUse, List<T> source, String target) {
		return isTrue(isUse) && source.contains(target);
	}

	private boolean isTrue(Boolean flag) {
		return flag == null ? false : flag;
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

	/** 大塚用連続休暇チェック */
	public Map<GeneralDate, Integer> checkContinuousHolidays(String employeeId, DatePeriod range) {
		Optional<ContinuousHolCheckSet> settingOp = checkSetting.find(AppContexts.user().companyId());
		Map<GeneralDate, Integer> result = new HashMap<>();
		settingOp.ifPresent(setting -> {
			if (setting.isUseAtr()) {
				processCheckContinuous(range.start(), range, result, setting, employeeId, null, 0, true);
			}
		});

		return result;
	}

	private void processCheckContinuous(GeneralDate endMark, DatePeriod range, Map<GeneralDate, Integer> result,
			ContinuousHolCheckSet setting, String employeeId, GeneralDate markDate, int count,
			boolean markPreviousDate) {
		boolean finishing = false;
		List<WorkInfoOfDailyPerformance> workInfos = workInfo.findByPeriodOrderByYmdDesc(employeeId, range);

		if (workInfos.isEmpty()) {
			return;
		}

		for (WorkInfoOfDailyPerformance info : workInfos) {
			WorkTypeCode currentWTC = info.getRecordWorkInformation().getWorkTypeCode();
			if (setting.getTargetWorkType().contains(currentWTC)) {
				if (markPreviousDate) {
					markDate = info.getYmd();
					markPreviousDate = false;
				}
				count++;
			} else if (!setting.getIgnoreWorkType().contains(currentWTC)) {
				if (count >= setting.getMaxContinuousDays()) {
					result.put(markDate, count);
				}
				if (endMark.afterOrEquals(info.getYmd())) {
					finishing = true;
					break;
				}
				markPreviousDate = true;
				count = 0;
			}
		}
		if (finishing) {
			return;
		}

		DatePeriod perviousRange = new DatePeriod(range.start().addDays(-16), range.start().addDays(-1));
		processCheckContinuous(endMark, perviousRange, result, setting, employeeId, markDate, count, markPreviousDate);
	}

	private boolean checkErrorAlarmCondition(DailyRecordDto record, ErrorAlarmCondition condition) {
		WorkInfoOfDailyPerformance workInfo = record.getWorkInfo().toDomain(record.employeeId(), record.getDate());
		return condition.checkWith(workInfo, item -> {
			if (item.isEmpty()) {
				return item;
			}
			return AttendanceItemUtil.toItemValues(record, item).stream().map(iv -> getValue(iv))
					.collect(Collectors.toList());
		});
	}

	private Integer getValue(ItemValue value) {
		if (value.value() == null) {
			return 0;
		}
		return value.getValueType() == ValueType.DECIMAL ? ((BigDecimal) value.value()).intValue()
				: (Integer) value.value();
	}

	private <T> Map<String, T> toEmptyResultMap() {
		return new HashMap<>();
	}

	private RegulationInfoEmployeeQuery createQueryToFilterEmployees(GeneralDate workingDate,
			AlCheckTargetCondition checkCondition) {
		if(checkCondition == null){
			return createFilterAllQuery(workingDate);
		}
		RegulationInfoEmployeeQuery query = commonQuery(workingDate);
		query.setFilterByEmployment(checkCondition.getFilterByEmployment());
		query.setEmploymentCodes(checkCondition.getLstEmploymentCode().stream().map(c -> c.v()).collect(Collectors.toList()));
		query.setFilterByClassification(checkCondition.getFilterByClassification());
		query.setClassificationCodes(checkCondition.getLstClassificationCode().stream().map(c -> c.v()).collect(Collectors.toList()));
		query.setFilterByJobTitle(checkCondition.getFilterByJobTitle());
		query.setJobTitleCodes(checkCondition.getLstJobTitleId());
		query.setFilterByWorktype(checkCondition.getFilterByBusinessType());
		query.setWorktypeCodes(checkCondition.getLstBusinessTypeCode().stream().map(c -> c.v()).collect(Collectors.toList()));
		return query;
	}

	private RegulationInfoEmployeeQuery createFilterAllQuery(GeneralDate workingDate) {
		RegulationInfoEmployeeQuery query = commonQuery(workingDate);
		query.setFilterByEmployment(false);
		query.setEmploymentCodes(new ArrayList<>());
		query.setFilterByClassification(false);
		query.setClassificationCodes(new ArrayList<>());
		query.setFilterByJobTitle(false);
		query.setJobTitleCodes(new ArrayList<>());
		query.setFilterByWorktype(false);
		query.setWorktypeCodes(new ArrayList<>());
		return query;
	}
	
	private RegulationInfoEmployeeQuery commonQuery(GeneralDate workingDate){
		RegulationInfoEmployeeQuery query = new RegulationInfoEmployeeQuery();
		query.setBaseDate(workingDate);
		query.setReferenceRange(EmployeeReferenceRange.ALL_EMPLOYEE.value);
		query.setFilterByDepartment(false);
		// query.setDepartmentCodes(departmentCodes);
		query.setFilterByWorkplace(false);
		// query.setWorkplaceCodes(workplaceCodes);
		query.setPeriodStart(workingDate.toString());
		query.setPeriodEnd(workingDate.toString());
		query.setIncludeIncumbents(true);
		query.setIncludeWorkersOnLeave(true);
		query.setIncludeOccupancy(true);
		query.setIncludeAreOnLoan(true);
		query.setIncludeGoingOnLoan(false);
		query.setIncludeRetirees(false);
		// query.setRetireStart(retireStart);
		// query.setRetireEnd(retireEnd);
		return query;
	}
}
