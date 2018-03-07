package nts.uk.ctx.at.record.app.service.workrecord.erroralarm.recordcheck;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.auth.dom.employmentrole.EmployeeReferenceRange;
import nts.uk.ctx.at.record.app.find.dailyperform.DailyRecordDto;
import nts.uk.ctx.at.record.app.find.dailyperform.DailyRecordWorkFinder;
import nts.uk.ctx.at.record.app.find.dailyperform.workinfo.dto.WorkInformationOfDailyDto;
import nts.uk.ctx.at.record.app.find.workrecord.erroralarm.ErrorAlarmWorkRecordDto;
import nts.uk.ctx.at.record.app.find.workrecord.erroralarm.condition.ErAlAtdItemConditionDto;
import nts.uk.ctx.at.record.app.find.workrecord.erroralarm.condition.WorkTimeConditionDto;
import nts.uk.ctx.at.record.app.find.workrecord.erroralarm.condition.WorkTypeConditionDto;
import nts.uk.ctx.at.record.dom.adapter.query.employee.RegulationInfoEmployeeQuery;
import nts.uk.ctx.at.record.dom.adapter.query.employee.RegulationInfoEmployeeQueryAdapter;
import nts.uk.ctx.at.record.dom.adapter.query.employee.RegulationInfoEmployeeQueryR;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.ErrorAlarmWorkRecord;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.ErrorAlarmWorkRecordRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.ErrorAlarmCondition;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.enums.ConditionAtr;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.enums.FilterByCompare;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.enums.LogicalOperator;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.enums.RangeCompareType;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.enums.SingleValueCompareType;
import nts.uk.ctx.at.shared.dom.attendance.util.AttendanceItemUtil;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ItemValue;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ValueType;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

@Stateless
public class WorkRecordCheckService {

	@Inject
	private RegulationInfoEmployeeQueryAdapter employeeSearch;

	@Inject
	private DailyRecordWorkFinder fullFinder;

	@Inject
	private ErrorAlarmWorkRecordRepository errorRecordRepo;

	public List<RegulationInfoEmployeeQueryR> filterEmployees(GeneralDate workingDate, Collection<String> employeeIds,
			ErrorAlarmCondition checkCondition) {
		return this.employeeSearch.search(createQueryToFilterEmployees(workingDate, checkCondition));
	}

	public Map<String, Boolean> check(GeneralDate workingDate, Collection<String> employeeIds,
			ErrorAlarmCondition checkCondition) {
		ErrorAlarmWorkRecord record = errorRecordRepo.findByErrorAlamCheckId(checkCondition.getErrorAlarmCheckID())
				.orElse(null);

		if (record != null) {
			return check(workingDate, employeeIds, ErrorAlarmWorkRecordDto.fromDomain(record, checkCondition));
		}

		return toEmptyResultMap();
	}

	public Map<String, Boolean> check(GeneralDate workingDate, Collection<String> employeeIds, String EACheckID) {
		ErrorAlarmCondition checkCondition = errorRecordRepo.findConditionByErrorAlamCheckId(EACheckID).orElse(null);

		if (checkCondition != null) {
			return check(workingDate, employeeIds, checkCondition);
		}

		return toEmptyResultMap();
	}

	public Map<String, Boolean> check(GeneralDate workingDate, Collection<String> employeeIds,
			ErrorAlarmWorkRecordDto checkCondition) {
		List<RegulationInfoEmployeeQueryR> searchR = this.employeeSearch
				.search(this.createQueryToFilterEmployees(workingDate, checkCondition));

		if (searchR.isEmpty()) {
			return toEmptyResultMap();
		}

		List<String> filted = searchR.stream().filter(e -> employeeIds.contains(e.getEmployeeId()))
				.map(e -> e.getEmployeeId()).collect(Collectors.toList());

		/** 社員に一致する日別実績を取得する */
		List<DailyRecordDto> record = fullFinder.find(filted, new DatePeriod(workingDate, workingDate));

		if (record.isEmpty()) {
			return toEmptyResultMap();
		}

		return record.stream().collect(Collectors.toMap(c -> c.employeeId(), c -> {
			boolean workTypeCheck = checkWithWorkType(checkCondition.getWorkTypeCondition(), c.getWorkInfo(),
					checkCondition.getOperatorBetweenPlanActual());
			if (workTypeCheck) {
				boolean workTimeCheck = checkWithWorkTime(checkCondition.getWorkTimeCondition(), c.getWorkInfo(),
						checkCondition.getOperatorBetweenPlanActual());
				if (workTimeCheck) {
					return checkWithAttendanceItem(checkCondition, c);
				}
			}
			return false;
		}));
	}

