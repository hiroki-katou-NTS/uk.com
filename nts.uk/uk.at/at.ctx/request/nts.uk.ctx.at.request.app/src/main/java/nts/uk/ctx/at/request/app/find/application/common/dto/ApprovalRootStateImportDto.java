package nts.uk.ctx.at.request.app.find.application.common.dto;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApprovalRootStateImport_New;

/**
 * refactor 5
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Getter
public class ApprovalRootStateImportDto {
	
	private String rootStateID;
	
	private List<ApprovalPhaseStateForAppDto> listApprovalPhaseState;
	
	private GeneralDate date;
	
	private int rootType;
	
	private String employeeID;
	
	public static ApprovalRootStateImportDto fromDomain(ApprovalRootStateImport_New approvalRootStateImport) {
		return new ApprovalRootStateImportDto(
				approvalRootStateImport.getRootStateID(), 
				approvalRootStateImport.getListApprovalPhaseState().stream().map(x -> ApprovalPhaseStateForAppDto.fromApprovalPhaseStateImport(x)).collect(Collectors.toList()), 
				approvalRootStateImport.getDate(),
				approvalRootStateImport.getRootType(),
				approvalRootStateImport.getEmployeeID());
	}
	
	public ApprovalRootStateImport_New toDomain() {
		return new ApprovalRootStateImport_New(
				rootStateID, 
				listApprovalPhaseState.stream().map(x -> x.toDomain()).collect(Collectors.toList()), 
				date,
				rootType,
				employeeID);
	}
}
