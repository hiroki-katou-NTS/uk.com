/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave;

/**
 * The Interface TransferSettingGetMemento.
 */
public interface TransferSettingGetMemento {
	 
 	/**
 	 * Gets the certain time.
 	 *
 	 * @return the certain time
 	 */
 	OneDayTime getCertainTime();

     /**
      * Checks if is use division.
      *
      * @return true, if is use division
      */
     boolean isUseDivision();

     /**
      * Gets the one day time.
      *
      * @return the one day time
      */
     OneDayTime getOneDayTime(); 

     /**
      * Gets the half day time.
      *
      * @return the half day time
      */
     OneDayTime getHalfDayTime();

     /**
      * Gets the transfer division.
      *
      * @return the transfer division
      */
     TransferSettingDivision getTransferDivision();
}
