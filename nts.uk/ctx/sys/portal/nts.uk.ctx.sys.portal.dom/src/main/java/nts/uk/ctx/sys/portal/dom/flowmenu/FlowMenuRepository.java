/**
 * author hieult
 */
package nts.uk.ctx.sys.portal.dom.flowmenu;

import java.util.List;
import java.util.Optional;

public interface FlowMenuRepository {

	/**
	 * Find All
	 * 
	 * @param companyID
	 * @return
	 */
	List<FlowMenuTopPagePart> findAll(String companyID);

	/**
	 * Find by code
	 * 
	 * @param companyID
	 * @param toppagePartID
	 * @return
	 */
	Optional<FlowMenuTopPagePart> findByCode(String companyID, String toppagePartID);

	/**
	 * Add
	 * 
	 * @param flow
	 * 
	 */
	void add(FlowMenu flow);

	/**
	 * Update
	 * 
	 * @param companyID
	 * @param toppagePartID
	 */
	void update(FlowMenu flow);

	/**
	 * Remove
	 * 
	 * @param companyID
	 * @param toppagePartID
	 */
	void remove(String companyID, String toppagePartID);

}
