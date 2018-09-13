package nts.uk.ctx.at.record.app.find.dailyperform.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.attendance.util.ItemConst;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ValueType;

/** 休憩時間帯 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BreakTimeSheetDto implements ItemConst {

	/** 開始: 勤怠打刻 */
	@AttendanceItemLayout(layout = LAYOUT_A, jpPropertyName = START)
	@AttendanceItemValue(type = ValueType.TIME_WITH_DAY)
	private Integer start;

	/** 終了: 勤怠打刻 */
	@AttendanceItemLayout(layout = LAYOUT_B, jpPropertyName = END)
	@AttendanceItemValue(type = ValueType.TIME_WITH_DAY)
	private Integer end;

	/** 休憩時間: 勤怠打刻 */
//	@AttendanceItemLayout(layout = "C")
//	@AttendanceItemValue(itemId = -1, type = ValueType.INTEGER)
	private Integer breakTime;

	/** 休憩枠NO: 休憩枠NO */
//	@AttendanceItemLayout(layout = "D")
//	@AttendanceItemValue(itemId = -1, type = ValueType.INTEGER)
	private Integer no;
	
	@Override
	public BreakTimeSheetDto clone() {
		return new BreakTimeSheetDto(start, end, breakTime, no);
	}
}
