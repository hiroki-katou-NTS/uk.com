/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.pub.shortworktime;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import nts.arc.time.calendar.period.DatePeriod;

/**
 * The Class ShShortWorkTimeExport.
 */
// 社員の短時間勤務
@Getter
@Builder
public class ShShortWorkTimeExport {

	 /** The employee id. */
	// 社員ID
	private String employeeId;
	 
	 /** The period. */
 	// 期間
	private DatePeriod period;
	
	/** The child care atr. */
	// 育児介護区分
	private int childCareAtr;
	 
 	/** The time slot. */
 	// 時間帯
	private List<ShShortChildCareFrameExport> lstTimeSlot;
}
