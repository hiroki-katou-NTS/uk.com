package nts.uk.ctx.hr.notice.app.find.report.regis.person;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.hr.shared.dom.adapter.EmployeeInformationImport;
import nts.uk.ctx.hr.shared.dom.approval.rootstate.ApprovalBehaviorAtrHrExport;
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
	
	public static ApprovalPhaseStateForAppDto fromApprovalPhaseStateImport(PhaseSttHrImport approvalPhaseStateImport, Map<String, List<EmployeeInformationImport>> employeeInfoMaps){
		
		ApprovalBehaviorAtrHrExport status = EnumAdaptor.valueOf(approvalPhaseStateImport.getApprovalAtr(), ApprovalBehaviorAtrHrExport.class);
		
		List<ApprovalFrameForAppDto> framAppLst = approvalPhaseStateImport.getLstApprovalFrame()

				.stream().map(x -> ApprovalFrameForAppDto.fromApprovalFrameImport(x, employeeInfoMaps))

				.collect(Collectors.toList());
		
		framAppLst.sort(Comparator.comparing(ApprovalFrameForAppDto::getFrameOrder));
		
		return new ApprovalPhaseStateForAppDto(
				
				approvalPhaseStateImport.getPhaseOrder(), 
				
				approvalPhaseStateImport.getApprovalAtr(),
				
				status.nameId,
				
				framAppLst
				);
	}
}
