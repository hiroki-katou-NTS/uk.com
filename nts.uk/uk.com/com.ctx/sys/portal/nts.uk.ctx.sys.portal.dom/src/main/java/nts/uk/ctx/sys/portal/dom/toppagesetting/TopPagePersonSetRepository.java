package nts.uk.ctx.sys.portal.dom.toppagesetting;

import java.util.List;
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
	 * @param List<sId>
	 * @return list TopPagepersonSet
	 */
	List<TopPagePersonSet> findByListSid(String companyId, List<String> sId);

	/**
	 * added by Hoant
	 * 
	 * @param companyId
	 * @param employeeId
	 * @return
	 */
	Optional<TopPagePersonSet> getbyCode(String companyId, String employeeId);
}
