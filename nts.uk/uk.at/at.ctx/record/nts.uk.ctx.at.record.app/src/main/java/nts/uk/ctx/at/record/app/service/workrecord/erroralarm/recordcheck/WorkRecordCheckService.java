package nts.uk.ctx.at.record.app.service.workrecord.erroralarm.recordcheck;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.ErrorAlarmWorkRecordRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.ErrorAlarmCondition;
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

		return record.stream().collect(Collectors.toMap(c -> c.employeeId(), c -> checkErrorAlarmCondition(c, checkCondition)));
	}

	public Map<String, Boolean> check(GeneralDate workingDate, Collection<String> employeeIds, String EACheckID) {
		ErrorAlarmCondition checkCondition = errorRecordRepo.findConditionByErrorAlamCheckId(EACheckID).orElse(null);

		if (checkCondition != null) {
			return check(workingDate, employeeIds, checkCondition);
		}

		return toEmptyResultMap();
	}

	private boolean checkErrorAlarmCondition(DailyRecordDto record, ErrorAlarmCondition condition){
		WorkInfoOfDailyPerformance workInfo = (WorkInfoOfDailyPerformance) record.getWorkInfo().toDomain();

		/** 勤務種類をチェックする */
		boolean workTypeCheck = condition.getWorkTypeCondition().checkWorkType(workInfo);
		if (workTypeCheck) {
			/** 就業時間帯をチェックする */
			boolean workTimeCheck = condition.getWorkTimeCondition().checkWorkTime(workInfo);
			if (workTimeCheck) {
				/** 勤怠項目をチェックする */
				return condition.getAtdItemCondition().check(c -> AttendanceItemUtil.toItemValues(record, c)
						.stream().map(iv -> getValue(iv)).collect(Collectors.toList())
				);
			}
		}
		return false;
	}

	private Integer getValue(ItemValue value) {
		if (value.value() == null) {
			return 0;
		}
		return value.getValueType() == ValueType.DECIMAL ? ((BigDecimal) value.value()).intValue() : (Integer) value.value();
	}

	private Map<String, Boolean> toEmptyResultMap() {
		return new HashMap<>();
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
