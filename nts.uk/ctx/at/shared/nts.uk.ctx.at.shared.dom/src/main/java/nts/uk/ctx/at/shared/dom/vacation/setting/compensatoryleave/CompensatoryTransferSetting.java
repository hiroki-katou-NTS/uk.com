/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;


/**
 * The Class CompensatoryTransferSetting.
 */
@Getter
public class CompensatoryTransferSetting extends DomainObject {

	/** The certain time. */
	private OneDayTime certainTime;
	
	/** The use division. */
	private boolean useDivision;
	
	/** The one day time. */
	private OneDayTime oneDayTime;
	
	/** The half day time. */
	private OneDayTime halfDayTime;
	
	/** The transfer division. */
	private TransferSettingDivision transferDivision;
	
	/** The compensatory occurrence division. */
	private CompensatoryOccurrenceDivision compensatoryOccurrenceDivision;
	
	/**
	 * Instantiates a new compensatory transfer setting.
	 *
	 * @param menento the menento
	 */
	public CompensatoryTransferSetting(CompensatoryTransferGetMemento memento) {
		this.certainTime = memento.getCertainTime();
		this.useDivision = memento.getUseDivision();
		this.transferDivision = memento.getTransferDivision();
		this.oneDayTime = memento.getOneDayTime();
		this.halfDayTime = memento.getHalfDayTime();
		this.compensatoryOccurrenceDivision = memento.getCompensatoryOccurrenceDivision();
	}
	
	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(CompensatoryTransferSetMemento memento) {
		memento.setCertainTime(this.certainTime);
		memento.setOneDayTime(this.oneDayTime);
		memento.setHalfDayTime(this.halfDayTime);
		memento.setTransferDivision(this.transferDivision);
		memento.setUseDivision(this.useDivision);
		memento.setCompensatoryOccurrenceDivision(this.compensatoryOccurrenceDivision);
	}
}
