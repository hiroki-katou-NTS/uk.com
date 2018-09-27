package nts.uk.ctx.workflow.dom.approvermanagement.workroot.service.output;

import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.workflow.dom.adapter.workplace.WorkplaceImport;
@Getter
@AllArgsConstructor
public class MasterWkpOutput {
	//workplaceId, approver of workplace
	private Map<String, WorkplaceApproverOutput> worplaceRootInfor;
	//職場情報一覧
	private List<WorkplaceImport> lstWpInfor;
}
