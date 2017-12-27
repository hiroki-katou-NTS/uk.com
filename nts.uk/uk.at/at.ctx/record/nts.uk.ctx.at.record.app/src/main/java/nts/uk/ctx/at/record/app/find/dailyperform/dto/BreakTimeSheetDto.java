package nts.uk.ctx.at.record.app.find.dailyperform.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/** 休憩時間帯 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BreakTimeSheetDto {

	/** 開始: 勤怠打刻 */
//	@AttendanceItemLayout(layout = "A")
//	@AttendanceItemValue(itemId = -1, type = ValueType.INTEGER)
	private Integer start;

	/** 終了: 勤怠打刻 */
//	@AttendanceItemLayout(layout = "B")
//	@AttendanceItemValue(itemId = -1, type = ValueType.INTEGER)
	private Integer end;

	/** 休憩時間: 勤怠打刻 */
//	@AttendanceItemLayout(layout = "C")
//	@AttendanceItemValue(itemId = -1, type = ValueType.INTEGER)
	private Integer breakTime;

	/** 休憩枠NO: 休憩枠NO */
//	@AttendanceItemLayout(layout = "D")
//	@AttendanceItemValue(itemId = -1, type = ValueType.INTEGER)
	private Integer breakFrameNo;
}
