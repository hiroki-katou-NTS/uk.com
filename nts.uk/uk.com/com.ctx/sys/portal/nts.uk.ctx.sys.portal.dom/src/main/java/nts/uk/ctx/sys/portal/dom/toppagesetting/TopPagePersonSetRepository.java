package nts.uk.ctx.sys.portal.dom.toppagesetting;

import java.util.Optional;

/**
 * 
 * @author sonnh1
 *
 */
public interface TopPagePersonSetRepository {
	/**
	 * 
	 * @param companyId
	 * @param sId
	 * @return 
	 */
	Optional<TopPagePersonSet> findBySid(String companyId, String sId);
}
