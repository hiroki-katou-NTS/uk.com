package nts.uk.ctx.at.request.dom.application.common.service.application.output;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.request.dom.application.Application_New;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.dto.PesionInforImport;

@AllArgsConstructor
@Getter
public class ApplicationForSendOutput {
	private Application_New application;
	private String mailTemplate;
	private ApprovalRootOutput approvalRoot;
	private String applicantMail;
}
