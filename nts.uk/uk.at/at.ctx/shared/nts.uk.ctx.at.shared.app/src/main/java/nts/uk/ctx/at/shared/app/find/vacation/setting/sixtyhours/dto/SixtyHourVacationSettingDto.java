/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.vacation.setting.sixtyhours.dto;

import lombok.Data;
import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;
import nts.uk.ctx.at.shared.dom.vacation.setting.SixtyHourExtra;
import nts.uk.ctx.at.shared.dom.vacation.setting.TimeDigestiveUnit;
import nts.uk.ctx.at.shared.dom.vacation.setting.sixtyhours.SixtyHourVacationSettingSetMemento;

// TODO: Auto-generated Javadoc
/**
 * The Class SubstVacationSettingDto.
 */

/**
 * Instantiates a new sixty hour vacation setting dto.
 */
@Data
public class SixtyHourVacationSettingDto implements SixtyHourVacationSettingSetMemento {

	/** The is manage. */
	private Integer isManage;

	/** The expiration date. */
	private Integer sixtyHourExtra;

	/** The allow prepaid leave. */
	private Integer digestiveUnit;

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.vacation.setting.sixtyhours.SixtyHourVacationSettingSetMemento#setIsManage(nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct)
	 */
	@Override
	public void setIsManage(ManageDistinct isManage) {
		this.isManage = isManage.value;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.vacation.setting.sixtyhours.SixtyHourVacationSettingSetMemento#setSixtyHourExtra(nts.uk.ctx.at.shared.dom.vacation.setting.SixtyHourExtra)
	 */
	@Override
	public void setSixtyHourExtra(SixtyHourExtra sixtyHourExtra) {
		this.sixtyHourExtra = sixtyHourExtra.value;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.vacation.setting.sixtyhours.SixtyHourVacationSettingSetMemento#setDigestiveUnit(nts.uk.ctx.at.shared.dom.vacation.setting.TimeDigestiveUnit)
	 */
	@Override
	public void setDigestiveUnit(TimeDigestiveUnit digestiveUnit) {
		this.digestiveUnit = digestiveUnit.value;
	}

}
