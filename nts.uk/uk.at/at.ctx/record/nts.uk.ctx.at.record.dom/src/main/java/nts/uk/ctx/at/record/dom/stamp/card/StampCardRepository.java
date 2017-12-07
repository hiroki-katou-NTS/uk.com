package nts.uk.ctx.at.record.dom.stamp.card;

import java.util.List;

public interface StampCardRepository {
	/**
	 *  Get List Card by Person ID
	 * @param employeeID
	 * @return
	 */
	List<StampCardItem> findByPersonID(String employeeID);
	/**
	 * Get List Card by List employee ID
	 * @param lstEmployeeID
	 * @return
	 */
	List<StampCardItem> findByListPersonID(List<String> lstEmployeeID);
	
}
