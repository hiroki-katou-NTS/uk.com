package nts.uk.ctx.at.request.app.find.application.common.dto;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.ctx.at.request.app.find.application.common.ApplicationDto_New;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.dto.PesionInforImport;
<<<<<<< HEAD
import nts.uk.ctx.at.request.dom.application.common.service.application.output.ApprovalFrameOutput;
import nts.uk.ctx.at.request.dom.application.common.service.application.output.ApprovalPhaseStateOutput;
import nts.uk.ctx.at.request.dom.application.common.service.application.output.ApprovalRootOutput;
=======
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApprovalFrameImport_New;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApprovalPhaseStateImport_New;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApprovalRootContentImport_New;
>>>>>>> pj/at/dev/Team_D/KDL034
@Value
@AllArgsConstructor
public class ApplicationSendDto {
	public ApplicationDto_New application;
	public String mailTemplate;
	public List<ApprovalPhaseStateDto> listApprovalPhaseStateDto;
<<<<<<< HEAD
	public String applicantMail;
	public static ApplicationSendDto fromDomain(ApplicationDto_New application_New, String mailTemplate, ApprovalRootOutput approvalRootContentImport, String loginerMail){
			return new ApplicationSendDto(application_New, mailTemplate, approvalRootContentImport.getListApprovalPhaseState()
					.stream().map(x -> ApprovalPhaseStateDto.fromApprovalPhaseState(x)).collect(Collectors.toList()), loginerMail);
=======
	public List<PesionInforImport> listApprover;
	public PesionInforImport applicant;
	public static ApplicationSendDto fromDomain(ApplicationDto_New application_New, String mailTemplate, ApprovalRootContentImport_New approvalRootContentImport, List<PesionInforImport> listApprover, PesionInforImport applicant){
			return new ApplicationSendDto(application_New, mailTemplate, approvalRootContentImport.getApprovalRootState().getListApprovalPhaseState()
					.stream().map(x -> ApprovalPhaseStateDto.fromApprovalPhaseStateImport(x)).collect(Collectors.toList()), listApprover, applicant);
>>>>>>> pj/at/dev/Team_D/KDL034
		}
}
@Value
@AllArgsConstructor
class ApprovalPhaseStateDto{
	private Integer phaseOrder;
	
	private Integer approvalAtrValue;
	
	private String approvalAtrName;
	
	private List<ApprovalFrameDto> listApprovalFrame;
	
<<<<<<< HEAD
	public static ApprovalPhaseStateDto fromApprovalPhaseState(ApprovalPhaseStateOutput approvalPhaseState){
		return new ApprovalPhaseStateDto(
				approvalPhaseState.getPhaseOrder(), 
				approvalPhaseState.getApprovalAtr().value,
				approvalPhaseState.getApprovalAtr().name,
				approvalPhaseState.getListApprovalFrame().stream().map(x -> ApprovalFrameDto.fromApprovalFrame(x)).collect(Collectors.toList()));
=======
	public static ApprovalPhaseStateDto fromApprovalPhaseStateImport(ApprovalPhaseStateImport_New approvalPhaseStateImport){
		return new ApprovalPhaseStateDto(
				approvalPhaseStateImport.getPhaseOrder(), 
				approvalPhaseStateImport.getApprovalAtr().value,
				approvalPhaseStateImport.getApprovalAtr().name,
				approvalPhaseStateImport.getListApprovalFrame().stream().map(x -> ApprovalFrameDto.fromApprovalFrameImport(x)).collect(Collectors.toList()));
>>>>>>> pj/at/dev/Team_D/KDL034
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
	
<<<<<<< HEAD
	public static ApprovalFrameDto fromApprovalFrame(ApprovalFrameOutput approvalFrame){
		return new ApprovalFrameDto(
				approvalFrame.getPhaseOrder(), 
				approvalFrame.getFrameOrder(), 
				approvalFrame.getApprovalAtr().value,
				approvalFrame.getApprovalAtr().name, 
				approvalFrame.getListApprover().stream()
=======
	public static ApprovalFrameDto fromApprovalFrameImport(ApprovalFrameImport_New approvalFrameImport){
		return new ApprovalFrameDto(
				approvalFrameImport.getPhaseOrder(), 
				approvalFrameImport.getFrameOrder(), 
				approvalFrameImport.getApprovalAtr().value,
				approvalFrameImport.getApprovalAtr().name, 
				approvalFrameImport.getListApprover().stream()
>>>>>>> pj/at/dev/Team_D/KDL034
					.map(x -> new ApproverStateDto(
							x.getApproverID(), 
							x.getApproverName(),
							x.getRepresenterID(),
<<<<<<< HEAD
							x.getRepresenterName(),
							x.getSMail()))
					.collect(Collectors.toList()), 
				approvalFrame.getApproverID(),
				approvalFrame.getApproverName(),
				approvalFrame.getRepresenterID(),
				approvalFrame.getRepresenterName(),
				approvalFrame.getApprovalReason());
=======
							x.getRepresenterName()))
					.collect(Collectors.toList()), 
				approvalFrameImport.getApproverID(),
				approvalFrameImport.getApproverName(),
				approvalFrameImport.getRepresenterID(),
				approvalFrameImport.getRepresenterName(),
				approvalFrameImport.getApprovalReason());
>>>>>>> pj/at/dev/Team_D/KDL034
	}
}

@Value
@AllArgsConstructor
class ApproverStateDto {
	private String approverID;
	
	private String approverName;
	
	private String representerID;
	
	private String representerName;
<<<<<<< HEAD
	
	private String approverMail;
=======
>>>>>>> pj/at/dev/Team_D/KDL034
}
