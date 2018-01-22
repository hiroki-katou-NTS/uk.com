package nts.uk.ctx.at.record.app.find.dailyperform.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.app.util.attendanceitem.annotation.AttendanceItemLayout;
import nts.uk.ctx.at.shared.app.util.attendanceitem.annotation.AttendanceItemValue;
import nts.uk.ctx.at.shared.app.util.attendanceitem.item.ValueType;

/** 所定外深夜時間 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExcessOfStatutoryMidNightTimeDto {

	/** 時間: 計算付き時間 */
	@AttendanceItemLayout(layout = "A", jpPropertyName="時間")
	private CalcAttachTimeDto time;

	/** 事前申請時間: 勤怠時間 */
	 @AttendanceItemLayout(layout = "B", jpPropertyName="事前申請時間")
	 @AttendanceItemValue(type = ValueType.INTEGER)
	private Integer beforeApplicationTime;
}
