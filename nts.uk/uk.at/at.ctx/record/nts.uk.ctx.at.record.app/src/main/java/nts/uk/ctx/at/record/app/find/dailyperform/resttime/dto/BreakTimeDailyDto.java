package nts.uk.ctx.at.record.app.find.dailyperform.resttime.dto;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Data;
import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.app.find.dailyperform.common.TimeSheetDto;
import nts.uk.ctx.at.record.app.find.dailyperform.common.TimeStampDto;
import nts.uk.ctx.at.record.dom.breakorgoout.BreakTimeOfDailyPerformance;
import nts.uk.ctx.at.record.dom.breakorgoout.BreakTimeSheet;
import nts.uk.ctx.at.record.dom.breakorgoout.enums.BreakType;
import nts.uk.ctx.at.record.dom.breakorgoout.primitivevalue.BreakFrameNo;
import nts.uk.ctx.at.shared.app.util.attendanceitem.ConvertHelper;
import nts.uk.ctx.at.shared.dom.attendance.util.ItemConst;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemRoot;
import nts.uk.ctx.at.shared.dom.attendance.util.item.AttendanceItemCommon;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.shr.com.time.TimeWithDayAttr;

@Data
@AttendanceItemRoot(rootName = ItemConst.DAILY_BREAK_TIME_NAME)
public class BreakTimeDailyDto extends AttendanceItemCommon {

	private String employeeId;
	
	private GeneralDate ymd;
	
	@AttendanceItemLayout(layout = LAYOUT_A, jpPropertyName = TIME_ZONE, needCheckIDWithMethod = DEFAULT_CHECK_ENUM_METHOD, 
			listMaxLength = 10, indexField = DEFAULT_INDEX_FIELD_NAME)
	private List<TimeSheetDto> timeZone;

	/** 休憩種類 */
	private int attr;

	public String enumText() {
		switch (this.attr) {
		case 0:
			return E_WORK_REF;
		case 1:
			return E_SCHEDULE_REF;
		default:
			return EMPTY_STRING;
		}
	}
	
	public static BreakTimeDailyDto getDto(BreakTimeOfDailyPerformance x) {
		BreakTimeDailyDto dto = new BreakTimeDailyDto();
		if(x != null){
			dto.setEmployeeId(x.getEmployeeId());
			dto.setYmd(x.getYmd());
			dto.setAttr(x.getBreakType() == null ? 0 : x.getBreakType().value);
			dto.setTimeZone(ConvertHelper.mapTo(x.getBreakTimeSheets(), 
													(c) -> new TimeSheetDto(
														c.getBreakFrameNo().v().intValue(),
														getTimeStamp(c.getStartTime()),
														getTimeStamp(c.getEndTime()),
														c.getBreakTime() == null ? 0 : c.getBreakTime().valueAsMinutes())));
			dto.exsistData();
		}
		return dto;
	}
	
	@Override
	public BreakTimeDailyDto clone() {
		BreakTimeDailyDto dto = new BreakTimeDailyDto();
		dto.setEmployeeId(employeeId());
		dto.setYmd(workingDate());
		dto.setAttr(attr);
		dto.setTimeZone(timeZone == null ? null : timeZone.stream().map(t -> t.clone()).collect(Collectors.toList()));
		if(isHaveData()){
			dto.exsistData();
		}
		return dto;
	}
	
	private static TimeStampDto getTimeStamp(TimeWithDayAttr c) {
		return c == null ? null : new TimeStampDto(c.valueAsMinutes(), null, null, null);
	}

	@Override
	public String employeeId() {
		return this.employeeId;
	}

	@Override
	public GeneralDate workingDate() {
		return this.ymd;
	}
	
	@Override
	public BreakTimeOfDailyPerformance toDomain(String emp, GeneralDate date) {
		if(!this.isHaveData()) {
			return null;
		}
		if (employeeId == null) {
			employeeId = this.employeeId();
		}
		if (date == null) {
			date = this.workingDate();
		}
		return new BreakTimeOfDailyPerformance(emp,
					EnumAdaptor.valueOf(attr, BreakType.class),
					ConvertHelper.mapTo(timeZone,
							(d) -> new BreakTimeSheet(new BreakFrameNo(d.getNo()),
									createWorkStamp(d.getStart()),
									createWorkStamp(d.getEnd()),
									// TODO: calculate break time
									new AttendanceTime(d.getBreakTime()))),
					date);
	}

	private TimeWithDayAttr createWorkStamp(TimeStampDto d) {
		return d == null || d.getTimesOfDay() == null ? null : new TimeWithDayAttr(d.getTimesOfDay());
	}
}
