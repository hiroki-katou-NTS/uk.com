package nts.uk.ctx.at.record.app.find.dailyperform.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.divergencetimeofdaily.DivergenceTime;
import nts.uk.ctx.at.shared.app.util.attendanceitem.annotation.AttendanceItemLayout;
import nts.uk.ctx.at.shared.app.util.attendanceitem.annotation.AttendanceItemValue;
import nts.uk.ctx.at.shared.app.util.attendanceitem.type.ValueType;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;

/** 乖離時間 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DivergenceTimeDto {

	/** 乖離時間: 勤怠時間 */
	@AttendanceItemLayout(layout = "A", jpPropertyName = "乖離時間")
	@AttendanceItemValue(itemId = { 436, 441, 446, 451, 456 }, type = ValueType.INTEGER)
	private Integer divergenceTime;

	/** 控除時間: 勤怠時間 */
	@AttendanceItemLayout(layout = "B", jpPropertyName = "控除時間")
	@AttendanceItemValue(itemId = { 437, 442, 447, 452, 457 }, type = ValueType.INTEGER)
	private Integer deductionTime;

	/** 乖離理由コード: 乖離理由コード */
	@AttendanceItemLayout(layout = "C", jpPropertyName = "乖離理由コード")
	@AttendanceItemValue(itemId = { 438, 443, 448, 453, 458 })
	private String divergenceReasonCode;

	/** 乖離理由: 乖離理由 */
	@AttendanceItemLayout(layout = "D", jpPropertyName = "乖離理由")
	@AttendanceItemValue(itemId = { 439, 444, 449, 454, 459 })
	private String divergenceReason;

	/** 控除後乖離時間: 勤怠時間 */
	@AttendanceItemLayout(layout = "E", jpPropertyName = "控除後乖離時間")
	@AttendanceItemValue(itemId = { 440, 445, 450, 455, 460 }, type = ValueType.INTEGER)
	private Integer divergenceTimeAfterDeduction;

	/** 乖離時間NO: 乖離時間NO */
	private Integer divergenceTimeNo;
	
	public static DivergenceTimeDto fromDivergenceTime(DivergenceTime domain){
		return domain == null ? null : new DivergenceTimeDto(
				getAttendanceTime(domain.getDivTime()), 
				getAttendanceTime(domain.getDeductionTime()),
				domain.getDivResonCode() == null ? null : domain.getDivResonCode().v(), 
				domain.getDivReason() == null ? null : domain.getDivReason().v(), 
				getAttendanceTime(domain.getDivTimeAfterDeduction()), 
				domain.getDivTimeId());
	}

	private static int getAttendanceTime(AttendanceTime domain) {
		return domain == null ? null : domain.valueAsMinutes();
	}
}
