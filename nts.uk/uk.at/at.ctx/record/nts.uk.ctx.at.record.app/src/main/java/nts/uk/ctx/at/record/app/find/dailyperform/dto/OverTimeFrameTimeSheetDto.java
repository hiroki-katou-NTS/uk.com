package nts.uk.ctx.at.record.app.find.dailyperform.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/** 残業枠時間帯 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OverTimeFrameTimeSheetDto {

	/** 時間帯: 計算用時間帯 */
//	@AttendanceItemLayout(layout = "A")
	private TimeSpanForCalcDto timeSheet;

	/** 残業枠NO: 残業枠NO */
//	@AttendanceItemLayout(layout = "B")
//	@AttendanceItemValue(itemId = -1, type = ValueType.INTEGER)
	private Integer overtimeFrameNo;
	
	@Override
	public OverTimeFrameTimeSheetDto clone() {
		return new OverTimeFrameTimeSheetDto(timeSheet == null ? null : timeSheet.clone(), overtimeFrameNo);
	}
}
