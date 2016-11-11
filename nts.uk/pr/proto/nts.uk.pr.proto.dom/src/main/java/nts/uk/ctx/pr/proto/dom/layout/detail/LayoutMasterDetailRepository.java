package nts.uk.ctx.pr.proto.dom.layout.detail;

import java.util.Optional;

public interface LayoutMasterDetailRepository {
	
	/**
	 * find layout master detail by company code, layout code, start date, category code
	 * @param companyCode
	 * @param layoutCode
	 * @param startYm
	 * @param categoryCode
	 * @return
	 */
	Optional<LayoutMasterDetail> find(String companyCode, String layoutCode, int startYm, String categoryCode);
			
	/**
	 * add a layout master detail
	 * @param layoutMasterDetail
	 */
	void add(LayoutMasterDetail layoutMasterDetail);
	
	/**
	 * update a layout master detail
	 * @param layoutMasterDetail
	 */
	void update(LayoutMasterDetail layoutMasterDetail);
	
	/**
	 * delete a layout master detail
	 * @param companyCode
	 * @param layoutCode
	 * @param startYm
	 * @param categoryCode
	 */
	void remove(String companyCode, String layoutCode, int startYm, String categoryCode);
}
