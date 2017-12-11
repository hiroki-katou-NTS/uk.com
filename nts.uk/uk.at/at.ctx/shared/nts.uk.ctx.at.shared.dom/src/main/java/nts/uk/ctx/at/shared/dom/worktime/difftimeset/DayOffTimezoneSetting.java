/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.difftimeset;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.worktime.common.HDWorkTimeSheetSetting;

@Getter
// 時差勤務休出時間の時間帯設定
public class DayOffTimezoneSetting extends HDWorkTimeSheetSetting {

	/** The is update start time. */
	// 開始時刻に合わせて時刻を変動させる
	private boolean isUpdateStartTime;
	
	/**
	 * Instantiates a new day off timezone setting.
	 *
	 * @param memento the memento
	 */
	public DayOffTimezoneSetting(DayOffTimezoneSettingGetMemento memento) {
		super(memento);
		this.isUpdateStartTime = memento.getIsUpdateStartTime();
	}
	
	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(DayOffTimezoneSettingSetMemento memento){
		super.saveToMemento(memento);
		memento.setIsUpdateStartTime(this.isUpdateStartTime);
	}
}
