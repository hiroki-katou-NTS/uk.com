package nts.uk.ctx.workflow.dom.approvermanagement.workroot.service.output;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.workflow.dom.adapter.workplace.WorkplaceImport;

@Data
@AllArgsConstructor
public class WorkplaceApproverOutput {
	//職場情報
	WorkplaceImport wpInfor;
	//職場別就業承認ルート情報
	List<ApprovalForApplication> wpRootInfor;
}
