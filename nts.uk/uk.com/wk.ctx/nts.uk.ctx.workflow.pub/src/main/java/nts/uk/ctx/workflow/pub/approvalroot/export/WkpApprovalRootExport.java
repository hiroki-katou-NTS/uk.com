package nts.uk.ctx.workflow.pub.approvalroot.export;

import lombok.Getter;
import nts.arc.time.GeneralDate;

@Getter
public class WkpApprovalRootExport extends ComApprovalRootExport {

	/** 職場ID */
	private String workplaceId;

	public WkpApprovalRootExport(
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
