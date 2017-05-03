package nts.uk.ctx.sys.portal.dom.toppagepart;

import java.util.List;
import java.util.Optional;

/**
 * @author LamDT
 */
public interface TopPagePartRepository {
	/**
	 * Find a TopPagePart
	 *
	 * @param topPagePartID
	 * @return Optional TopPagePart
	 */
	Optional<TopPagePart> find(String topPagePartID);

	/**
	 * Find all TopPagePart
	 *
	 * @param companyID
	 * @return List TopPagePart
	 */
	List<TopPagePart> findAll(String companyID);

	/**
	 * Find TopPagePart by Type
	 *
	 * @param type
	 * @return List TopPagePart
	 */
	List<TopPagePart> findByType(String companyID, int type);

	/**
	 * Remove a TopPagePart
	 *
	 * @param companyID
	 * @param topPagePartID
	 */
	void remove(String companyID, String topPagePartID);

	/**
	 * Add a TopPagePart
	 *
	 * @param topPagePart
	 */
	void add(TopPagePart topPagePart);

	/**
	 * update a TopPagePart
	 *
	 * @param topPagePart
	 */
	void update(TopPagePart topPagePart);

}
