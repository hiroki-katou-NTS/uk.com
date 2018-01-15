/**
 * 
 */
package nts.uk.ctx.bs.employee.dom.classification.affiliate_ver1;

import java.util.Optional;

/**
 * @author danpv
 *
 */
public interface AffClassHistItemRepository_ver1 {
	
	/**
	 * get AffClassHistItem_ver1 by history id
	 * @param historyId
	 * @return
	 */
	Optional<AffClassHistItem_ver1> getByHistoryId(String historyId);
	
	/**
	 * add an AffClassHistItem_ver1
	 * @param item
	 */
	void add(AffClassHistItem_ver1 item); 
	
	/**
	 * update an AffClassHistItem_ver1
	 * @param item
	 */
	void update(AffClassHistItem_ver1 item); 
	
	/**
	 * @param historyId
	 */
	void delete(String historyId); 

}
