package nts.uk.ctx.workflow.dom.approvermanagement.workroot.service.output;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MasterApproverRootOutput {
	/** company */
	private CompanyApprovalInfor comRootInfor;
	/** workplace */
	private MasterWkpOutput wkpRootOutput;
	/** employee */
	private MasterEmployeeOutput empRootOutput;
}
