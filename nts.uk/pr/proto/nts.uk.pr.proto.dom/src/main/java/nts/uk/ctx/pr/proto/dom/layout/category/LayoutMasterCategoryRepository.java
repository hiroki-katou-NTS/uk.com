package nts.uk.ctx.pr.proto.dom.layout.category;

import java.util.List;

public interface LayoutMasterCategoryRepository {

	/**
	 * add LayoutMasterCategory
	 * 
	 * @param layoutMasterCategory
	 */

	void add(LayoutMasterCategory layoutMasterCategory);

	/**
	 * update LayoutMasterCategory
	 * 
	 * @param layoutMasterCategory
	 */
	void update(LayoutMasterCategory layoutMasterCategory);

	/**
	 * delete a LayoutMasterCategory
	 * 
	 * @param companyCode
	 * @param layoutCode
	 * @param startYm
	 */
	void remove(String companyCode, String layoutCode, int startYm);

	/**
	 * get Categories
	 * 
	 * @param companyCode
	 * @param layoutCode
	 * @param startYm
	 * @return List Categories
	 */
	List<LayoutMasterCategory> getCategories(String companyCode, String layoutCode, int startYm);
}
