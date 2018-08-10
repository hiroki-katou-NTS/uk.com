/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.function.dom.statement;

import nts.uk.ctx.at.shared.dom.common.CompanyId;

/**
 * The Interface StampingOutputItemSetSetMemento.
 */
public interface StampingOutputItemSetSetMemento {
	
	/**
	 * Sets the company ID.
	 *
	 * @param companyID the new company ID
	 */
	void setCompanyID(CompanyId companyID);
	
	/**
	 * Sets the stamp output set code.
	 *
	 * @param stampOutputSettingCode the new stamp output set code
	 */
	void setStampOutputSetCode(StampOutputSettingCode stampOutputSettingCode);
	
	/**
	 * Sets the stamp output set name.
	 *
	 * @param stampOutputSettingName the new stamp output set name
	 */
	void setStampOutputSetName(StampOutputSettingName stampOutputSettingName);
	
	/**
	 * Sets the output emboss method.
	 *
	 * @param outputEmbossMethod the new output emboss method
	 */
	void setOutputEmbossMethod(boolean outputEmbossMethod);
	
	/**
	 * Sets the output work hours.
	 *
	 * @param outputWorkHours the new output work hours
	 */
	 void setOutputWorkHours(boolean outputWorkHours);
	
	/**
	 * Sets the output set location.
	 *
	 * @param outputSetLocation the new output set location
	 */
	 void setOutputSetLocation(boolean outputSetLocation);
	
	/**
	 * Sets the output pos infor.
	 *
	 * @param outputPosInfor the new output pos infor
	 */
	 void setOutputPosInfor(boolean outputPosInfor);
	
	/**
	 * Sets the output OT.
	 *
	 * @param outputOT the new output OT
	 */
	 void setOutputOT(boolean outputOT);
	
	/**
	 * Sets the output night time.
	 *
	 * @param outputNightTime the new output night time
	 */
	 void setOutputNightTime(boolean outputNightTime);
	
	/**
	 * Sets the output support card.
	 *
	 * @param outputSupportCard the new output support card
	 */
	 void setOutputSupportCard(boolean outputSupportCard);
}
