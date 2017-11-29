/**
 * 
 */
package nts.uk.ctx.bs.employee.dom.classification.affiliate_ver1;

import java.util.Optional;

import nts.uk.shr.com.history.DateHistoryItem;

/**
 * @author danpv
 *
 */
public interface AffClassHistoryRepository_ver1 {
	
	/**
	 * return historyDomain with one period
	 * @param historyId
	 * @return
	 */
	Optional<AffClassHistory_ver1> getByHistoryId(String historyId);
	
	/**
	 * return historyDomain with periods
	 * @param employeeId
	 * @return
	 */
	Optional<AffClassHistory_ver1> getByEmployeeId(String employeeId);
	
	/**
	 * add domain history
	 * add last item and update before item
	 * @param history
	 */
	void add(AffClassHistory_ver1 history);
	
	/**
	 * update domain history
	 * update item and nearly item
	 * @param history
	 */
	void update(AffClassHistory_ver1 history, DateHistoryItem item);

}
