package nts.uk.ctx.at.record.app.find.monthly.root.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ValueType;

@Data
/** 集計休業日数 */
@NoArgsConstructor
@AllArgsConstructor
public class AggregateLeaveDaysDto {

	/** 休業区分: 休業区分 */
//	@AttendanceItemValue(type = ValueType.INTEGER)
//	@AttendanceItemLayout(jpPropertyName = "休業区分", layout = "A", needCheckIDWithMethod = "leaveAtr")
	private int leaveAtr;

	/** 日数: 勤怠月間日数 */
	@AttendanceItemValue(type = ValueType.DOUBLE)
	@AttendanceItemLayout(jpPropertyName = "日数", layout = "B", needCheckIDWithMethod = "leaveAtr")
	private Double days;

	public String leaveAtr() {
		switch (this.leaveAtr) {
		case 0:
			return "産前休業";
		case 1:
			return "産後休業";
		case 2:
			return "育児休業";
		case 3:
			return "介護休業";
		case 4:
		default:
			return "傷病休業";
		}
	}
}
