package nts.uk.ctx.pr.proto.dom.layout.category;

import java.util.List;
import java.util.Optional;

import nts.uk.ctx.pr.proto.dom.layout.LayoutMaster;

public interface LayoutMasterCategoryRepository {

	/**
	 * find layout master category by companyCode, layoutCode ,startYm
	 * 
	 * @param companyCode
	 * @param layoutCode
	 * @param startYm
	 * @return
	 */
	Optional<LayoutMasterCategory> find(String companyCode, String layoutCode, int startYm);

	/**
	 * find layout master category by companyCode
	 * 
	 * @param companyCode
	 * @return
	 */
	List<LayoutMasterCategory> findAll(String companyCode);

	/**
	 * add LayoutMasterCategory
	 * 
	 * @param companyCode
	 * @param layoutCode
	 * @param startYm
	 */

	void add(String companyCode, String layoutCode, int startYm);

	/**
	 * update LayoutMasterCategory
	 * 
	 * @param companyCode
	 * @param stmtCode
	 * @param startYm
	 * @param categoryAtr
	 */
	void update(String companyCode, String stmtCode, int startYm, int categoryAtr);

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
	 * get Categories
	 * 
	 * @param companyCode
	 * @param layoutCode
	 * @param startYm
	 * @return List Categories
	 */
	List<LayoutMasterCategory> getCategories(String companyCode, String layoutCode, int startYm);
}
