package nts.uk.ctx.pr.proto.dom.layout.detail;

import java.util.List;
import java.util.Optional;

import nts.uk.ctx.pr.proto.dom.layout.category.LayoutMasterCategory;

public interface LayoutMasterDetailRepository {

	/**
	 * find layout master detail by company code, layout code, start date,
	 * category code
	 * 
	 * @param companyCode
	 * @param layoutCode
	 * @param startYm
	 * @param categoryAttribute
	 * @param autoLineID
	 * @param itemCode
	 * @return
	 */
	Optional<LayoutMasterDetail> find(String companyCode, String layoutCode, int startYm, String stmtCode,
			String categoryAttribute, String autoLineID, String itemCode);

	/**
	 * add a layout master detail
	 * 
	 * @param layoutMasterDetail
	 */
	void add(LayoutMasterDetail layoutMasterDetail);

	/**
	 * update a layout master detail
	 * 
	 * @param layoutMasterDetail
	 */
	void update(LayoutMasterDetail layoutMasterDetail);

	/**
	 * delete a layout master detail
	 * 
	 * @param companyCode
	 * @param layoutCode
	 * @param startYm
	 * @param categoryAtr
	 * @param autoLineID
	 * @param itemCode
	 */
	void remove(String companyCode, int startYm, String stmtCode, int categoryAtr,
			String autoLineID, String itemCode);

	/**
	 * get Detail
	 * 
	 * @param companyCode
	 * @param layoutCode
	 * @param startYm
	 * @param categoryAtr
	 * @param autoLineID
	 * @return list Detail
	 */
	List<LayoutMasterDetail> getDetails(String companyCode, String stmtCode, int startYm,
			int categoryAtr, String autoLineID);
}
