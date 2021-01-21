package nts.uk.screen.at.app.monthlyperformance.correction.query;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.app.find.monthly.finder.MonthlyRecordWorkFinder;
import nts.uk.ctx.at.record.app.find.monthly.root.MonthlyRecordWorkDto;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.AttendanceItemUtil;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;
import nts.uk.shr.com.time.calendar.date.ClosureDate;

@Stateless
public class MonthlyModifyQueryProcessor {

	@Inject
	MonthlyRecordWorkFinder monthlyRecordWorkFinder;

	public List<MonthlyModifyResult> initScreen(MonthlyMultiQuery query, List<Integer> itemIds, YearMonth yearMonth,
			ClosureId closureId, ClosureDate closureDate) {
		if (query.getEmployeeIds() == null || query.getEmployeeIds().isEmpty()) {
			return new ArrayList<>();
		}
		List<MonthlyRecordWorkDto> lstData = this.monthlyRecordWorkFinder.find(query.getEmployeeIds(),
				Arrays.asList(yearMonth));
		lstData = lstData.stream().filter(x -> x.getClosureID() == closureId.value).collect(Collectors.toList());
		if (lstData.isEmpty())
			return new ArrayList<>();
		return AttendanceItemUtil.toItemValues(lstData, itemIds, AttendanceItemUtil.AttendanceItemType.MONTHLY_ITEM)
				.entrySet().stream().map(record -> {
					return MonthlyModifyResult.builder().items(record.getValue())
							.employeeId(record.getKey().getEmployeeId()).yearMonth(record.getKey().getYearMonth().v())
							.closureId(record.getKey().getClosureID()).closureDate(record.getKey().getClosureDate())
							.workDatePeriod(record.getKey().getAttendanceTime().getDatePeriod().toDomain())
							.version(record.getKey().getAffiliation().getVersion()).completed();
				}).collect(Collectors.toList());
	}

	public Optional<MonthlyRecordWorkDto> getDataMonthAll(MonthlyMultiQuery query, List<Integer> itemIds, YearMonth yearMonth,
			ClosureId closureId, ClosureDate closureDate) {
		if (query.getEmployeeIds() == null || query.getEmployeeIds().isEmpty()) {
			return Optional.empty();
		}
		List<MonthlyRecordWorkDto> lstData = this.monthlyRecordWorkFinder.find(query.getEmployeeIds(),
				Arrays.asList(yearMonth));
		return lstData.stream()
				.filter(x -> x.getClosureID() == closureId.value
						&& x.getClosureDate().getClosureDay() == x.getClosureDate().getClosureDay()
						&& x.getClosureDate().getLastDayOfMonth() == x.getClosureDate().getLastDayOfMonth())
				.findFirst();
	}
}
