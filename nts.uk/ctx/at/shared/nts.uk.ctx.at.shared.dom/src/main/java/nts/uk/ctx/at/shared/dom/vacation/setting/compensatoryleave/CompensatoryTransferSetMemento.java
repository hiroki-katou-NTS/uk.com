/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave;

/**
 * The Interface CompensatoryTransferSetMemento.
 */
public interface CompensatoryTransferSetMemento {
	
	/**
	 * Sets the certain time.
	 *
	 * @param certainTime the new certain time
	 */
	void setCertainTime(OneDayTime certainTime);
	
	/**
	 * Sets the use division.
	 *
	 * @param useDivision the new use division
	 */
	void setUseDivision(boolean useDivision);
	
	/**
	 * Sets the design time.
	 *
	 * @param designTime the new design time
	 */
	void setDesignTime(DesignTime designTime);
	
	/**
	 * Sets the transfer division.
	 *
	 * @param transferDivision the new transfer division
	 */
	void setTransferDivision(TransferSettingDivision transferDivision); 
}
