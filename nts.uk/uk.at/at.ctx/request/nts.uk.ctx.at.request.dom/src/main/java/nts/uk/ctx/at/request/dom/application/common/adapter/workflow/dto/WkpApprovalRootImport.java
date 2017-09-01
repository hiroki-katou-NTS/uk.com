package nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto;

import lombok.Getter;
import nts.arc.time.GeneralDate;

@Getter
public class WkpApprovalRootImport extends ComApprovalRootImport {

	/** 職場ID */
	private String workplaceId;

	public WkpApprovalRootImport(
			String companyId, 
			String approvalId, 
			String workplaceId,
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
		this.workplaceId = workplaceId;
	}
}
