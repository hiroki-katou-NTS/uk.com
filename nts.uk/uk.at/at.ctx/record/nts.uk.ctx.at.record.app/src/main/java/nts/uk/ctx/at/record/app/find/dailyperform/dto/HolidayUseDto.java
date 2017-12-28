package nts.uk.ctx.at.record.app.find.dailyperform.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.app.util.attendanceitem.annotation.AttendanceItemLayout;
import nts.uk.ctx.at.shared.app.util.attendanceitem.annotation.AttendanceItemValue;
import nts.uk.ctx.at.shared.app.util.attendanceitem.type.ValueType;

/**
 * 日別実績の時間年休使用時間
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HolidayUseDto {

	/** 時間年休使用時間 */
	@AttendanceItemLayout(layout = "A", jpPropertyName = "時間年休使用時間")
	@AttendanceItemValue(itemId = { 595, 601 }, type = ValueType.INTEGER)
	private Integer timeAnnualLeaveUseTime;

	/** 時間代休使用時間 */
	@AttendanceItemLayout(layout = "B", jpPropertyName = "時間代休使用時間")
	@AttendanceItemValue(itemId = { 597, 603 }, type = ValueType.INTEGER)
	private Integer timeCompensatoryLeaveUseTime;

	/** 超過有休使用時間 */
	@AttendanceItemLayout(layout = "C", jpPropertyName = "60H超休使用時間")
	@AttendanceItemValue(itemId = { 596, 602 }, type = ValueType.INTEGER)
	private Integer excessHolidayUseTime;

	/** 特別休暇使用時間 */
//	@AttendanceItemLayout(layout = "D")
//	@AttendanceItemValue(itemId = -1, type = ValueType.INTEGER)
	private Integer timeSpecialHolidayUseTime;
}
