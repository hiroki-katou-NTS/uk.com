package nts.uk.ctx.at.record.pubimp.dailyattendanceitem;

import java.util.ArrayList;
import java.util.Arrays;
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
import nts.uk.ctx.at.record.pub.dailyattendanceitem.AttendanceItemService;
import nts.uk.ctx.at.record.pub.dailyattendanceitem.AttendanceItemValue;
import nts.uk.ctx.at.record.pub.dailyattendanceitem.AttendanceResult;
import nts.uk.ctx.at.record.pub.dailyattendanceitem.MonthlyAttendanceResult;
import nts.uk.ctx.at.shared.app.util.attendanceitem.ConvertHelper;
import nts.uk.ctx.at.shared.dom.attendance.util.AttendanceItemUtil;
import nts.uk.ctx.at.shared.dom.attendance.util.AttendanceItemUtil.AttendanceItemType;
import nts.uk.ctx.at.shared.dom.attendance.util.item.AttendanceItemCommon;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureDate;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

@Stateless
public class AttendanceItemServiceImpl implements AttendanceItemService {

	@Inject
	private DailyRecordWorkFinder fullFinder;
	
	@Inject
	private MonthlyRecordWorkFinder monthlyFinder;

	@Override
	public Optional<AttendanceItemValue> getValueOf(String employeeId, GeneralDate workingDate, int itemId) {
		DailyRecordDto itemDtos = this.fullFinder.find(employeeId, workingDate);
		return AttendanceItemUtil.toItemValues(itemDtos, Arrays.asList(itemId)).stream()
				.filter(c -> c.itemId() == itemId).findFirst()
				.map(c -> new AttendanceItemValue(c.getValueType().value, c.itemId(), c.value()));
	}

	@Override
	public AttendanceResult getValueOf(String employeeId, GeneralDate workingDate, List<Integer> itemIds) {
		DailyRecordDto itemDtos = this.fullFinder.find(employeeId, workingDate);
		return getAndConvert(employeeId, workingDate, itemDtos, itemIds);
	}

	@Override
	public List<AttendanceResult> getValueOf(List<String> employeeId, DatePeriod workingDate, List<Integer> itemIds) {
		if(employeeId == null || employeeId.isEmpty() || workingDate == null){
			return new ArrayList<>();
		}
		return this.fullFinder.find(employeeId, workingDate).stream()
				.map(c -> getAndConvert(c.employeeId(), c.workingDate(),(DailyRecordDto)  c, itemIds))
				.collect(Collectors.toList());
	}

	@Override
	public List<MonthlyAttendanceResult> getMonthlyValueOf(List<String> employeeId, YearMonth yearMonth, int closureId,
			int clouseDate, boolean lastDayOfMonth, List<Integer> itemIds) {
		return employeeId.stream().map(c -> getMonthlyValueOf(c, yearMonth, closureId, clouseDate, lastDayOfMonth, itemIds))
									.collect(Collectors.toList());
	}

	@Override
	public MonthlyAttendanceResult getMonthlyValueOf(String employeeId, YearMonth yearMonth, int closureId,
			int clouseDate, boolean lastDayOfMonth, List<Integer> itemIds) {
		MonthlyRecordWorkDto itemDtos = monthlyFinder.find(employeeId, yearMonth, 
															ConvertHelper.getEnum(closureId, ClosureId.class), 
															new ClosureDate(clouseDate, lastDayOfMonth));
		return getAndConvert(employeeId, yearMonth, closureId, clouseDate, lastDayOfMonth, itemDtos, itemIds);
	}

	
	private AttendanceResult getAndConvert(String employeeId, GeneralDate workingDate, DailyRecordDto data, List<Integer> itemIds){
		return AttendanceResult.builder().employeeId(employeeId).workingDate(workingDate)
				.attendanceItems(toAttendanceValue(data, itemIds, AttendanceItemType.DAILY_ITEM)).build();
	}
	
	private MonthlyAttendanceResult getAndConvert(String employeeId, YearMonth yearMonth, int closureId,
			int clouseDate, boolean lastDayOfMonth, MonthlyRecordWorkDto data, List<Integer> itemIds){
		return MonthlyAttendanceResult.builder().employeeId(employeeId).yearMonth(yearMonth)
				.closureId(closureId).clouseDate(clouseDate).lastDayOfMonth(lastDayOfMonth)
				.attendanceItems(toAttendanceValue(data, itemIds, AttendanceItemType.MONTHLY_ITEM)).build();
	}

	private <T extends AttendanceItemCommon> List<AttendanceItemValue> toAttendanceValue(T data, List<Integer> itemIds, AttendanceItemType type) {
		return AttendanceItemUtil.toItemValues(data, itemIds, type).stream()
				.map(c -> new AttendanceItemValue(c.getValueType().value, c.itemId(), c.value()))
				.collect(Collectors.toList());
	}
}
