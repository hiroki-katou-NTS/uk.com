/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.vacation.setting.sixtyhours.find.dto;

import lombok.Data;
import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;
import nts.uk.ctx.at.shared.dom.vacation.setting.SixtyHourExtra;
import nts.uk.ctx.at.shared.dom.vacation.setting.TimeDigestiveUnit;
import nts.uk.ctx.at.shared.dom.vacation.setting.sixtyhours.SixtyHourVacationSettingSetMemento;

/**
 * The Class SubstVacationSettingDto.
 */
@Data
public class SixtyHourVacationSettingDto implements SixtyHourVacationSettingSetMemento {

	/** The is manage. */
	private Integer isManage;

	/** The expiration date. */
	private Integer expirationDate;

	/** The allow prepaid leave. */
	private Integer allowPrepaidLeave;

	@Override
	public void setIsManage(ManageDistinct isManage) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setSixtyHourExtra(SixtyHourExtra sixtyHourExtra) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setDigestiveUnit(TimeDigestiveUnit digestiveUnit) {
		// TODO Auto-generated method stub

	}

}
