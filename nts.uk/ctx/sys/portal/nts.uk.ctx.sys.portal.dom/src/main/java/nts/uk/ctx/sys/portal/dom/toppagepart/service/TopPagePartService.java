package nts.uk.ctx.sys.portal.dom.toppagepart.service;

import java.util.List;

import nts.arc.enums.EnumConstant;
import nts.uk.ctx.sys.portal.dom.layout.PGType;
import nts.uk.ctx.sys.portal.dom.toppagepart.TopPagePart;

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
	 * Delete TopPagePart & TopPagePartSetting & Placements using it
	 * 
	 * @param topPagePartID
	 */
	void deleteTopPagePart(String companyID, String topPagePartID);
	
	/**
	 * Get List TopPagePartType by PGType
	 * 
	 * @param pgType
	 * @return List EnumConstant of TopPagePartType
	 */
	List<EnumConstant> getAllActiveTopPagePartType(String companyID, PGType pgType);
	
	/**
	 * Get all active TopPagePart
	 * 
	 * @param
	 * @return List active TopPagePart
	 */
	List<TopPagePart> getAllActiveTopPagePart(String companyID, PGType pgType);
	
}
