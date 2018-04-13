package nts.uk.ctx.at.request.app.find.application.common.dto;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.ctx.at.request.app.find.application.common.ApplicationDto_New;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.dto.PesionInforImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApprovalFrameImport_New;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApprovalPhaseStateImport_New;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApprovalRootContentImport_New;
@Value
@AllArgsConstructor
public class ApplicationSendDto {
	public ApplicationDto_New application;
	public String mailTemplate;
	public List<ApprovalPhaseStateDto> listApprovalPhaseStateDto;
	public List<PesionInforImport> listApprover;
	public PesionInforImport applicant;
	public static ApplicationSendDto fromDomain(ApplicationDto_New application_New, String mailTemplate, ApprovalRootContentImport_New approvalRootContentImport, List<PesionInforImport> listApprover, PesionInforImport applicant){
			return new ApplicationSendDto(application_New, mailTemplate, approvalRootContentImport.getApprovalRootState().getListApprovalPhaseState()
					.stream().map(x -> ApprovalPhaseStateDto.fromApprovalPhaseStateImport(x)).collect(Collectors.toList()), listApprover, applicant);
		}
}
@Value
@AllArgsConstructor
class ApprovalPhaseStateDto{
	private Integer phaseOrder;
	
	private Integer approvalAtrValue;
	
	private String approvalAtrName;
	
	private List<ApprovalFrameDto> listApprovalFrame;
	
	public static ApprovalPhaseStateDto fromApprovalPhaseStateImport(ApprovalPhaseStateImport_New approvalPhaseStateImport){
		return new ApprovalPhaseStateDto(
				approvalPhaseStateImport.getPhaseOrder(), 
				approvalPhaseStateImport.getApprovalAtr().value,
				approvalPhaseStateImport.getApprovalAtr().name,
				approvalPhaseStateImport.getListApprovalFrame().stream().map(x -> ApprovalFrameDto.fromApprovalFrameImport(x)).collect(Collectors.toList()));
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
	
	public static ApprovalFrameDto fromApprovalFrameImport(ApprovalFrameImport_New approvalFrameImport){
		return new ApprovalFrameDto(
				approvalFrameImport.getPhaseOrder(), 
				approvalFrameImport.getFrameOrder(), 
				approvalFrameImport.getApprovalAtr().value,
				approvalFrameImport.getApprovalAtr().name, 
				approvalFrameImport.getListApprover().stream()
					.map(x -> new ApproverStateDto(
							x.getApproverID(), 
							x.getApproverName(),
							x.getRepresenterID(),
							x.getRepresenterName()))
					.collect(Collectors.toList()), 
				approvalFrameImport.getApproverID(),
				approvalFrameImport.getApproverName(),
				approvalFrameImport.getRepresenterID(),
				approvalFrameImport.getRepresenterName(),
				approvalFrameImport.getApprovalReason());
	}
}

@Value
@AllArgsConstructor
class ApproverStateDto {
	private String approverID;
	
	private String approverName;
	
	private String representerID;
	
	private String representerName;
}
