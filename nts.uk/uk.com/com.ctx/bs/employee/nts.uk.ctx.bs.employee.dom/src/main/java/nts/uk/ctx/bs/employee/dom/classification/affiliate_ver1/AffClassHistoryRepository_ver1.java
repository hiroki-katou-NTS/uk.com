/**
 * 
 */
package nts.uk.ctx.bs.employee.dom.classification.affiliate_ver1;

import java.util.Optional;

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
	
	void add(AffClassHistory_ver1 history);

}
