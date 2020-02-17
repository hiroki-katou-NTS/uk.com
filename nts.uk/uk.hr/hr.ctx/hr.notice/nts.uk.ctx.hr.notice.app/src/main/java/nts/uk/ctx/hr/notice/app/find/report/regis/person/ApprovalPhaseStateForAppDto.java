package nts.uk.ctx.hr.notice.app.find.report.regis.person;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.hr.shared.dom.notice.report.registration.person.PhaseSttHrImport;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ApprovalPhaseStateForAppDto {
	
	private Integer phaseOrder;
	
	private Integer approvalAtrValue;
	
	private String approvalAtrName;
	
	private List<ApprovalFrameForAppDto> listApprovalFrame;
	
	public static ApprovalPhaseStateForAppDto fromApprovalPhaseStateImport(PhaseSttHrImport approvalPhaseStateImport){
		return new ApprovalPhaseStateForAppDto(
				approvalPhaseStateImport.getPhaseOrder(), 
				approvalPhaseStateImport.getApprovalAtr(),
				"",
				approvalPhaseStateImport.getLstApprovalFrame().stream().map(x -> ApprovalFrameForAppDto.fromApprovalFrameImport(x)).collect(Collectors.toList()));
	}
}
