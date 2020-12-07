package nts.uk.ctx.at.request.app.find.application.approvalstatus;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.request.app.find.application.ApplicationDto;
import nts.uk.ctx.at.request.dom.application.approvalstatus.service.output.ApprSttEmpDateContent;
import nts.uk.ctx.at.request.dom.application.approvalstatus.service.output.PhaseApproverStt;

/**
 * refactor 5
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Getter
public class ApprSttEmpDateContentDto {
	
	private ApplicationDto application;
	
	private String content;
	
	private int reflectedState;
	
	private List<PhaseApproverStt> phaseApproverSttLst;
	
	public static ApprSttEmpDateContentDto fromDomain(ApprSttEmpDateContent apprSttEmpDateContent) {
		return new ApprSttEmpDateContentDto(
				ApplicationDto.fromDomain(apprSttEmpDateContent.getApplication()), 
				apprSttEmpDateContent.getContent(), 
				apprSttEmpDateContent.getReflectedState().value, 
				apprSttEmpDateContent.getPhaseApproverSttLst());
	}
}
