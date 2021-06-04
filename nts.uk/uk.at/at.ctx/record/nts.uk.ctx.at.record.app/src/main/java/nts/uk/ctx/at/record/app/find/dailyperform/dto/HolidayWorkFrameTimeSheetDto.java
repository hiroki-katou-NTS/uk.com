package nts.uk.ctx.at.record.app.find.dailyperform.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/** 休出枠時間帯 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HolidayWorkFrameTimeSheetDto {

	/** 時間帯: 計算用時間帯 */
//	@AttendanceItemLayout(layout = "A")
	private TimeSpanForCalcDto timeSheet;

	/** 休出枠NO: 休出枠NO */
//	@AttendanceItemLayout(layout = "B")
//	@AttendanceItemValue(itemId = -1, type = ValueType.INTEGER)
	private Integer holidayWorkFrameNo;
	
	//【追加予定】計算休出時間
	private Integer hdTimeCalc;
	
	//【追加予定】計算振替休出時間
	private Integer tranferTimeCalc;
	
	@Override
	public HolidayWorkFrameTimeSheetDto clone() {
		return new HolidayWorkFrameTimeSheetDto(timeSheet == null ? null : timeSheet.clone(), holidayWorkFrameNo, hdTimeCalc, tranferTimeCalc);
	}
}
