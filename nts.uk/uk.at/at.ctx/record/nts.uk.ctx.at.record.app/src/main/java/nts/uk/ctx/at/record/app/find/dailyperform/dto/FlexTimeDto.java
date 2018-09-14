package nts.uk.ctx.at.record.app.find.dailyperform.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.attendance.util.ItemConst;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ValueType;

/** フレックス時間 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FlexTimeDto implements ItemConst {

	/** フレックス時間: 計算付き時間(マイナス有り) */
	@AttendanceItemLayout(layout = LAYOUT_A, jpPropertyName = TIME)
	private CalcAttachTimeDto flexTime;

	/** 事前申請時間: 勤怠時間 */
	@AttendanceItemLayout(layout = LAYOUT_B, jpPropertyName = BEFOR_APPLICATION)
	@AttendanceItemValue(type = ValueType.TIME)
	private Integer beforeApplicationTime;
	
	@Override
	public FlexTimeDto clone() {
		return new FlexTimeDto(flexTime == null ? null : flexTime.clone(), beforeApplicationTime);
	}
}
