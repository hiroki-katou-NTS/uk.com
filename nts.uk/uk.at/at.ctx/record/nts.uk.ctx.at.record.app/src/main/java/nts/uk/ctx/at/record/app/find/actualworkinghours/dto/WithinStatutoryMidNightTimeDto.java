package nts.uk.ctx.at.record.app.find.actualworkinghours.dto;

import lombok.Data;
import nts.uk.ctx.at.shared.app.util.attendanceitem.annotation.AttendanceItemLayout;

/** 所定内深夜時間 */
@Data
public class WithinStatutoryMidNightTimeDto {

	/** 時間: 計算付き時間 */
	@AttendanceItemLayout(layout="A")
	private CalcAttachTimeDto time;
}
