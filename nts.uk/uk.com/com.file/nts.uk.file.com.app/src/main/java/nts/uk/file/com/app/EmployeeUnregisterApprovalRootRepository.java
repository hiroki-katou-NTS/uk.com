/**
 * 
 */
package nts.uk.file.com.app;

import java.util.List;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.service.output.EmployeeUnregisterOutput;

/**
 * @author lanlt
 *
 */
public interface EmployeeUnregisterApprovalRootRepository {
	List<EmployeeUnregisterOutput> getEmployeeUnregisterOutputLst(String companyId, GeneralDate date);
}
