package nts.uk.ctx.at.record.app.find.monthly.root.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ValueType;

@Data
@NoArgsConstructor
@AllArgsConstructor
/** 月別実績の回数集計::回数集計 */
public class AggregateTimesDto {
	
	/** 回数集計NO: 回数集計NO */
	private int no;

	/** 回数: 勤怠月間回数 */
	@AttendanceItemLayout(jpPropertyName = "回数", layout="A")
	@AttendanceItemValue(type=ValueType.INTEGER)
	private Integer times;
	
	/** 時間: 勤怠月間時間 */
	@AttendanceItemLayout(jpPropertyName = "時間", layout="B")
	@AttendanceItemValue(type=ValueType.INTEGER)
	private Integer time;
}
