/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.ot.autocalsetting.wkp;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.AutoCalFlexOvertimeSettingSetMemento;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.AutoCalSetting;

/**
 * Gets the flex ot night time.
 *
 * @return the flex ot night time
 */
@Getter
@Setter
public class AutoCalFlexOvertimeSettingDto implements AutoCalFlexOvertimeSettingSetMemento {

	/** The flex ot time. */
	// フレックス超過時間
	private AutoCalSettingDto flexOtTime;


	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.shift.autocalsetting.
	 * AutoCalFlexOvertimeSettingSetMemento#setFlexOtTime(nts.uk.ctx.at.schedule
	 * .dom.shift.autocalsetting.AutoCalSetting)
	 */
	@Override
	public void setFlexOtTime(AutoCalSetting flexOtTime) {
		this.flexOtTime = new AutoCalSettingDto(flexOtTime.getUpLimitORtSet().value,
				flexOtTime.getCalAtr().value);

	}


}
