package nts.uk.ctx.sys.portal.dom.layout.service;

import nts.uk.ctx.sys.portal.dom.layout.Layout;

/**
 * @author LamDT
 */
public interface LayoutService {

	/**
	 * Check Layout is existed
	 * 
	 * @param
	 * @return True if existed
	 */
	boolean isExist(String layoutID);

	/**
	 * Delete Layout and all Placements
	 * 
	 * @param
	 */
	void createLayout(String companyID, String parentCode, int pgType, Layout layout);

	/**
	 * Delete Layout and all Placements
	 * 
	 * @param
	 */
	void deleteLayout(String companyID, String layoutID);

	/**
	 * Copy Layout with given ID
	 * 
	 * @param
	 * @return Copied layout ID
	 */
	String copyTopPageLayout(String layoutID);

	/**
	 * Copy Layout with given ID
	 * 
	 * @param
	 * @return Copied layout ID
	 */
	String copyTitleMenuLayout(String layoutID);

	/**
	 * Copy Layout with given ID
	 * 
	 * @param
	 * @return Copied layout ID
	 */
	String copyMyPageLayout(String layoutID);

}