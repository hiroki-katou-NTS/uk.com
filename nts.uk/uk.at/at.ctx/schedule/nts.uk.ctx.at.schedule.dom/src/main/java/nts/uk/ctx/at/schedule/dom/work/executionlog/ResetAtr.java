/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.work.executionlog;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;

/**
 * The Class ResetAtr.
 */
// 再設定区分
@Getter
public class ResetAtr extends DomainObject {

	/** The reset master info. */
	// マスタ情報再設定
	private Boolean resetMasterInfo;
	
	/** The reset absent holiday business. */
	// 休職休業再設定
	private Boolean resetAbsentHolidayBusines;
	
	/** The reset working hours. */
	// 就業時間帯再設定
	private Boolean resetWorkingHours;

	/** The reset time assignment. */
	// 申し送り時間再設定
	private Boolean resetTimeAssignment;

	/** The reset direct line bounce. */
	// 直行直帰再設定
	private Boolean resetDirectLineBounce;

	/** The reset time child care. */
	// 育児介護時間再設定
	private Boolean resetTimeChildCare;

}
