package nts.uk.ctx.at.shared.dom.category.repository;

import java.util.List;
import java.util.Optional;

import nts.uk.ctx.at.shared.dom.category.HoriTotalCategory;
/**
 * 
 * @author yennth
 *
 */
public interface HoriTotalCategoryRepository {
	/**
	 * get all data
	 * @param companyId
	 * @return
	 */
	List<HoriTotalCategory> findAll(String companyId);
	
	/**
	 * update a item 
	 * @param aggregateCategory
	 */
	void updae(HoriTotalCategory aggregateCategory);
	
	/**
	 * insert a item
	 * @param aggregateCategory
	 */
	void insert(HoriTotalCategory aggregateCategory);
	
	/**
	 * delete a item
	 * @param companyId
	 * @param categoryCode
	 */
	void delete(String companyId, String categoryCode);
	
	/**
	 * @param company
	 * @param categoryCode
	 * @return
	 */
	Optional<HoriTotalCategory> findByCode(String company, String categoryCode);
}
