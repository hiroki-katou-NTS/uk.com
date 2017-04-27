/**
 * author hieult
 */
package nts.uk.ctx.sys.portal.dom.titlemenu;

import java.util.List;
import java.util.Optional;

public interface TitleMenuRepository {
	/**
	 * Find All
	 * 
	 * @param companyID
	 * @return List
	 */
	List<TitleMenu> findAll(String companyID);

	/**
	 * Find by Code
	 * 
	 * @param companyID
	 * @param toppagePartID
	 * @return Optional
	 */

	Optional<TitleMenu> findByCode(String companyID, String titleMenuCD);
	
	/**
	 * Add
	 * @param companyID
	 * @param titleMenuCD
	 */
	void add (String companyID , String titleMenuCD);
	
	/**
	 * Update
	 * @param companyID
	 * @param titleMenuCD
	 */
	void update (String companyID , String titleMenuCD);
	
	/**
	 * Remove
	 * @param companyID
	 * @param titleMenuCD
	 */
	void remove (String companyID , String titleMenuCD);
}
