/**
 * 5:04:30 PM Mar 9, 2018
 */
package nts.uk.ctx.at.record.dom.adapter.workflow.service;

import java.util.List;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.adapter.workflow.service.dtos.ApprovalRootOfEmployeeImport;
import nts.uk.ctx.at.record.dom.adapter.workflow.service.dtos.ApproveRootStatusForEmpImport;

/**
 * @author hungnm
 *
 */
public interface ApprovalStatusAdapter {
	List<ApproveRootStatusForEmpImport> getApprovalByEmplAndDate(GeneralDate startDate, GeneralDate endDate, String employeeID,String companyID,Integer rootType); 
	ApprovalRootOfEmployeeImport getApprovalRootOfEmloyee(GeneralDate startDate, GeneralDate endDate, String approverID,String companyID,Integer rootType);
}
