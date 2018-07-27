package nts.uk.ctx.at.request.dom.application.approvalstatus.service.output;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.request.dom.application.Application_New;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApprovalRootContentImport_New;

@Getter
@Setter
@AllArgsConstructor
public class ApplicationApprContent {
	//「申請」
	Application_New application;
	
	//「承認ルートの内容」
	ApprovalRootContentImport_New apprRootContentExport;
}