	/** 勤務種類をチェックする */
	private boolean checkWithWorkType(WorkTypeConditionDto checkCondition, WorkInformationOfDailyDto workInfo,
			int operatorBetweenPlanActual) {
		if (checkCondition.isUseAtr()) {
			/** @see FilterByCompare */
			if (checkCondition.getComparePlanAndActual() == FilterByCompare.EXTRACT_SAME.value) {
				/** 予定と実績が同じものを抽出する */
				if (!workInfo.getPlanWorkInfo().getWorkTypeCode()
						.equals(workInfo.getActualWorkInfo().getWorkTypeCode())) {
					return false;
				}
			} else if (checkCondition.getComparePlanAndActual() == FilterByCompare.EXTRACT_DIFFERENT.value) {
				/** 予定と実績が異なるものを抽出する */
				if (workInfo.getPlanWorkInfo().getWorkTypeCode()
						.equals(workInfo.getActualWorkInfo().getWorkTypeCode())) {
					return false;
				}
			}
			/** 勤務種類（予実）．予定に該当するかチェックする */
			boolean planAL = true;
			if (checkCondition.isPlanFilterAtr()) {
				planAL = checkCondition.getPlanLstWorkType().contains(workInfo.getPlanWorkInfo().getWorkTypeCode());
			}
			/** 勤務種類（予実）．実績に該当するかチェックする */
			boolean actualAL = true;
			if (checkCondition.isActualFilterAtr()) {
				actualAL = checkCondition.getActualLstWorkType()
						.contains(workInfo.getActualWorkInfo().getWorkTypeCode());
			}
			/** 演算子をチェックする @see LogicalOperator */
			return operatorBetweenPlanActual == LogicalOperator.OR.value ? planAL || actualAL : planAL && actualAL;
		}
		return false;
	}

	/** 就業時間帯をチェックする */
	private boolean checkWithWorkTime(WorkTimeConditionDto checkCondition, WorkInformationOfDailyDto workInfo,
			int operatorBetweenPlanActual) {
		if (checkCondition.isUseAtr()) {
			/** @see FilterByCompare */
			if (checkCondition.getComparePlanAndActual() == FilterByCompare.EXTRACT_SAME.value) {
				/** 予定と実績が同じものを抽出する */
				if ((workInfo.getPlanWorkInfo().getWorkTimeCode() == null
						&& workInfo.getActualWorkInfo().getWorkTimeCode() == null)
						|| (workInfo.getPlanWorkInfo().getWorkTimeCode() != null && !workInfo.getPlanWorkInfo()
								.getWorkTimeCode().equals(workInfo.getActualWorkInfo().getWorkTimeCode()))) {
					return false;
				}
			} else if (checkCondition.getComparePlanAndActual() == FilterByCompare.EXTRACT_DIFFERENT.value) {
				/** 予定と実績が異なるものを抽出する */
				if ((workInfo.getPlanWorkInfo().getWorkTimeCode() == null
						&& workInfo.getActualWorkInfo().getWorkTimeCode() == null)
						|| (workInfo.getPlanWorkInfo().getWorkTimeCode()
								.equals(workInfo.getActualWorkInfo().getWorkTimeCode()))) {
					return false;
				}
			}
			/** 勤務種類（予実）．予定に該当するかチェックする */
			boolean planAL = true;
			if (checkCondition.isPlanFilterAtr()) {
				planAL = checkCondition.getPlanLstWorkTime().contains(workInfo.getPlanWorkInfo().getWorkTimeCode());
			}
			/** 勤務種類（予実）．実績に該当するかチェックする */
			boolean actualAL = true;
			if (checkCondition.isActualFilterAtr()) {
				actualAL = checkCondition.getPlanLstWorkTime().contains(workInfo.getActualWorkInfo().getWorkTimeCode());
			}
			/** 演算子をチェックする @see LogicalOperator */
			return operatorBetweenPlanActual == LogicalOperator.OR.value ? planAL || actualAL : planAL && actualAL;
		}
		return false;
	}

