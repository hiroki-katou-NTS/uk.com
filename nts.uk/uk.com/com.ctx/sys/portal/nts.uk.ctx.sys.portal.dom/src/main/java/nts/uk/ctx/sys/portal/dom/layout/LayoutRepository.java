package nts.uk.ctx.sys.portal.dom.layout;

import java.util.List;
import java.util.Optional;

/**
 * @author LamDT
 */
public interface LayoutRepository {

	/**
	 * Find a Layout
	 *
	 * @param layoutID
	 * @return Optional Layout
	 */
	Optional<Layout> find(String layoutID);

	/**
	 * Find all Layout
	 *
	 * @param companyID
	 * @return List Layout
	 */
	List<Layout> findAll(String companyID);

	/**
	 * Find Layout by PGType
	 *
	 * @param companyID
	 * @param pgType
	 * @return List Layout
	 */
	List<Layout> findByType(String companyID, int pgType);

	/**
	 * Remove a Layout
	 *
	 * @param companyID
	 * @param layoutID
	 */
	void remove(String companyID, String layoutID);

	/**
	 * Add a Layout
	 *
	 * @param layout
	 */
	void add(Layout layout);

	/**
	 * update a Layout
	 *
	 * @param layout
	 */
	void update(Layout layout);

}