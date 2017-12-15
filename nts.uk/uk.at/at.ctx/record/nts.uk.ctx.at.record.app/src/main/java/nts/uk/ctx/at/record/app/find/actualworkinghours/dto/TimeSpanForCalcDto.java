package nts.uk.ctx.at.record.app.find.actualworkinghours.dto;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.app.util.attendanceitem.annotation.AttendanceItemLayout;
import nts.uk.ctx.at.shared.app.util.attendanceitem.annotation.AttendanceItemValue;
import nts.uk.ctx.at.shared.app.util.attendanceitem.type.ValueType;

/**
 * 計算用時間帯
 *
 */
@Getter
@Setter
public class TimeSpanForCalcDto {

	/** 開始時刻 */
	@AttendanceItemLayout(layout = "A")
	@AttendanceItemValue(itemId = -1, type = ValueType.INTEGER)
	private Integer start;

	/** 終了時刻 */
	@AttendanceItemLayout(layout = "B")
	@AttendanceItemValue(itemId = -1, type = ValueType.INTEGER)
	private Integer end;
}