package nts.uk.ctx.at.record.app.find.dailyperform.dto;

import lombok.Data;
import nts.uk.ctx.at.shared.app.util.attendanceitem.annotation.AttendanceItemLayout;
import nts.uk.ctx.at.shared.app.util.attendanceitem.annotation.AttendanceItemValue;
import nts.uk.ctx.at.shared.app.util.attendanceitem.type.ValueType;

/** 日別実績の短時間勤務時間 */
@Data
public class ShortWorkTimeDto {

	/** 合計控除時間 */
	@AttendanceItemLayout(layout = "A", jpPropertyName = "合計控除時間", needCheckIDWithMethod = "childCareAttr")
	private TotalDeductionTimeDto totalDeductionTime;

	/** 合計時間 */
	@AttendanceItemLayout(layout = "B", jpPropertyName = "合計時間", needCheckIDWithMethod = "childCareAttr")
	private TotalDeductionTimeDto totalTime;

	/** 勤務回数 */
	@AttendanceItemLayout(layout = "C", jpPropertyName = "回数", needCheckIDWithMethod = "childCareAttr")
	@AttendanceItemValue(type = ValueType.INTEGER, getIdFromUtil = true)
	private Integer times;

	/** 育児介護区分 */
	@AttendanceItemLayout(layout = "D", jpPropertyName = "育児介護区分")
	@AttendanceItemValue(type = ValueType.INTEGER, getIdFromUtil = true)
	private int childCareAndFamilyCareAtr;
	
	public String childCareAttr(){
		switch (this.childCareAndFamilyCareAtr) {
		case 0:
			return "育児";

		case 1:
			return "介護";
		default:
			return "";
		}
	}
}
