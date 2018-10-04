package nts.uk.ctx.at.record.app.find.dailyperform.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.attendance.util.ItemConst;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemLayout;

/** 日別実績の遅刻時間 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LateTimeDto implements ItemConst {

	/** 遅刻時間: 計算付き時間 */
	@AttendanceItemLayout(layout = LAYOUT_A, jpPropertyName = TIME)
	private CalcAttachTimeDto lateTime;

	/** 遅刻控除時間: 計算付き時間 */
	@AttendanceItemLayout(layout = LAYOUT_B, jpPropertyName = DEDUCTION)
	private CalcAttachTimeDto lateDeductionTime;

	/** 休暇使用時間/休憩使用時間: 日別実績の時間年休使用時間 */
	@AttendanceItemLayout(layout = LAYOUT_C, jpPropertyName = HOLIDAY + USAGE)
	private ValicationUseDto breakUse;

	/** インターバル免除時間/インターバル時間: インターバル免除時間 */
	// @AttendanceItemLayout(layout = "D")
	// @AttendanceItemValue(itemId = -1, type = ValueType.INTEGER)
	private Integer intervalExemptionTime;

	/** 勤務NO/勤務回数 */
	// @AttendanceItemLayout(layout = "E")
	// @AttendanceItemValue(itemId = -1, type = ValueType.INTEGER)
	private Integer no;
	
	@Override
	public LateTimeDto clone() {
		return new LateTimeDto(lateTime == null ? null : lateTime.clone(), lateDeductionTime == null ? null : lateDeductionTime.clone(),
				breakUse == null ? null : breakUse.clone(), intervalExemptionTime, no);
	}
}
