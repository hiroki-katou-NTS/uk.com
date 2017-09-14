package nts.uk.ctx.workflow.dom.approvermanagement.workroot.service.output;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class MasterApproverRootOutput {
	
	CompanyApprovalInfor companyRootInfor;
	/**
	 * workplaceId, approver of workplace
	 */
	Map<String, WorkplaceApproverOutput> worplaceRootInfor;
	
	public MasterApproverRootOutput(CompanyApprovalInfor companyRootInfor,
			Map<String, WorkplaceApproverOutput> worplaceRootInfor) {
		super();
		this.companyRootInfor = companyRootInfor;
		this.worplaceRootInfor = worplaceRootInfor;
	}
	
	
}
