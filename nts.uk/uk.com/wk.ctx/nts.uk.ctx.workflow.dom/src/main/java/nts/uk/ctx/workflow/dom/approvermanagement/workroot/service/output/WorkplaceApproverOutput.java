package nts.uk.ctx.workflow.dom.approvermanagement.workroot.service.output;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.workplace.WorkplaceApproverDto;

@Data
@AllArgsConstructor
public class WorkplaceApproverOutput {
	//職場情報
	List<WorkplaceApproverDto> wpInfor;
	//職場別就業承認ルート情報
	List<ApprovalForApplication> wpRootInfor;
}
