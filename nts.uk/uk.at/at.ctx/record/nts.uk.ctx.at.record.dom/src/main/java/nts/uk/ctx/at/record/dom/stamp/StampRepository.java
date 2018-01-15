package nts.uk.ctx.at.record.dom.stamp;

import java.util.List;

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
	List<StampItem> findByDate(String companyId, String cardNumber, String startDate, String endDate);
	
	void updateStampItem(StampItem stampItem);
}
