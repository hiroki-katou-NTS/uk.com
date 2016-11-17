package nts.uk.ctx.pr.proto.dom.layout.detail;

import java.util.List;
import java.util.Optional;

public interface LayoutMasterDetailRepository {

	/**
	 * add a layout master detail
	 * 
	 * @param companyCode
	 * @param startYm
	 * @param stmtCode
	 */
	void add(LayoutMasterDetail layoutMasterDetail);

	/**
	 * update a layout master detail
	 */
	void update(LayoutMasterDetail layoutMasterDetail);

	/**
	 * delete a layout master detail
	 */
	void remove(String companyCode
			, String layoutCode
			, int startYm
			, int categoryAtr
			, String itemCode);

	/**
	 * get Detail
	 * 
	 * @param companyCode
	 * @param layout code
	 * @param start YM
	 * @return category type
	 */
	List<LayoutMasterDetail> getDetails(
			String companyCd, 
			String stmtCd, 
			int startYm);

	/**
	 * get detail
	 * 
	 * @param companyCode
	 * @param layoutCode
	 * @param startYM
	 * @param categoryAtr
	 * @param calculationMethod
	 * @param item code
	 * @return
	 */
	Optional<LayoutMasterDetail> getDetail(
			String companyCd, 
			String stmtCd, 
			int startYm, 
			int categoryAtr,
			String itemCd);
}
