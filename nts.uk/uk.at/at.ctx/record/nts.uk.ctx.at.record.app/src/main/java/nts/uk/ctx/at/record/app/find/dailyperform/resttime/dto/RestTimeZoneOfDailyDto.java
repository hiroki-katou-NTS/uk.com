package nts.uk.ctx.at.record.app.find.dailyperform.resttime.dto;

import java.util.List;

import lombok.Data;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.app.find.dailyperform.common.TimeSheetDto;
import nts.uk.ctx.at.shared.app.util.attendanceitem.annotation.AttendanceItemLayout;
import nts.uk.ctx.at.shared.app.util.attendanceitem.annotation.AttendanceItemRoot;
import nts.uk.ctx.at.shared.app.util.attendanceitem.type.ConvertibleAttendanceItem;

@Data
@AttendanceItemRoot(rootName = "日別実績の休憩時間帯")
public class RestTimeZoneOfDailyDto implements ConvertibleAttendanceItem {

	private String employeeId;
	
	private GeneralDate ymd;
	
	@AttendanceItemLayout(layout = "A", jpPropertyName = "時間帯", isList = true, needCheckIDWithMethod = "restTimeType", listMaxLength = 10, setFieldWithIndex = "timeSheetNo")
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
}
