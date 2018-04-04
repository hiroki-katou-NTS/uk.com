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
/** 集計PCログオン時刻 + 集計PCログオン乖離 */
public class TotalPcLogon {

	@AttendanceItemLayout(jpPropertyName = "合計時刻", layout = "A")
	@AttendanceItemValue(type = ValueType.INTEGER)
	/** 合計時刻: 勤怠月間時間 + 合計時間: 勤怠月間時間 */
	private Integer totalTime;
	
	@AttendanceItemLayout(jpPropertyName = "合計日数", layout = "B")
	@AttendanceItemValue(type = ValueType.DOUBLE)
	/** 合計日数: 勤怠月間日数 + 日数: 勤怠月間日数 */
	private Double totalDays;
	
	@AttendanceItemLayout(jpPropertyName = "平均時刻", layout = "C")
	@AttendanceItemValue(type = ValueType.INTEGER)
	/** 平均時刻: 勤怠月間時間 + 平均時間: 勤怠月間時間 */
	private Integer averageTime;
}
