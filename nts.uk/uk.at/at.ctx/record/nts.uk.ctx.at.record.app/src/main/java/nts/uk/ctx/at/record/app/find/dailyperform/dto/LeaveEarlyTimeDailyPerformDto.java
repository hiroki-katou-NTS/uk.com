package nts.uk.ctx.at.record.app.find.dailyperform.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.attendance.util.ItemConst;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemLayout;

/** 日別実績の早退時間 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LeaveEarlyTimeDailyPerformDto implements ItemConst {

	/** 早退時間: 計算付き時間 */
	@AttendanceItemLayout(layout = LAYOUT_A, jpPropertyName = TIME)
	private CalcAttachTimeDto time;

	/** 早退控除時間: 勤怠時間 */
	@AttendanceItemLayout(layout = LAYOUT_B, jpPropertyName = DEDUCTION)
	private CalcAttachTimeDto deductionTime;

	/** 休暇使用時間/休憩使用時間: 日別実績の時間休暇使用時間 */
	@AttendanceItemLayout(layout = LAYOUT_C, jpPropertyName = HOLIDAY + USAGE)
	private ValicationUseDto valicationUseTime;

	/** インターバル免除時間/インターバル時間: インターバル免除時間 */
	// @AttendanceItemLayout(layout = "D")
	// @AttendanceItemValue(itemId = -1, type = ValueType.INTEGER)
	private Integer intervalExemptionTime;

	/** 勤務NO/勤務回数: 勤務NO */
	// @AttendanceItemLayout(layout = "E")
	// @AttendanceItemValue(itemId = -1, type = ValueType.INTEGER)
	private Integer no;
	
	@Override
	public LeaveEarlyTimeDailyPerformDto clone() {
		return new LeaveEarlyTimeDailyPerformDto(time == null ? null : time.clone(),
						deductionTime == null ? null : deductionTime.clone(),
						valicationUseTime == null ? null : valicationUseTime.clone(), intervalExemptionTime, no);
	}
}
