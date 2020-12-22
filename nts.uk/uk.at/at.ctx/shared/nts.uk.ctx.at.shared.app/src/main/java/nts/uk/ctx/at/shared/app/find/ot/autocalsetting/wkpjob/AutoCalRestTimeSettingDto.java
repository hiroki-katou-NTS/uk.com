/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.ot.autocalsetting.wkpjob;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.AutoCalRestTimeSettingSetMemento;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.AutoCalSetting;

@Getter
@Setter
public class AutoCalRestTimeSettingDto implements AutoCalRestTimeSettingSetMemento {

	private AutoCalSettingDto restTime;

	/** The late night time. */
	// 休出深夜時間
	private AutoCalSettingDto lateNightTime;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.shift.autocalsetting.
	 * AutoCalRestTimeSettingSetMemento#setRestTime(nts.uk.ctx.at.schedule.dom.
	 * shift.autocalsetting.AutoCalSetting)
	 */
	@Override
	public void setRestTime(AutoCalSetting restTime) {
		this.restTime = new AutoCalSettingDto(restTime.getUpLimitORtSet().value, restTime.getCalAtr().value);

	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.schedule.dom.shift.autocalsetting.AutoCalRestTimeSettingSetMemento#setLateNightTime(nts.uk.ctx.at.schedule.dom.shift.autocalsetting.AutoCalSetting)
	 */
	@Override
	public void setLateNightTime(AutoCalSetting lateNightTime) {
		this.lateNightTime = new AutoCalSettingDto(lateNightTime.getUpLimitORtSet().value, lateNightTime.getCalAtr().value);

	}

}
