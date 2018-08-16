package nts.uk.ctx.bs.company.pub.company;

import java.util.List;

public interface ICompanyPub {

	/**
	 * for request list No.51
	 * 
	 * @return List Company
	 */

	List<CompanyExport> getAllCompany();

	/**
	 * for request list No.108
	 * 
	 * @return Company Info
	 */

	BeginOfMonthExport getBeginOfMonth(String cid);

	/**
	 * for request list No.125
	 * 
	 * @return Company Info
	 */

	CompanyExport getCompanyByCid(String cid);
	
	/**
	 * For RequestList289
	 * 
	 * @param companyId
	 * @param contractCd
	 * @return
	 */
	List<String> acquireAllCompany();
	/** RequestList No58 **/
	List<CompanyExport> getAllCompanyInfor();
	
	/**
	 * Get Company By cid
	 * @param cid
	 * @return
	 */
	CompanyExport getCompany(String cid);
}
