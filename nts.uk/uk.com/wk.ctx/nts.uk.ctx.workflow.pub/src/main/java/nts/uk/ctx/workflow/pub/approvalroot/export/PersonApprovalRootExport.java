package nts.uk.ctx.workflow.pub.approvalroot.export;

import lombok.Getter;
import nts.arc.time.GeneralDate;

@Getter
public class PersonApprovalRootExport extends ComApprovalRootExport {
	/** 社員ID */
	private String employeeId;

	public PersonApprovalRootExport(
			String companyId, 
			String approvalId, 
			String employeeId,
			String historyId, 
			int applicationType,
			GeneralDate startDate, 
			GeneralDate endDate, 
			String branchId, 
			String anyItemApplicationId,
			int confirmationRootType, 
			int employmentRootAtr) {
		super(companyId, 
				approvalId, 
				historyId, 
				applicationType, 
				startDate, 
				endDate, 
				branchId, 
				anyItemApplicationId,
				confirmationRootType, 
				employmentRootAtr);
		this.employeeId = employeeId;
	}
	
	
}
