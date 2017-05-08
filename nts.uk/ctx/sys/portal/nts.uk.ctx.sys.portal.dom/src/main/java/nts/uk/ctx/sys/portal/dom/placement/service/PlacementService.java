package nts.uk.ctx.sys.portal.dom.placement.service;

import java.util.List;

/**
 * @author LamDT
 */
public interface PlacementService {

	/**
	 * Check Layout is existed
	 * 
	 * @param
	 * @return True if existed
	 */
	boolean isExist(String placementID);

	/**
	 * Copy List Placement within Layout
	 * 
	 * @param
	 * @return Copied List placement ID
	 */
	List<String> copyPlacementByLayout(String layoutID);

	/**
	 * Copy Placement with given ID
	 * 
	 * @param
	 * @return Copied placement ID
	 */
	String copyPlacement(String placementID);

}