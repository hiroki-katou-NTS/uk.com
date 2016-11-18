package nts.uk.ctx.pr.proto.dom.layout.detail;

import java.util.List;
import java.util.Optional;

import nts.arc.time.YearMonth;
import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.proto.dom.itemmaster.ItemCode;
import nts.uk.ctx.pr.proto.dom.layout.LayoutCode;

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
	 * add list details
	 * @param details
	 */
	void add(List<LayoutMasterDetail> details);

	/**
	 * update a layout master detail
	 */
	void update(LayoutMasterDetail layoutMasterDetail);

	/**
	 * update list details
	 * @param details
	 */
	void update(List<LayoutMasterDetail> details);
	
	/**
	 * delete a layout master detail
	 */
	void remove(CompanyCode companyCode
			, LayoutCode layoutCode
			, YearMonth startYm
			, int categoryAtr
			, ItemCode itemCode);

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
	
	List<LayoutMasterDetail> getDetailsByCategory(
			String companyCd, 
			String stmtCd, 
			int startYm,
			int categoryAtr);

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
	
	/**
	 * get detail
	 * 
	 * @param companyCode
	 * @param layoutCode
	 * @param startYearMonth
	 * @param categoryAttribute
	 * @param calculationMethod
	 * @param item code
	 * @return
	 */
	Optional<LayoutMasterDetail> getDetail(String companyCode, String stmtCode, int startYearMonth, int categoryAttribute, String itemCode, int sumScopeAtr);
}
