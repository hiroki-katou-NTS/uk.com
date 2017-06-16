/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave2;

import lombok.Getter;

@Getter
public class TransferSetting {
	
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
	
	/**
	 * Instantiates a new compensatory transfer setting.
	 *
	 * @param menento the menento
	 */
	public TransferSetting(TransferSettingGetMemento memento) {
		this.certainTime = memento.getCertainTime();
		this.useDivision = memento.isUseDivision();
		this.transferDivision = memento.getTransferDivision();
		this.oneDayTime = memento.getOneDayTime();
		this.halfDayTime = memento.getHalfDayTime();
	}
	
	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(TransferSettingSetMemento memento) {
		memento.setCertainTime(this.certainTime);
		memento.setOneDayTime(this.oneDayTime);
		memento.setHalfDayTime(this.halfDayTime);
		memento.setTransferDivision(this.transferDivision);
		memento.setUseDivision(this.useDivision);
	}
}
