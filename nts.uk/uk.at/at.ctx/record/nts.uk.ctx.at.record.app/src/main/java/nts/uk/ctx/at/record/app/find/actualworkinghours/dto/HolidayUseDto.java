package nts.uk.ctx.at.record.app.find.actualworkinghours.dto;

import lombok.Data;
import nts.uk.ctx.at.shared.app.util.attendanceitem.annotation.AttendanceItemLayout;
import nts.uk.ctx.at.shared.app.util.attendanceitem.annotation.AttendanceItemValue;
import nts.uk.ctx.at.shared.app.util.attendanceitem.type.ValueType;

/**
 * 日別実績の時間年休使用時間
 */
@Data
public class HolidayUseDto {

	/** 時間年休使用時間 */
	@AttendanceItemLayout(layout="A")
	@AttendanceItemValue(itemId=-1, type=ValueType.INTEGER)
	private Integer timeAnnualLeaveUseTime;
	
	/** 時間代休使用時間 */
	@AttendanceItemLayout(layout="B")
	@AttendanceItemValue(itemId=-1, type=ValueType.INTEGER)
	private Integer timeCompensatoryLeaveUseTime;
	
	/** 超過有休使用時間 */
	@AttendanceItemLayout(layout="C")
	@AttendanceItemValue(itemId=-1, type=ValueType.INTEGER)
	private Integer excessHolidayUseTime;
	
	/** 特別休暇使用時間 */
	@AttendanceItemLayout(layout="D")
	@AttendanceItemValue(itemId=-1, type=ValueType.INTEGER)
	private Integer timeSpecialHolidayUseTime;
}
