package nts.uk.ctx.at.record.app.find.dailyperform.resttime.dto;

import java.util.List;

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
import nts.uk.ctx.at.shared.dom.attendance.util.AttendanceLayoutConst;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemRoot;
import nts.uk.ctx.at.shared.dom.attendance.util.item.AttendanceItemCommon;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.shr.com.time.TimeWithDayAttr;

@Data
@AttendanceItemRoot(rootName = AttendanceLayoutConst.DAILY_BREAK_TIME_NAME)
public class BreakTimeDailyDto extends AttendanceItemCommon {

	private String employeeId;
	
	private GeneralDate ymd;
	
	@AttendanceItemLayout(layout = "A", jpPropertyName = "時間帯", needCheckIDWithMethod = "restTimeType", listMaxLength = 10, indexField = "timeSheetNo")
	private List<TimeSheetDto> timeZone;

	/** 休憩種類 */
	private int restTimeType;

	public String restTimeType() {
		switch (this.restTimeType) {
		case 0:
			return "就業時間帯から参照";
		case 1:
			return "スケジュールから参照";
		default:
			return "";
		}
	}
	
	public static BreakTimeDailyDto getDto(BreakTimeOfDailyPerformance x) {
		BreakTimeDailyDto dto = new BreakTimeDailyDto();
		if(x != null){
			dto.setEmployeeId(x.getEmployeeId());
			dto.setYmd(x.getYmd());
			dto.setRestTimeType(x.getBreakType() == null ? 0 : x.getBreakType().value);
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
					EnumAdaptor.valueOf(restTimeType, BreakType.class),
					ConvertHelper.mapTo(timeZone,
							(d) -> new BreakTimeSheet(new BreakFrameNo(d.getTimeSheetNo()),
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
