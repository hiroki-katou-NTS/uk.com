package nts.uk.ctx.at.request.dom.application.common.service.application.output;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.dto.PesionInforImport;

@AllArgsConstructor
@Getter
public class ApplicationForRemandOutput {
	public String appId;
	public Long version;
	public int errorFlag;
	public String applicantPosition;
	public PesionInforImport applicant; 
	public List<DetailApprovalFrameOutput> approvalFrameDtoForRemand;
}
