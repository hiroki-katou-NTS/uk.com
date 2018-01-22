package nts.uk.ctx.at.record.app.find.dailyperform.dto;

import lombok.Data;
import nts.uk.ctx.at.shared.app.util.attendanceitem.annotation.AttendanceItemLayout;

/** 加給時間 */
@Data
public class RaisingSalaryTimeDto {

	/** 加給時間: 計算付き時間 */
	@AttendanceItemLayout(layout = "A", jpPropertyName = "加給時間")
	private CalcAttachTimeDto rasingSalaryTime;

	/** 法定外加給時間: 計算付き時間 */
	@AttendanceItemLayout(layout = "B", jpPropertyName = "法定外加給時間")
	private CalcAttachTimeDto outOfLegalRasingSalaryTime;

	/** 法定内加給時間: 計算付き時間 */
	@AttendanceItemLayout(layout = "C", jpPropertyName = "法定内加給時間")
	private CalcAttachTimeDto inLegalRasingSalaryTime;

	/** 加給NO: 加給時間項目NO */
	private Integer raisingSalaryNo;
}
