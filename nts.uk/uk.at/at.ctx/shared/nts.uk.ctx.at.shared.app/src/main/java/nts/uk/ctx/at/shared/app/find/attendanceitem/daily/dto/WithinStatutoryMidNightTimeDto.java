package nts.uk.ctx.at.shared.app.find.attendanceitem.daily.dto;

import lombok.Data;
import nts.uk.ctx.at.shared.app.util.attendanceitem.annotation.AttendanceItemLayout;
import nts.uk.ctx.at.shared.app.util.attendanceitem.annotation.AttendanceItemValue;
import nts.uk.ctx.at.shared.app.util.attendanceitem.type.ValueType;

/** 所定内深夜時間 */
@Data
public class WithinStatutoryMidNightTimeDto {

	/** 時間: 計算付き時間 */
	@AttendanceItemLayout(layout="A")
	private CalcAttachTimeDto time;
}
