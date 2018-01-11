package nts.uk.ctx.at.record.app.find.dailyperform.workrecord.dto;

import java.util.List;

import lombok.Data;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.app.util.attendanceitem.annotation.AttendanceItemRoot;
import nts.uk.ctx.at.shared.app.util.attendanceitem.type.ConvertibleAttendanceItem;

@Data
@AttendanceItemRoot(rootName = "日別実績の作業別勤怠時間")
public class AttendanceTimeByWorkOfDailyDto implements ConvertibleAttendanceItem {

	//TODO: not map item id
	/** 社員ID: 社員ID */
	private String employeeId;

	/** 年月日: 年月日 */
	private GeneralDate ymd;

	/** 作業一覧: 日別実績の作業時間 */
	//TODO: set list max length
//	@AttendanceItemLayout(layout = "A", jpPropertyName = "", isList = true, listMaxLength = ?, setFieldWithIndex = "workFrameNo")
	private List<WorkTimeOfDailyDto> workTimes;
}