	/** 勤怠項目をチェックする */
	private boolean checkWithAttendanceItem(ErrorAlarmWorkRecordDto condition, DailyRecordDto data) {
		/** グループ1をチェックする */
		boolean group1 = checkG(condition.getErAlAtdItemConditionGroup1(), condition.getOperatorGroup1(), data);

		if (condition.isGroup2UseAtr()) {
			/** グループ2をチェックする */
			boolean group2 = checkG(condition.getErAlAtdItemConditionGroup2(), condition.getOperatorGroup2(), data);

			/** 該当しているかチェックする */
			return condition.getOperatorBetweenGroups() == LogicalOperator.OR.value ? group1 || group2
					: group1 && group2;
		}

		return group1;
	}

	private boolean checkG(List<ErAlAtdItemConditionDto> group, int operatorGroup, DailyRecordDto data) {
		List<Boolean> groupResult = group.stream().filter(g -> g.isUseAtr()).map(g -> {
			Set<Integer> items = new HashSet<>();
			BigDecimal attendanceValue = BigDecimal.ZERO;
			if (g.getConditionAtr() == ConditionAtr.TIME_WITH_DAY.value) {
				items.add(g.getUncountableAtdItem());
				List<ItemValue> value = AttendanceItemUtil.toItemValues(data, items);
				if (!value.isEmpty()) {
					attendanceValue = getValue(value.get(0));
				}
			} else {
				items.addAll(g.getCountableAddAtdItems());
				items.addAll(g.getCountableSubAtdItems());
				List<ItemValue> values = AttendanceItemUtil.toItemValues(data, items).stream()
						.filter(av -> av.value() != null).collect(Collectors.toList());
				for (ItemValue av : values) {
					if (g.getCountableAddAtdItems().contains(av.itemId())) {
						attendanceValue.add(getValue(av));
					}
					if (g.getCountableSubAtdItems().contains(av.itemId())) {
						attendanceValue.subtract(getValue(av));
					}
				}
			}

			boolean compareResult = true;
			if (g.getCompareEndValue() != null) {
				if (g.getCompareOperator() == RangeCompareType.BETWEEN_RANGE_CLOSED.value) {
					compareResult = attendanceValue.compareTo(g.getCompareStartValue()) > 0
							&& attendanceValue.compareTo(g.getCompareEndValue()) < 0;
				} else if (g.getCompareOperator() == RangeCompareType.BETWEEN_RANGE_OPEN.value) {
					compareResult = attendanceValue.compareTo(g.getCompareStartValue()) >= 0
							&& attendanceValue.compareTo(g.getCompareEndValue()) <= 0;
				} else if (g.getCompareOperator() == RangeCompareType.OUTSIDE_RANGE_CLOSED.value) {
					compareResult = attendanceValue.compareTo(g.getCompareStartValue()) < 0
							|| attendanceValue.compareTo(g.getCompareEndValue()) > 0;
				} else if (g.getCompareOperator() == RangeCompareType.OUTSIDE_RANGE_OPEN.value) {
					compareResult = attendanceValue.compareTo(g.getCompareStartValue()) <= 0
							|| attendanceValue.compareTo(g.getCompareEndValue()) >= 0;
				}
			} else {
				BigDecimal singleValue = g.getCompareStartValue();
				if (singleValue == null) {
					/** TODO: 値を集計した値と比較する */
					List<ItemValue> value = AttendanceItemUtil.toItemValues(data, Arrays.asList(g.getSingleAtdItem()));
					singleValue = value.isEmpty() ? BigDecimal.ZERO : getValue(value.get(0));
				}
				if (g.getCompareOperator() == SingleValueCompareType.EQUAL.value) {
					compareResult = attendanceValue.compareTo(singleValue) == 0;
				} else if (g.getCompareOperator() == SingleValueCompareType.GREATER_OR_EQUAL.value) {
					compareResult = attendanceValue.compareTo(singleValue) >= 0;
				} else if (g.getCompareOperator() == SingleValueCompareType.GREATER_THAN.value) {
					compareResult = attendanceValue.compareTo(singleValue) > 0;
				} else if (g.getCompareOperator() == SingleValueCompareType.LESS_OR_EQUAL.value) {
					compareResult = attendanceValue.compareTo(singleValue) <= 0;
				} else if (g.getCompareOperator() == SingleValueCompareType.LESS_THAN.value) {
					compareResult = attendanceValue.compareTo(singleValue) < 0;
				} else if (g.getCompareOperator() == SingleValueCompareType.NOT_EQUAL.value) {
					compareResult = attendanceValue.compareTo(singleValue) != 0;
				}
			}
			return compareResult;
		}).collect(Collectors.toList());
		if (operatorGroup == LogicalOperator.AND.value) {
			return groupResult.stream().allMatch(c -> c);
		} else {
			return groupResult.stream().anyMatch(c -> c);
		}
	}

