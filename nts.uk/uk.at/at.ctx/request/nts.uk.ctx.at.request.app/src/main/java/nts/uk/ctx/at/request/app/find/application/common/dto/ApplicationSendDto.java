package nts.uk.ctx.at.request.app.find.application.common.dto;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.ctx.at.request.app.find.application.common.ApplicationDto_New;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.dto.PesionInforImport;
import nts.uk.ctx.at.request.dom.application.common.service.application.output.ApprovalFrameOutput;
import nts.uk.ctx.at.request.dom.application.common.service.application.output.ApprovalPhaseStateOutput;
import nts.uk.ctx.at.request.dom.application.common.service.application.output.ApprovalRootOutput;
@Value
@AllArgsConstructor
public class ApplicationSendDto {
	public ApplicationDto_New application;
	public String mailTemplate;
	public List<ApprovalPhaseStateDto> listApprovalPhaseStateDto;
	public String applicantMail;
	public static ApplicationSendDto fromDomain(ApplicationDto_New application_New, String mailTemplate, ApprovalRootOutput approvalRootContentImport, String loginerMail){
			return new ApplicationSendDto(application_New, mailTemplate, approvalRootContentImport.getListApprovalPhaseState()
					.stream().map(x -> ApprovalPhaseStateDto.fromApprovalPhaseState(x)).collect(Collectors.toList()), loginerMail);
		}
}
@Value
@AllArgsConstructor
class ApprovalPhaseStateDto{
	private Integer phaseOrder;
	
	private Integer approvalAtrValue;
	
	private String approvalAtrName;
	
	private List<ApprovalFrameDto> listApprovalFrame;
	
	public static ApprovalPhaseStateDto fromApprovalPhaseState(ApprovalPhaseStateOutput approvalPhaseState){
		return new ApprovalPhaseStateDto(
				approvalPhaseState.getPhaseOrder(), 
				approvalPhaseState.getApprovalAtr().value,
				approvalPhaseState.getApprovalAtr().name,
				approvalPhaseState.getListApprovalFrame().stream().map(x -> ApprovalFrameDto.fromApprovalFrame(x)).collect(Collectors.toList()));
	}
}

@Value
@AllArgsConstructor
class ApprovalFrameDto {
	private Integer phaseOrder;
	
	private Integer frameOrder;
	
	private Integer approvalAtrValue;
	
	private String approvalAtrName;
	
	private List<ApproverStateDto> listApprover;
	
	private String approverID;
	
	private String approverName;
	
	private String representerID;
	
	private String representerName;
	
	private String approvalReason;
	
	public static ApprovalFrameDto fromApprovalFrame(ApprovalFrameOutput approvalFrame){
		return new ApprovalFrameDto(
				approvalFrame.getPhaseOrder(), 
				approvalFrame.getFrameOrder(), 
				approvalFrame.getApprovalAtr().value,
				approvalFrame.getApprovalAtr().name, 
				approvalFrame.getListApprover().stream()
					.map(x -> new ApproverStateDto(
							x.getApproverID(), 
							x.getApproverName(),
							x.getRepresenterID(),
							x.getRepresenterName(),
							x.getSMail()))
					.collect(Collectors.toList()), 
				approvalFrame.getApproverID(),
				approvalFrame.getApproverName(),
				approvalFrame.getRepresenterID(),
				approvalFrame.getRepresenterName(),
				approvalFrame.getApprovalReason());
	}
}

@Value
@AllArgsConstructor
class ApproverStateDto {
	private String approverID;
	
	private String approverName;
	
	private String representerID;
	
	private String representerName;
	
	private String approverMail;
}
