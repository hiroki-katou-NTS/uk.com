package nts.uk.ctx.at.record.app.find.dailyperform.resttime.dto;

import java.util.ArrayList;
import java.util.Arrays;
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
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemRoot;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ConvertibleAttendanceItem;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.shr.com.time.TimeWithDayAttr;

@Data
@AttendanceItemRoot(rootName = "日別実績の休憩時間帯")
public class BreakTimeDailyDto implements ConvertibleAttendanceItem {

	private String employeeId;
	
	private GeneralDate ymd;
	
	@AttendanceItemLayout(layout = "A", jpPropertyName = "時間帯", needCheckIDWithMethod = "restTimeType", listMaxLength = 10, indexField = "timeSheetNo", methodForEnumValues = "restTime")
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
	
	public void restTimeType(String text) {
		if(text.contains("就業時間帯から参照")){
			this.restTimeType = 0;
		} else if(text.contains("スケジュールから参照")){
			this.restTimeType = 1;
		}
	}
	
	public List<String> restTime(){
		return Arrays.asList("就業時間帯から参照", "スケジュールから参照");
	}
	
	public static BreakTimeDailyDto getDto(BreakTimeOfDailyPerformance x) {
		BreakTimeDailyDto dto = new BreakTimeDailyDto();
		if(x != null){
			dto.setEmployeeId(x.getEmployeeId());
			dto.setYmd(x.getYmd());
			dto.setRestTimeType(x.getBreakType().value);
			dto.setTimeZone(ConvertHelper.mapTo(x.getBreakTimeSheets(), (c) -> new TimeSheetDto(
					c.getBreakFrameNo().v().intValue(),
					getTimeStamp(c.getStartTime()),
					getTimeStamp(c.getEndTime()),
					c.getBreakTime() == null ? null : c.getBreakTime().valueAsMinutes())));
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
		return new BreakTimeOfDailyPerformance(emp,
					EnumAdaptor.valueOf(restTimeType, BreakType.class),
					timeZone == null ? new ArrayList<>() : ConvertHelper.mapTo(timeZone,
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
