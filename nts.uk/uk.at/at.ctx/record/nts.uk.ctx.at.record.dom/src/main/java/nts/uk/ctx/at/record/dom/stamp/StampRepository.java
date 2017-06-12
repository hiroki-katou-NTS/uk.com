package nts.uk.ctx.at.record.dom.stamp;

import java.util.List;

public interface StampRepository {
	// get List Stamp by Card Number and Day of Work
	//List<StampItem> findByEmployeeCode(String companyId, String cardNumber, String startDate, String endDate);
	List<StampItem> findByEmployeeCode(String companyId, List<String> lstCardNumber, String startDate, String endDate);
	
	// get List Stamp by Date
	List<StampItem> findByDate(String companyId, String cardNumber, String startDate, String endDate);


	
}
