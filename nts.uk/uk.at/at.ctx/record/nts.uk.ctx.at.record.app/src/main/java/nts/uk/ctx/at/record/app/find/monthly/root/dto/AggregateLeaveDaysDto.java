package nts.uk.ctx.at.record.app.find.monthly.root.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.ItemConst;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ValueType;

@Data
/** 集計休業日数 */
@NoArgsConstructor
@AllArgsConstructor
public class AggregateLeaveDaysDto implements ItemConst {

	/** 休業区分: 休業区分 */
//	@AttendanceItemValue(type = ValueType.INTEGER)
//	@AttendanceItemLayout(jpPropertyName = "休業区分", layout = "A", needCheckIDWithMethod = "leaveAtr")
	private int attr;

	/** 日数: 勤怠月間日数 */
	@AttendanceItemValue(type = ValueType.DAYS)
	@AttendanceItemLayout(jpPropertyName = DAYS, layout = LAYOUT_B, needCheckIDWithMethod = DEFAULT_CHECK_ENUM_METHOD)
	private double days;

	public String enumText() {
		switch (this.attr) {
		case 0:
			return E_OFF_BEFORE_BIRTH;
		case 1:
			return E_OFF_AFTER_BIRTH;
		case 2:
			return E_OFF_CHILD_CARE;
		case 3:
			return E_OFF_CARE;
		default:
			return E_OFF_INJURY;
		}
	}
}
