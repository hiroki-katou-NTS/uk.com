/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.employeeinfo;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;

/**
 * The Class MonthlyPatternWorkScheduleCre.
 */
// 月間パターンによる勤務予定作成
@Getter
public class MonthlyPatternWorkScheduleCre extends DomainObject{

	/** The reference type. */
	// 勤務種類と就業時間帯の参照先
	private TimeZoneScheduledMasterAtr referenceType;

	/**
	 * Instantiates a new monthly pattern work schedule cre.
	 *
	 * @param referenceType the reference type
	 */
	public MonthlyPatternWorkScheduleCre(TimeZoneScheduledMasterAtr referenceType) {
		this.referenceType = referenceType;
	}
	
}
