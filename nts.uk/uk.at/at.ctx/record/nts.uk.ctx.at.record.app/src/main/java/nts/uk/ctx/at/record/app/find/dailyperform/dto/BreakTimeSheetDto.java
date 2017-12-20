package nts.uk.ctx.at.record.app.find.dailyperform.dto;

import lombok.Data;

/** 休憩時間帯 */
@Data
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
	private Integer BreakFrameNo;
}
