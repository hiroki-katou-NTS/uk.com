/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.vacation.setting.compensatoryleave.find.dto;

import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensatoryOccurrenceDivision;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensatoryTransferSetMemento;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.OneDayTime;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.TransferSettingDivision;

public class CompensatoryTransferSettingDto implements CompensatoryTransferSetMemento {

	/** The certain time. */
	public long certainTime;

	/** The use division. */
	public boolean useDivision;

	/** The one day time. */
	public long oneDayTime;

	/** The half day time. */
	public long halfDayTime;

	/** The transfer division. */
	public TransferSettingDivision transferDivision;
	
	/** The compensatory occurrence division. */
	public CompensatoryOccurrenceDivision compensatoryOccurrenceDivision;

	@Override
	public void setCertainTime(OneDayTime certainTime) {
		this.certainTime = certainTime.v();
	}

	@Override
	public void setUseDivision(boolean useDivision) {
		this.useDivision = useDivision;
	}

	@Override
	public void setOneDayTime(OneDayTime oneDayTime) {
		this.oneDayTime = oneDayTime.v();
	}

	@Override
	public void setHalfDayTime(OneDayTime halfDayTime) {
		this.halfDayTime = halfDayTime.v();
	}

	@Override
	public void setTransferDivision(TransferSettingDivision transferDivision) {
		this.transferDivision = transferDivision;
	}

	@Override
	public void setCompensatoryOccurrenceDivision(CompensatoryOccurrenceDivision compensatoryOccurrenceDivision) {
		this.compensatoryOccurrenceDivision = compensatoryOccurrenceDivision;
	}

}
