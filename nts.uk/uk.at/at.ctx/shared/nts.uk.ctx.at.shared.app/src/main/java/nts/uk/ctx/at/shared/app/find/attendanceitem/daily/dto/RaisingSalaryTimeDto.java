package nts.uk.ctx.at.shared.app.find.attendanceitem.daily.dto;

import lombok.Data;
import nts.uk.ctx.at.shared.app.util.attendanceitem.annotation.AttendanceItemLayout;
import nts.uk.ctx.at.shared.app.util.attendanceitem.annotation.AttendanceItemValue;
import nts.uk.ctx.at.shared.app.util.attendanceitem.type.ValueType;

/** 加給時間*/
@Data
public class RaisingSalaryTimeDto {

	/** 加給時間: 計算付き時間 */
	@AttendanceItemLayout(layout="A")
	private CalcAttachTimeDto rasingSalaryTime;
	
	/** 所定外加給時間: 計算付き時間 */
	@AttendanceItemLayout(layout="B")
	private CalcAttachTimeDto outOfLegalRasingSalaryTime;
	
	/** 所定内加給時間: 計算付き時間 */
	@AttendanceItemLayout(layout="C")
	private CalcAttachTimeDto inLegalRasingSalaryTime;
	
	/** 加給NO: 加給時間項目NO */
	@AttendanceItemLayout(layout="D")
	@AttendanceItemValue(itemId=-1, type=ValueType.INTEGER)
	private int raisingSalaryNo;
}
