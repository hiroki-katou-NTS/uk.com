package nts.uk.ctx.at.shared.app.find.attendanceitem.daily.dto;

import lombok.Data;

/** 乖離時間 */
@Data
public class DivergenceTimeDto {

	/** 乖離理由コード: 乖離理由コード */
	private String divergenceReasonCode;

	/** 乖離理由: 乖離理由 */
	private String divergenceReason;

	/** 乖離時間NO: 乖離時間NO */
	private int divergenceTimeNo;

	/** 乖離時間: 勤怠時間 */
	private Integer  divergenceTime;

	/** 控除時間: 勤怠時間 */
	private Integer deductionTime;

	/** 控除後乖離時間: 勤怠時間 */
	private Integer divergenceTimeAfterDeduction;
}
