/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.vacation.setting.annualpaidleave.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDate;

/**
 * The Class OperationStartSetDailyPerformDto.
 */
@Getter
@Setter
@NoArgsConstructor
public class OperationStartSetDailyPerformDto {
	
	// 日別実績の運用開始日
	String operateStartDateDailyPerform;
}

