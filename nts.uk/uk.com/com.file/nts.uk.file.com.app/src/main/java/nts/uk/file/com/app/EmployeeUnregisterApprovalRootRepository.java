/**
 * 
 */
package nts.uk.file.com.app;

import nts.arc.time.GeneralDate;

/**
 * @author lanlt
 *
 */
public interface EmployeeUnregisterApprovalRootRepository {
	EmployeeUnregisterOutputDataSoure getEmployeeUnregisterOutputLst(String companyId, GeneralDate date);
}
