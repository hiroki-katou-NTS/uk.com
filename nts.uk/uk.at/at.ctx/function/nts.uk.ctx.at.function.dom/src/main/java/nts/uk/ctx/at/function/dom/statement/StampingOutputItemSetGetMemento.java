/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.function.dom.statement;

import nts.uk.ctx.at.shared.dom.common.CompanyId;

/**
 * The Interface StampingOutputItemSetGetMemento.
 */
public interface StampingOutputItemSetGetMemento {
	
	/**
	 * Gets the company ID.
	 *
	 * @return the company ID
	 */
	CompanyId getCompanyID();
	
	/**
	 * Gets the stamp output set code.
	 *
	 * @return the stamp output set code
	 */
	StampOutputSettingCode getStampOutputSetCode();
	
	/**
	 * Gets the stamp output set name.
	 *
	 * @return the stamp output set name
	 */
	StampOutputSettingName getStampOutputSetName();
	
	/**
	 * Gets the output emboss method.
	 *
	 * @return the output emboss method
	 */
	boolean getOutputEmbossMethod();
	
	/**
	 * Gets the output work hours.
	 *
	 * @return the output work hours
	 */
	boolean getOutputWorkHours();
	
	/**
	 * Gets the output set location.
	 *
	 * @return the output set location
	 */
	boolean getOutputSetLocation();
	
	/**
	 * Gets the output pos infor.
	 *
	 * @return the output pos infor
	 */
	boolean getOutputPosInfor();
	
	/**
	 * Gets the output OT.
	 *
	 * @return the output OT
	 */
	boolean getOutputOT();
	
	/**
	 * Gets the output night time.
	 *
	 * @return the output night time
	 */
	boolean getOutputNightTime();
	
	/**
	 * Gets the output support card.
	 *
	 * @return the output support card
	 */
	boolean getOutputSupportCard();
}
