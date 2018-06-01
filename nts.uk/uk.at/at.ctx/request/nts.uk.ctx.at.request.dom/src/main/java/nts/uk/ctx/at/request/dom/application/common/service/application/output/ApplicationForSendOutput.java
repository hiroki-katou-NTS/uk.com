package nts.uk.ctx.at.request.dom.application.common.service.application.output;


import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.request.dom.application.Application_New;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.dto.PesionInforImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApprovalRootContentImport_New;

@AllArgsConstructor
@Getter
public class ApplicationForSendOutput {
	private Application_New application;
	private String mailTemplate;
	private ApprovalRootOutput approvalRoot;
	private String applicantMail;
}
