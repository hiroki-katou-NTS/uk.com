package nts.uk.ctx.at.record.app.find.dailyperform.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.attendance.util.ItemConst;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ValueType;

/** 休出枠時間 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HolidayWorkFrameTimeDto implements ItemConst {

	/** 休出時間: 計算付き時間 */
	@AttendanceItemLayout(layout = LAYOUT_A, jpPropertyName = TIME)
	private CalcAttachTimeDto holidayWorkTime;

	/** 振替時間: 計算付き時間 */
	@AttendanceItemLayout(layout = LAYOUT_B, jpPropertyName = TRANSFER)
	private CalcAttachTimeDto transferTime;

	/** 事前申請時間: 勤怠時間 */
	@AttendanceItemLayout(layout = LAYOUT_B, jpPropertyName = BEFOR_APPLICATION)
	@AttendanceItemValue(type = ValueType.TIME)
	private Integer beforeApplicationTime;

	/** 休出枠NO: 休出枠NO */
	// @AttendanceItemLayout(layout = "D")
	// @AttendanceItemValue( type = ValueType.INTEGER)
	private Integer no;
	
	@Override
	public HolidayWorkFrameTimeDto clone() {
		return new HolidayWorkFrameTimeDto(holidayWorkTime == null ? null : holidayWorkTime.clone(), 
											transferTime == null ? null : transferTime.clone(),
											beforeApplicationTime, no);
	}
}
