/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.vacation.setting.compensatoryleave.find.dto;

import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensatoryTransferSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.OccurrenceVacationSetMemento;

public class OccurrenceVacationSettingDto implements OccurrenceVacationSetMemento {

	/** The transfer setting. */
	public CompensatoryTransferSettingDto transferSettingDayOffTime;
	
	/** The transfer setting. */
	public CompensatoryTransferSettingDto transferSettingOverTime;

	@Override
	public void setTransferSettingOverTime(CompensatoryTransferSetting transferSettingOverTime) {
		CompensatoryTransferSettingDto ctsd = new CompensatoryTransferSettingDto();
		transferSettingOverTime.saveToMemento(ctsd);
		this.transferSettingOverTime = ctsd;
	}

	@Override
	public void setTransferSettingDayOffTime(CompensatoryTransferSetting transferSettingDayOffTime) {
		CompensatoryTransferSettingDto ctsd = new CompensatoryTransferSettingDto();
		transferSettingDayOffTime.saveToMemento(ctsd);
		this.transferSettingDayOffTime = ctsd;
	}

}
