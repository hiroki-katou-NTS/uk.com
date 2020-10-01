package nts.uk.ctx.at.record.app.find.dailyperform.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemLayout;

/** 休出深夜時間 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HolidayWorkMidNightTimeDto {

	/** 時間: 計算付き時間 */
	@AttendanceItemLayout(layout = "A", jpPropertyName = "時間", needCheckIDWithMethod = "holidayWorkAttr")
	private CalcAttachTimeDto time;

	/** 法定区分: 休日出勤の法定区分 */
	// @AttendanceItemLayout(layout = "B")
	// @AttendanceItemValue(itemId = -1, type = ValueType.INTEGER)
	private Integer holidayWorkOfPrescribedAtr;

	public String holidayWorkAttr() {
		switch (this.holidayWorkOfPrescribedAtr) {
		case 0:
			return "法定内休出";
		case 1:
			return "法定外休出";
		case 2:
			return "祝日休出";
		default:
			return "";
		}
	}
}
