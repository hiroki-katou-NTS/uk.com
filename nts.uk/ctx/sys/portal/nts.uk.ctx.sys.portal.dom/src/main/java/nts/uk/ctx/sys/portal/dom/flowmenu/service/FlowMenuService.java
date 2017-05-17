package nts.uk.ctx.sys.portal.dom.flowmenu.service;

/**
 * @author HieuLT
 */
public interface FlowMenuService {

	/**
	 * Check FlowMenu is existed
	 * 
	 * @param
	 * @return True if existed
	 */
	boolean isExist(String companyID, String topPagePartId);

	/**
	 * Delete FlowMenu
	 * 
	 * @param
	 */
	void deleteFlowMenu(String companyID, String topPagePartId);

	
}