package nts.uk.ctx.at.record.app.find.dailyperform.shorttimework.dto;

import java.util.List;

import lombok.Data;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.app.util.attendanceitem.annotation.AttendanceItemRoot;
import nts.uk.ctx.at.shared.app.util.attendanceitem.type.ConvertibleAttendanceItem;

@AttendanceItemRoot(rootName = "日別実績の短時間勤務時間帯")
@Data
public class ShortTimeOfDailyDto implements ConvertibleAttendanceItem {

	//TODO: item id not map
	/** 社員ID: 社員ID */
	private String employeeId;

	/** 年月日: 年月日 */
	private GeneralDate ymd;

	/** 時間帯: 短時間勤務時間帯 */
	//TODO: set list max length
//	@AttendanceItemLayout(layout = "B", jpPropertyName = "", isList = true, listMaxLength = ?, setFieldWithIndex = "shortWorkTimeFrameNo")
	private List<ShortWorkTimeSheetDto> shortWorkingTimeSheets;

}
