/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.common;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;

/**
 * The Class GraceTimeSetting.
 */
// 猶予時間設定
@Getter
public class GraceTimeSetting extends DomainObject {

	/** The include working hour. */
	// 就業時間に含める
	private boolean includeWorkingHour;

	/** The grace time. */
	// 猶予時間
	private LateEarlyGraceTime graceTime;

	/**
	 * Instantiates a new grace time setting.
	 *
	 * @param includeWorkingHour
	 *            the include working hour
	 * @param graceTime
	 *            the grace time
	 */
	public GraceTimeSetting(boolean includeWorkingHour, LateEarlyGraceTime graceTime) {
		super();
		this.includeWorkingHour = includeWorkingHour;
		this.graceTime = graceTime;
	}

	/**
	 * Instantiates a new grace time setting.
	 *
	 * @param memento
	 *            the memento
	 */
	public GraceTimeSetting(GraceTimeSettingGetMemento memento) {
		this.includeWorkingHour = memento.getIncludeWorkingHour();
		this.graceTime = memento.getGraceTime();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento
	 *            the memento
	 */
	public void saveToMemento(GraceTimeSettingSetMemento memento) {
		memento.setIncludeWorkingHour(this.includeWorkingHour);
		memento.setGraceTime(this.graceTime);
	}
	
	/**
	 * 猶予時間が0：00かどうか判断する
	 * @return　0：00の場合：true　0：00でない場合：false
	 */
	public boolean isZero() {
		return this.graceTime.v() == 0;
	}

}
