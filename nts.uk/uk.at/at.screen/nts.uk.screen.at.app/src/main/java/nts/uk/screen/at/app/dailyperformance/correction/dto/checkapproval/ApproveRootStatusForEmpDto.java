package nts.uk.screen.at.app.dailyperformance.correction.dto.checkapproval;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.record.dom.adapter.workflow.service.dtos.ApprovalStatus;
import nts.uk.ctx.at.record.dom.adapter.workflow.service.dtos.ApproveRootStatusForEmpImport;


@NoArgsConstructor
@Getter
public class ApproveRootStatusForEmpDto extends ApproveRootStatusForEmpImport{
	@Setter
	private ApprovalStatus approvalStatusEmployee;

	public ApproveRootStatusForEmpDto(ApproveRootStatusForEmpImport approve, ApprovalStatus approvalStatusEmployee) {
		super(approve.getEmployeeID(), approve.getAppDate(), approve.getApprovalStatus());
		this.approvalStatusEmployee = approvalStatusEmployee;
	}
	
}