	private BigDecimal getValue(ItemValue value) {
		if (value.value() == null) {
			return BigDecimal.ZERO;
		}
		return value.getValueType() == ValueType.DECIMAL ? value.value() : BigDecimal.valueOf(value.value());
	}

	private Map<String, Boolean> toEmptyResultMap() {
		return new HashMap<>();
	}

	private RegulationInfoEmployeeQuery createQueryToFilterEmployees(GeneralDate workingDate,
			ErrorAlarmWorkRecordDto checkCondition) {
		RegulationInfoEmployeeQuery query = new RegulationInfoEmployeeQuery();
		query.setBaseDate(workingDate);
		query.setReferenceRange(EmployeeReferenceRange.ALL_EMPLOYEE.value);
		query.setFilterByEmployment(checkCondition.getAlCheckTargetCondition().isFilterByEmployment());
		query.setEmploymentCodes(checkCondition.getAlCheckTargetCondition().getLstEmployment());
		query.setFilterByDepartment(false);
		// query.setDepartmentCodes(departmentCodes);
		query.setFilterByWorkplace(false);
		// query.setWorkplaceCodes(workplaceCodes);
		query.setFilterByClassification(checkCondition.getAlCheckTargetCondition().isFilterByClassification());
		query.setClassificationCodes(checkCondition.getAlCheckTargetCondition().getLstClassification());
		query.setFilterByJobTitle(checkCondition.getAlCheckTargetCondition().isFilterByJobTitle());
		query.setJobTitleCodes(checkCondition.getAlCheckTargetCondition().getLstJobTitle());
		query.setFilterByWorktype(checkCondition.getAlCheckTargetCondition().isFilterByBusinessType());
		query.setWorktypeCodes(checkCondition.getAlCheckTargetCondition().getLstBusinessType());
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

	private RegulationInfoEmployeeQuery createQueryToFilterEmployees(GeneralDate workingDate,
			ErrorAlarmCondition checkCondition) {
		RegulationInfoEmployeeQuery query = new RegulationInfoEmployeeQuery();
		query.setBaseDate(workingDate);
		query.setReferenceRange(EmployeeReferenceRange.ALL_EMPLOYEE.value);
		query.setFilterByEmployment(checkCondition.getCheckTargetCondtion().getFilterByEmployment());
		query.setEmploymentCodes(checkCondition.getCheckTargetCondtion().getLstEmploymentCode().stream().map(c -> c.v())
				.collect(Collectors.toList()));
		query.setFilterByDepartment(false);
		// query.setDepartmentCodes(departmentCodes);
		query.setFilterByWorkplace(false);
		// query.setWorkplaceCodes(workplaceCodes);
		query.setFilterByClassification(checkCondition.getCheckTargetCondtion().getFilterByClassification());
		query.setClassificationCodes(checkCondition.getCheckTargetCondtion().getLstClassificationCode().stream()
				.map(c -> c.v()).collect(Collectors.toList()));
		query.setFilterByJobTitle(checkCondition.getCheckTargetCondtion().getFilterByJobTitle());
		query.setJobTitleCodes(checkCondition.getCheckTargetCondtion().getLstJobTitleId());
		query.setFilterByWorktype(checkCondition.getCheckTargetCondtion().getFilterByBusinessType());
		query.setWorktypeCodes(checkCondition.getCheckTargetCondtion().getLstBusinessTypeCode().stream().map(c -> c.v())
				.collect(Collectors.toList()));
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
