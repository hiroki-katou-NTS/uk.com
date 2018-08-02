/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.dom.stamp;

import java.util.List;

import nts.arc.time.GeneralDateTime;



public interface StampRepository {
	/**
	 * Get list StampItem by List Card Number.
	 * @param lstCardNumber
	 * @return StampItem list
	 */
	List<StampItem> findByListCardNo(List<String> lstCardNumber);
	
	// get List Stamp by Card Number and Day of Work
	//List<StampItem> findByEmployeeCode(String companyId, String cardNumber, String startDate, String endDate);
	List<StampItem> findByEmployeeID(String companyId, List<String> stampCard,String startDate, String endDate);
	
	// get List Stamp by Date
	List<StampItem> findByDate(String cardNumber, GeneralDateTime startDate, GeneralDateTime endDate);
	
	void updateStampItem(StampItem stampItem);
	
	/**
	 * @param companyId
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	List<StampItem> findByDateCompany(String companyId,GeneralDateTime startDate ,GeneralDateTime endDate);
	
	/**
	 * Find by cards date.
	 *
	 * @param companyId the company id
	 * @param lstCardNumber the lst card number
	 * @param startDate the start date
	 * @param endDate the end date
	 * @return the list
	 */
	List<StampItem> findByCardsDate(String companyId, List<String> lstCardNumber, GeneralDateTime startDate ,GeneralDateTime endDate);
	
	/**
	 * Find by employee I D fix.
	 *
	 * @param companyId the company id
	 * @param stampCards the stamp cards
	 * @param startDate the start date
	 * @param endDate the end date
	 * @return the list
	 */
	List<StampItem> findByEmployeeID_Fix(String companyId, List<String> stampCards, GeneralDateTime startDate, GeneralDateTime endDate);
}
