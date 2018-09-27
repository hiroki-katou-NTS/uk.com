package nts.uk.ctx.at.record.app.service.attendanceitem.value;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.app.find.dailyperform.DailyRecordDto;
import nts.uk.ctx.at.record.app.find.dailyperform.DailyRecordWorkFinder;
import nts.uk.ctx.at.record.app.find.monthly.finder.MonthlyRecordWorkFinder;
import nts.uk.ctx.at.record.app.find.monthly.root.MonthlyRecordWorkDto;
import nts.uk.ctx.at.shared.app.util.attendanceitem.ConvertHelper;
import nts.uk.ctx.at.shared.dom.attendance.util.AttendanceItemUtil;
import nts.uk.ctx.at.shared.dom.attendance.util.AttendanceItemUtil.AttendanceItemType;
import nts.uk.ctx.at.shared.dom.attendance.util.item.AttendanceItemCommon;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ItemValue;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;
import nts.uk.shr.com.time.calendar.date.ClosureDate;
import nts.uk.shr.com.time.calendar.period.DatePeriod;
import nts.uk.shr.com.time.calendar.period.YearMonthPeriod;

@Stateless
public class AttendanceItemValueServiceImp implements AttendanceItemValueService {


	@Inject
	private DailyRecordWorkFinder fullFinder;
	
    @Inject
    private MonthlyRecordWorkFinder monthlyFinder;
    
	@Override
	public Optional<ItemValue> getValueOf(String employeeId, GeneralDate workingDate, int itemId) {
		DailyRecordDto itemDtos = this.fullFinder.find(employeeId, workingDate);
		return AttendanceItemUtil.toItemValues(itemDtos, Arrays.asList(itemId)).stream()
				.filter(c -> c.itemId() == itemId).findFirst();
	}

	@Override
	public AttendanceItemValueResult getValueOf(String employeeId, GeneralDate workingDate,
			Collection<Integer> itemIds) {
		DailyRecordDto itemDtos = this.fullFinder.find(employeeId, workingDate);
		return getAndConvert(employeeId, workingDate, itemDtos, itemIds);
	}

	@Override
	public List<AttendanceItemValueResult> getValueOf(Collection<String> employeeId, DatePeriod workingDate,
			Collection<Integer> itemIds) {
		if (employeeId == null || employeeId.isEmpty() || workingDate == null) {
			return new ArrayList<>();
		}
		return this.fullFinder.find(new ArrayList<>(employeeId), workingDate).stream()
				.map(c -> getAndConvert(c.employeeId(), c.workingDate(), (DailyRecordDto) c, itemIds))
				.collect(Collectors.toList());
	}

	@Override
	public List<MonthlyAttendanceItemValueResult> getMonthlyValueOf(Collection<String> employeeId, YearMonth yearMonth,
			int closureId, int clouseDate, boolean lastDayOfMonth, Collection<Integer> itemIds) {
		return employeeId.stream().map(c -> getMonthlyValueOf(c, yearMonth, closureId, clouseDate, lastDayOfMonth, itemIds))
									.collect(Collectors.toList());
	}

	@Override
	public MonthlyAttendanceItemValueResult getMonthlyValueOf(String employeeId, YearMonth yearMonth, int closureId,
			int clouseDate, boolean lastDayOfMonth, Collection<Integer> itemIds) {
		MonthlyRecordWorkDto itemDtos = monthlyFinder.find(employeeId, yearMonth,
				ConvertHelper.getEnum(closureId, ClosureId.class), new ClosureDate(clouseDate, lastDayOfMonth));
		return getAndConvert(employeeId, yearMonth, closureId, clouseDate, lastDayOfMonth, itemDtos, itemIds);
	}

	@Override
	public List<MonthlyAttendanceItemValueResult> getMonthlyValueOf(Collection<String> employeeId, DatePeriod range,
			Collection<Integer> itemIds) {
		List<MonthlyRecordWorkDto> result = monthlyFinder.find(employeeId, range);
		return result.stream().map(mrw -> {
			return getAndConvert(mrw.employeeId(), mrw.yearMonth(), mrw.getClosureID(),
					mrw.getClosureDate().getClosureDay(), mrw.getClosureDate().getLastDayOfMonth(), mrw, itemIds);
		}).collect(Collectors.toList());
	}

	@Override
	public List<MonthlyAttendanceItemValueResult> getMonthlyValueOf(String employeeId, DatePeriod range,
			Collection<Integer> itemIds) {
		return getMonthlyValueOf(Arrays.asList(employeeId), range, itemIds);
	}

	@Override
	public List<MonthlyAttendanceItemValueResult> getMonthlyValueOf(Collection<String> employeeId,
			YearMonthPeriod range, Collection<Integer> itemIds) {
		List<MonthlyRecordWorkDto> result = monthlyFinder.find(employeeId, range);
		return result.stream().map(mrw -> {
			return getAndConvert(mrw.employeeId(), mrw.yearMonth(), mrw.getClosureID(),
					mrw.getClosureDate().getClosureDay(), mrw.getClosureDate().getLastDayOfMonth(), mrw, itemIds);
		}).collect(Collectors.toList());
	}

	@Override
	public List<MonthlyAttendanceItemValueResult> getMonthlyValueOf(String employeeId, YearMonthPeriod range,
			Collection<Integer> itemIds) {
		return getMonthlyValueOf(Arrays.asList(employeeId), range, itemIds);
	}

	@Override
	public List<MonthlyAttendanceItemValueResult> getMonthlyValueOf(Collection<String> employeeId, YearMonth ym,
			Collection<Integer> itemIds) {
		List<MonthlyRecordWorkDto> result = monthlyFinder.find(employeeId, ym);
		return result.stream().map(mrw -> {
			return getAndConvert(mrw.employeeId(), mrw.yearMonth(), mrw.getClosureID(),
					mrw.getClosureDate().getClosureDay(), mrw.getClosureDate().getLastDayOfMonth(), mrw, itemIds);
		}).collect(Collectors.toList());
	}

	@Override
	public List<MonthlyAttendanceItemValueResult> getMonthlyValueOf(String employeeId, YearMonth ym,
			Collection<Integer> itemIds) {
		return getMonthlyValueOf(Arrays.asList(employeeId), ym, itemIds);
	}

	private AttendanceItemValueResult getAndConvert(String employeeId, GeneralDate workingDate, DailyRecordDto data,
			Collection<Integer> itemIds) {
		return AttendanceItemValueResult.builder().employeeId(employeeId).workingDate(workingDate)
				.attendanceItems(toAttendanceValue(data, itemIds, AttendanceItemType.DAILY_ITEM)).build();
	}

	private MonthlyAttendanceItemValueResult getAndConvert(String employeeId, YearMonth yearMonth, int closureId,
			Integer clouseDate, Boolean lastDayOfMonth, MonthlyRecordWorkDto data, Collection<Integer> itemIds) {
		return MonthlyAttendanceItemValueResult.builder()
												.employeeId(employeeId)
												.yearMonth(yearMonth).closureId(closureId)
												.clouseDate(clouseDate).lastDayOfMonth(lastDayOfMonth)
												.attendanceItems(toAttendanceValue(data, itemIds, AttendanceItemType.MONTHLY_ITEM))
												.build();
	}

	private <T extends AttendanceItemCommon> List<ItemValue> toAttendanceValue(T data,
			Collection<Integer> itemIds, AttendanceItemType type) {
		return AttendanceItemUtil.toItemValues(data, itemIds, type);
	}
	
}
