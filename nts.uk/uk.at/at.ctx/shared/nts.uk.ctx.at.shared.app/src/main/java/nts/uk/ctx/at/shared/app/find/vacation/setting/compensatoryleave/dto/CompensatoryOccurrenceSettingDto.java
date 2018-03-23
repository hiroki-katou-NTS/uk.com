/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.vacation.setting.compensatoryleave.dto;

import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensatoryOccurrenceDivision;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensatoryOccurrenceSettingSetMemento;
import nts.uk.ctx.at.shared.dom.worktime.common.SubHolTransferSet;

/**
 * The Class CompensatoryOccurrenceSettingDto.
 */
public class CompensatoryOccurrenceSettingDto implements CompensatoryOccurrenceSettingSetMemento {

	/** The occurrence type. */
	public Integer occurrenceType;
	
	/** The transfer setting. */
	public CompensatoryTransferSettingDto transferSetting;

	@Override
	public void setOccurrenceType(CompensatoryOccurrenceDivision occurrenceType) {
		this.occurrenceType = occurrenceType.value;
	}

	@Override
	public void setTransferSetting(SubHolTransferSet transferSetting) {
		CompensatoryTransferSettingDto transfer = new CompensatoryTransferSettingDto();
		transferSetting.saveToMemento(transfer);
		this.transferSetting = transfer;
	}

	
}
