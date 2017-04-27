package nts.uk.ctx.sys.portal.dom.placement;

import java.util.List;
import java.util.Optional;

import nts.uk.ctx.sys.portal.dom.placement.Placement;

/**
 * @author LamDT
 */
public interface PlacementRepository {
	
	/**
	 * Find a Placement
	 *
	 * @param placementID
	 * @return Optional Placement
	 */
	Optional<Placement> find(String placementID);

	/**
	 * Find Placement by Layout
	 *
	 * @param layoutID
	 * @return List Placement
	 */
	List<Placement> findByLayout(String layoutID);

	/**
	 * Remove a Placement
	 *
	 * @param companyID
	 * @param placementID
	 */
	void remove(String companyID, String placementID);

	/**
	 * Add a Placement
	 *
	 * @param placement
	 */
	void add(Placement placement);

	/**
	 * update a Placement
	 *
	 * @param placement
	 */
	void update(Placement placement);

}
