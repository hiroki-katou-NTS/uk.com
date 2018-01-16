package nts.uk.ctx.at.record.app.find.dailyperform.dto;

import java.util.List;

import lombok.Data;
import nts.uk.ctx.at.shared.app.util.attendanceitem.annotation.AttendanceItemLayout;

/** 日別実績の加給時間 */
@Data
public class RaisingSalaryTimeDailyPerformDto {

	/** 特定日加給時間 : 加給時間 */
	@AttendanceItemLayout(layout = "A", jpPropertyName = "特定加給時間", needCheckIDWithIndex = true, listMaxLength = 10, indexField = "raisingSalaryNo")
	private List<RaisingSalaryTimeDto> specificDayOfRaisingSalaryTime;

	/** 加給時間 : 加給時間 */
	@AttendanceItemLayout(layout = "B", jpPropertyName = "加給時間", needCheckIDWithIndex = true, listMaxLength = 10, indexField = "raisingSalaryNo")
	private List<RaisingSalaryTimeDto> raisingSalaryTime;
}
