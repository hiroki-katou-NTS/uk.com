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
	 * Find TopPagePart by PGType
	 *
	 * @param companyID
	 * @param pgType
	 * @return List TopPagePart
	 */
	List<TopPagePart> findByLayout(String topPagePartID);

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
