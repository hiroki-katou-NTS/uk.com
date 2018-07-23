package nts.uk.ctx.at.request.app.find.application.common.dto;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.ctx.at.request.app.find.application.common.ApplicationDto_New;
import nts.uk.ctx.at.request.app.find.application.common.dto.approvesendmail.ApproverPhaseStateSendDto;
import nts.uk.ctx.at.request.dom.application.common.service.application.output.ApprovalRootOutput;
import nts.uk.shr.com.context.AppContexts;
@Value
@AllArgsConstructor
public class ApplicationSendDto {
	public ApplicationDto_New application;
	public String mailTemplate;
	public List<ApproverPhaseStateSendDto> listApprovalPhaseStateDto;
	public String applicantMail;
	//ver6
	private String sidLogin;
	public static ApplicationSendDto fromDomain(ApplicationDto_New application_New, String mailTemplate, ApprovalRootOutput approvalRootContentImport, String loginerMail){
			return new ApplicationSendDto(application_New, mailTemplate, 
					approvalRootContentImport.getListApprovalPhaseState()
						.stream().map(x -> ApproverPhaseStateSendDto.fromApprovalPhaseState(x))
						.collect(Collectors.toList()),
					loginerMail,
					AppContexts.user().employeeId());
		}
}
