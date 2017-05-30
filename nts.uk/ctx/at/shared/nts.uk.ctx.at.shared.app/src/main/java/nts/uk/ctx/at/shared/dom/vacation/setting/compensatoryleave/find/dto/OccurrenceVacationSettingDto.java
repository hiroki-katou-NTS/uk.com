/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.find.dto;

import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensatoryOccurrenceDivision;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensatoryTransferSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.OccurrenceVacationSetMemento;

public class OccurrenceVacationSettingDto implements OccurrenceVacationSetMemento {

	/** The transfer setting. */
	public CompensatoryTransferSettingDto transferSetting;

	/** The occurrence division. */
	public CompensatoryOccurrenceDivision occurrenceDivision;
	
	@Override
	public void setTransferSetting(CompensatoryTransferSetting transferSetting) {
		CompensatoryTransferSettingDto ctsd = new CompensatoryTransferSettingDto();
		transferSetting.saveToMemento(ctsd);
		this.transferSetting = ctsd;
	}

	@Override
	public void setOccurrenceDivision(CompensatoryOccurrenceDivision occurrenceDivision) {
		this.occurrenceDivision = occurrenceDivision;
	}

}
