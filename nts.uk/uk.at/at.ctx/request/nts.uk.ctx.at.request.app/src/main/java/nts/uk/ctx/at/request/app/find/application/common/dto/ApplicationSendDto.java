package nts.uk.ctx.at.request.app.find.application.common.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.ctx.at.request.app.find.application.common.ApplicationDto_New;
import nts.uk.ctx.at.request.app.find.setting.company.mailsetting.mailapplicationapproval.ApprovalTempDto;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.dto.PesionInforImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApprovalRootContentImport_New;
@Value
@AllArgsConstructor
public class ApplicationSendDto {
	public ApplicationDto_New application;
	public ApprovalTempDto approvalTemplate;
	public ApprovalRootContentImport_New approvalRoot;
	public List<PesionInforImport> listApprover;
	public PesionInforImport applicant;
	public static ApplicationSendDto fromDomain(ApplicationDto_New application_New, ApprovalTempDto approvalTemplate, ApprovalRootContentImport_New approvalRoot, List<PesionInforImport> listApprover, PesionInforImport applicant){
			return new ApplicationSendDto(application_New, approvalTemplate, approvalRoot, listApprover, applicant);
		}
}