package nts.uk.ctx.sys.portal.dom.standardmenu;

import java.util.List;

/**
 * The Interface StandardMenuRepository.
 */
public interface StandardMenuRepository {
	/**
	 * Find all.
	 *
	 * @param companyId the company id
	 * @return the list
	 */
	 List<StandardMenu> findAll(String companyId);
	 
	/**
	 * Find all with afterLoginDisplayIndicator is true.
	 *
	 * @param companyId the company id
	 * @return the list with afterLoginDisplayIndicator is true
	 */
	 List<StandardMenu> findAllWithAfterLoginDisplayIndicatorIsTrue(String companyId);
}
