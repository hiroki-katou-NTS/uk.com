package nts.uk.ctx.sys.portal.dom.toppagepart.service;

/**
 * @author HieuLT
 */
public interface TopPagePartService {
	
	/**
	 * check data is exit with companyId, code and type
	 * 
	 * @param 
	 * @return
	 * true: is data
	 * false: no data
	 */
	boolean isExist(String companyID, String flowMenuCD, int type);
	
	/**
	 * Delete TopPagePart & Placements using it
	 * 
	 * @param topPagePartID
	 */
	void deleteTopPagePart(String companyID, String topPagePartID);
}
