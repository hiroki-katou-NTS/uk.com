/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.statutory.worktime.shared;

import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.DomainObject;

/**
 * 通常勤務労働時間設定.
 */
@Getter
@Setter
public class NormalSetting extends DomainObject {
	
	/** 法定労働時間設定. */
	private WorkingTimeSetting statutorySetting;

	/** 週開始. */
	private WeekStart weekStart;
	
	/**
	 * 法定内残業にできる時間を求める（一日分）
	 * @param workTime 就業時間（法定外用）
	 * @return
	 */
	public int calculateDailyLimitOfLegalOverworkTime(int workTime) {
		return this.statutorySetting.getDaily().v() - workTime;
	}
}