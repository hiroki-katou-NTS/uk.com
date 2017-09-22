/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.vacation.setting.sixtyhours.dto;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;
import nts.uk.ctx.at.shared.dom.vacation.setting.SixtyHourExtra;
import nts.uk.ctx.at.shared.dom.vacation.setting.TimeDigestiveUnit;
import nts.uk.ctx.at.shared.dom.vacation.setting.sixtyhours.SixtyHourVacationSettingSetMemento;

/**
 * The Class SixtyHourVacationSettingCheckDto.
 */
@Getter
@Setter
public class SixtyHourVacationSettingCheckDto implements SixtyHourVacationSettingSetMemento{

	/** The manage. */
	private Boolean manage;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.vacation.setting.sixtyhours.
	 * SixtyHourVacationSettingSetMemento#setIsManage(nts.uk.ctx.at.shared.dom.
	 * vacation.setting.ManageDistinct)
	 */
	@Override
	public void setIsManage(ManageDistinct isManage) {
		this.manage = isManage.value == ManageDistinct.YES.value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.vacation.setting.sixtyhours.
	 * SixtyHourVacationSettingSetMemento#setSixtyHourExtra(nts.uk.ctx.at.shared
	 * .dom.vacation.setting.SixtyHourExtra)
	 */
	@Override
	public void setSixtyHourExtra(SixtyHourExtra sixtyHourExtra) {
		// Nothing code

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.vacation.setting.sixtyhours.
	 * SixtyHourVacationSettingSetMemento#setDigestiveUnit(nts.uk.ctx.at.shared.
	 * dom.vacation.setting.TimeDigestiveUnit)
	 */
	@Override
	public void setDigestiveUnit(TimeDigestiveUnit digestiveUnit) {
		// Nothing code

	}
}
