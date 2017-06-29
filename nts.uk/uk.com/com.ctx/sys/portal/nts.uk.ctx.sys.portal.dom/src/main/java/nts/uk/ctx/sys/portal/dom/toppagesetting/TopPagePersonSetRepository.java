package nts.uk.ctx.sys.portal.dom.toppagesetting;

import java.util.List;

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
	TopPagePersonSet getbyCode(String companyId, String employeeId);
}
