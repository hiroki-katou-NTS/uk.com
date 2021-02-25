/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting;

/**
 * The Interface AutoCalOvertimeSettingGetMemento.
 */
public interface AutoCalOvertimeSettingGetMemento<T> {
	
	/**
	 * Gets the early ot time.
	 *
	 * @return the early ot time
	 */
	AutoCalSetting getEarlyOtTime();
	
	/**
	 * Gets the early mid ot time.
	 *
	 * @return the early mid ot time
	 */
	AutoCalSetting getEarlyMidOtTime();
	
	/**
	 * Gets the normal ot time.
	 *
	 * @return the normal ot time
	 */
	AutoCalSetting getNormalOtTime();
	
	/**
	 * Gets the normal mid ot time.
	 *
	 * @return the normal mid ot time
	 */
	AutoCalSetting getNormalMidOtTime();
	
	/**
	 * Gets the legal ot time.
	 *
	 * @return the legal ot time
	 */
	AutoCalSetting getLegalOtTime();
	
	/**
	 * Gets the legal mid ot time.
	 *
	 * @return the legal mid ot time
	 */
	AutoCalSetting getLegalMidOtTime();
	
}
