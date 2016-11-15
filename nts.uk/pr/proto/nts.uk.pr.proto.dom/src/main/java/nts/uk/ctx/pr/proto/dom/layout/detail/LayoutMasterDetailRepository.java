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
	 * @return
	 */
	Optional<LayoutMasterDetail> find(String companyCode, String layoutCode, int startYm, String categoryAttribute);

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
	 * @param categoryAttribute
	 */
	void remove(String companyCode, String layoutCode, int startYm, String categoryAttribute);

	/**
	 * get Detail
	 * 
	 * @param companyCode
	 * @param layoutCode
	 * @param startYm
	 * @param categoryAttribute
	 * @return list Detail
	 */
	List<LayoutMasterDetail> getDetail(String companyCode, String layoutCode, int startYm, String categoryAttribute);
}
