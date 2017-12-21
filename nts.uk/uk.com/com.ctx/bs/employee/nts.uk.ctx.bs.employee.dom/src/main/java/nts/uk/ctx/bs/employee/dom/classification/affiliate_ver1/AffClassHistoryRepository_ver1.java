/**
 * 
 */
package nts.uk.ctx.bs.employee.dom.classification.affiliate_ver1;

import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.uk.shr.com.history.DateHistoryItem;

/**
 * @author danpv
 * @author hop.nt
 *
 */
public interface AffClassHistoryRepository_ver1 {
	
	/**
	 * return historyDomain with one period
	 * @param historyId
	 * @return
	 */
	Optional<DateHistoryItem> getByHistoryId(String historyId);
	
	/**
	 * get with employeeId and standardDate
	 * @param employeeId
	 * @param standardDate
	 * @return
	 */
	Optional<DateHistoryItem> getByEmpIdAndStandardDate(String employeeId, GeneralDate standardDate);
	
	/**
	 * return historyDomain with periods
	 * @param employeeId
	 * @return
	 */
	Optional<AffClassHistory_ver1> getByEmployeeId(String cid, String employeeId);
	
	/**
	 * return historyDomain with periods descending
	 * @param cid
	 * @param employeeId
	 * @return
	 */
	Optional<AffClassHistory_ver1> getByEmployeeIdDesc(String cid, String employeeId);
	
	/**
	 * add domain history
	 * @param history
	 * @author hop.nt
	 */
	void add(String cid, String sid, DateHistoryItem itemToBeAdded);
	
	/**
	 * update domain history
	 * @param history
	 * @author hop.nt
	 */
	void update(DateHistoryItem item);
	
	/**
	 * delete domain history
	 * @param histId
	 * @author hop.nt
	 */
	void delete(String histId);

}
