package nts.uk.ctx.at.record.app.find.dailyperform.resttime.dto;

import java.util.Arrays;
import java.util.List;

import lombok.Data;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.app.find.dailyperform.common.TimeSheetDto;
import nts.uk.ctx.at.record.app.find.dailyperform.common.TimeStampDto;
import nts.uk.ctx.at.record.dom.breakorgoout.BreakTimeOfDailyPerformance;
import nts.uk.ctx.at.record.dom.worktime.WorkStamp;
import nts.uk.ctx.at.shared.app.util.attendanceitem.ConvertHelper;
import nts.uk.ctx.at.shared.app.util.attendanceitem.annotation.AttendanceItemLayout;
import nts.uk.ctx.at.shared.app.util.attendanceitem.annotation.AttendanceItemRoot;
import nts.uk.ctx.at.shared.app.util.attendanceitem.item.ConvertibleAttendanceItem;

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
	
	public static BreakTimeDailyDto toDto(BreakTimeOfDailyPerformance x) {
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
	
	private static TimeStampDto getTimeStamp(WorkStamp c) {
		return c == null ? null : new TimeStampDto(c.getTimeWithDay().valueAsMinutes(),
				c.getAfterRoundingTime().valueAsMinutes(),
				c.getLocationCode().v(), c.getStampSourceInfo().value);
	}
}
