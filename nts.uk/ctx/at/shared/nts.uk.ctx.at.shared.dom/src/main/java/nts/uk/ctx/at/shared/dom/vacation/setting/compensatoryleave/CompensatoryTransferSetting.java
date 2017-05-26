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
	
	/** The design time. */
	private DesignTime designTime;
	
	/** The transfer division. */
	private TransferSettingDivision transferDivision; 
	
	/**
	 * Instantiates a new compensatory transfer setting.
	 *
	 * @param menento the menento
	 */
	public CompensatoryTransferSetting(CompensatoryTransferGetMenento menento) {
		this.certainTime = menento.getCertainTime();
		this.useDivision = menento.getUseDivision();
		this.transferDivision = menento.getTransferDivision();
		this.designTime = menento.getDesignTime();
	}
	
	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(CompensatoryTransferSetMemento memento) {
		memento.setCertainTime(this.certainTime);
		memento.setDesignTime(this.designTime);
		memento.setTransferDivision(this.transferDivision);
		memento.setUseDivision(this.useDivision);
	}
}
