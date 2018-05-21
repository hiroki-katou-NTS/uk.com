package nts.uk.ctx.at.record.dom.stamp.card;

import java.util.List;

public interface StampCardtemRepository {
	
	//rename file StampCardRepository -> StampCardtemRepository
	
	/**
	 *  Get List Card by Person ID
	 * @param employeeID
	 * @return
	 */
	List<StampCardItem> findByEmployeeID(String employeeID);
	/**
	 * Get List Card by List employee ID
	 * @param lstEmployeeID
	 * @return
	 */
	List<StampCardItem> findByListEmployeeID(List<String> lstEmployeeID);
	
}
