package nts.uk.screen.at.app.dailyperformance.correction.dto.checkapproval;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.record.dom.adapter.workflow.service.enums.ApproverEmployeeState;
import nts.uk.ctx.at.record.dom.adapter.workflow.service.enums.ReleasedProprietyDivision;


@NoArgsConstructor
@Getter
public class ApproveRootStatusForEmpDto {
	@Setter
	private ApproverEmployeeState approverEmployeeState;
	
	@Setter
	private ReleasedProprietyDivision approvalStatus;
	
	@Setter
	private boolean checkApproval;

	public ApproveRootStatusForEmpDto(ApproverEmployeeState approverEmployeeState, boolean checkBox) {
		this.approverEmployeeState = approverEmployeeState;
		this.checkApproval = checkBox;
	}
	
}
