package nts.uk.ctx.sys.log.dom.reference;

/**
 * 
 * @author thuongtv
 *
 */

public interface PersonEmpBasicInfoAdapter {
	/**
	 * Get list PersonEmpBasicInfo by employee ID
	 * @return PersonEmpBasicInfoImport
	 */
	PersonEmpBasicInfoImport getPersonEmpBasicInfoByEmpId(String empId);
}
