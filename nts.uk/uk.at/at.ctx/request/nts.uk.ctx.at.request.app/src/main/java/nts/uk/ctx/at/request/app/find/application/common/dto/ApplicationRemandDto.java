package nts.uk.ctx.at.request.app.find.application.common.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.dto.PesionInforImport;
@Value
@AllArgsConstructor
public class ApplicationRemandDto {
	public String appId;
	public Long version;
	public int errorFlag;
	public String applicantPosition;
	public PesionInforImport applicant; 
	public List<ApprovalFrameForRemandDto> approvalFrameDtoForRemand;
	public static ApplicationRemandDto fromDomain(String appId, Long version, int flag, String applicantPosition, PesionInforImport applicant, List<ApprovalFrameForRemandDto> approvalFrameDtoForRemand
		){
		return new ApplicationRemandDto(appId, version, flag, applicantPosition, applicant, approvalFrameDtoForRemand);
	}
}
