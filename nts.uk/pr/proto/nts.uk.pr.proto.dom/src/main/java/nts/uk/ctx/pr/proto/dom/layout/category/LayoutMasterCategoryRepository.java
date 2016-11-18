package nts.uk.ctx.pr.proto.dom.layout.category;

import java.util.List;


public interface LayoutMasterCategoryRepository {

	/**
	 * add LayoutMasterCategory
	 * 
	 * @param companyCode
	 * @param layoutCode
	 * @param startYm
	 */
	void add(LayoutMasterCategory layoutMasterCategory);
	/**
	 * add list categories
	 * @param categories
	 */
	void add(List<LayoutMasterCategory> categories);
	/**
	 * update LayoutMasterCategory
	 * 
	 * @param companyCode
	 * @param stmtCode
	 * @param startYm
	 * @param categoryAtr
	 */
	void update(LayoutMasterCategory layoutMasterCategory);
	/**
	 * update list categories
	 * @param categories
	 */
	void update(List<LayoutMasterCategory> categories);
	
	/**
	 * delete a LayoutMasterCategory
	 * 
	 * @param companyCode
	 * @param stmtCode
	 * @param startYm
	 * @param categoryAtr
	 */
	void remove(String companyCode, String stmtCode, int startYm, int categoryAtr);
/**
 * 
 * @param companyCode
 * @param stmtCode
 * @param startYm
 */
	void removeAllCategory(String companyCode, String stmtCode, int startYm);
	/**
	 * get Categories
	 * 
	 * @param companyCode
	 * @param stmtCode
	 * @param startYm
	 * @return List Categories
	 */
	List<LayoutMasterCategory> getCategories(String companyCode, String stmtCode, int startYm);

	
}
