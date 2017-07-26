/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.app.find.shift.pattern.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class MonthlyPatternSettingBatch.
 */
@Getter
@Setter
public class MonthlyPatternSettingBatchDto {

	/** The work type code. */
	// 勤務種類コード
	private String workTypeCode;

	/** The working code. */
	// 就業時間帯コード
	private String workingCode;
}
