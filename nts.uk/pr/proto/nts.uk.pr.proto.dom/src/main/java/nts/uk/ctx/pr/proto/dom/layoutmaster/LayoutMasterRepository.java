package nts.uk.ctx.pr.proto.dom.layoutmaster;

import java.util.List;
import java.util.Optional;

public interface LayoutMasterRepository {
	//layout master
	/**
	 * find layout master by company code, layout master(name or code), start date
	 * @param companyCode
	 * @param layoutMaster
	 * @param strYm
	 * @return
	 */
	Optional<LayoutMaster> find(String companyCode, String layoutMaster, int strYm);	
	
	/**
	 * find all layout master by company code, start date
	 * @return layout master
	 */
	List<LayoutMaster> findAll(String companyCode, int strYm);
	
	/**
	 * Add a  new layout master
	 * @param layoutMaster
	 */
	void add(LayoutMaster layoutMaster);
	
	/**
	 * update a layout master
	 * @param layoutMaster
	 */
	void update(LayoutMaster layoutMaster);
	
	/**
	 * delete a layout master
	 * @param companyCode
	 * @param layoutCode 
	 * @param startYm
	 */
	void remove(String companyCode, String layoutCode, int startYm);
	
	//Layout Master Category
	/**
	 * find category by company code, layout code, start date
	 * @param companyCode
	 * @param layoutCode
	 * @param startDate
	 * @return
	 */
	Optional<LayoutMasterCategory> findCategory(String companyCode, String layoutCode, int startDate);
	
	/**
	 * add a category
	 * @param layoutMasterCategory
	 */
	void addCategory(LayoutMasterCategory layoutMasterCategory);
	
	/**
	 * update a category
	 * @param layoutMasterCategory
	 */
	void update(LayoutMasterCategory layoutMasterCategory);
	
	/**
	 * delete a category by company code, layout code, start date, category code
	 * @param companyCode
	 * @param layoutCode
	 * @param startDate
	 * @param categoryCode
	 */
	void delete(String companyCode, String layoutCode, int startDate, String categoryCode);
	
	//Layout master line
	/**
	 * find line by company code, layout code, start date
	 * @param companyCode
	 * @param layoutCode
	 * @param startDate
	 * @return
	 */
	Optional<LayoutMasterLine> findLine(String companyCode, String layoutCode, int startDate);
	
	/**
	 * add a line
	 * @param layoutMasterLine
	 */
	void addLine(LayoutMasterLine layoutMasterLine);
	
	/**
	 * update layout master line
	 * @param layoutMasterLine
	 */
	void updateLine(LayoutMasterLine layoutMasterLine);
	
	/**
	 * delete line 
	 * @param companyCode
	 * @param layoutCode
	 * @param startDate
	 * @param categoryCode
	 * @param autoLineId
	 */
	void removeLine(String companyCode, String layoutCode, int startDate, String categoryCode, String autoLineId);
}
