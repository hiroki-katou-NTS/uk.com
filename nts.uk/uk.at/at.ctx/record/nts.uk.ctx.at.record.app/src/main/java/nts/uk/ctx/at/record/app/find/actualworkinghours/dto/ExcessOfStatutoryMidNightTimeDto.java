package nts.uk.ctx.at.record.app.find.actualworkinghours.dto;

import lombok.Data;
import nts.uk.ctx.at.shared.app.util.attendanceitem.annotation.AttendanceItemLayout;
import nts.uk.ctx.at.shared.app.util.attendanceitem.annotation.AttendanceItemValue;
import nts.uk.ctx.at.shared.app.util.attendanceitem.type.ValueType;

/** 所定外深夜時間 */
@Data
public class ExcessOfStatutoryMidNightTimeDto {

	/** 時間: 計算付き時間*/
	@AttendanceItemLayout(layout="A")
	private CalcAttachTimeDto time;
	/** 事前申請時間: 勤怠時間*/
	@AttendanceItemLayout(layout="B")
	@AttendanceItemValue(itemId=-1, type=ValueType.INTEGER)
	private int beforeApplicationTime;
}
