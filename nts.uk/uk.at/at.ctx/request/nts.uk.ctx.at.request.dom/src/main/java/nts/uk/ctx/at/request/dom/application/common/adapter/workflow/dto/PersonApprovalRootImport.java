package nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto;

import lombok.Getter;
import nts.arc.time.GeneralDate;

@Getter
public class PersonApprovalRootImport extends ComApprovalRootImport {
	/** 社員ID */
	private String employeeId;

	public PersonApprovalRootImport(
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
