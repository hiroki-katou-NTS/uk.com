package nts.uk.ctx.at.shared.app.find.attendanceitem.daily.dto;

import lombok.Data;
import nts.uk.ctx.at.shared.app.util.attendanceitem.annotation.AttendanceItemLayout;
import nts.uk.ctx.at.shared.app.util.attendanceitem.annotation.AttendanceItemValue;
import nts.uk.ctx.at.shared.app.util.attendanceitem.type.ValueType;

/** 日別実績の短時間勤務時間 */
@Data
public class ShortWorkTimeSheetDto {
	
	/** 合計控除時間 */
	@AttendanceItemLayout(layout="A")
	@AttendanceItemValue(itemId=-1, type=ValueType.INTEGER)
	private TotalDeductionTimeDto totalDeductionTime;
	
	/** 合計時間 */
	@AttendanceItemLayout(layout="B")
	@AttendanceItemValue(itemId=-1, type=ValueType.INTEGER)
	private TotalDeductionTimeDto totalTime;
	
	/** 勤務回数 */
	@AttendanceItemLayout(layout="C")
	@AttendanceItemValue(itemId=-1, type=ValueType.INTEGER)
	private int times;
	
	/** 育児介護区分 */
	@AttendanceItemLayout(layout="D")
	@AttendanceItemValue(itemId=-1, type=ValueType.INTEGER)
	private int childCareAndFamilyCareAtr;
}
