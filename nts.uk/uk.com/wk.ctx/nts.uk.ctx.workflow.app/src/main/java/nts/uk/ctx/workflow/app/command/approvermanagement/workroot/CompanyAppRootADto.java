package nts.uk.ctx.workflow.app.command.approvermanagement.workroot;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;
import nts.uk.ctx.workflow.app.find.approvermanagement.workroot.ApprovalPhaseDto;
@Data
@NoArgsConstructor
public class CompanyAppRootADto {

	private boolean color;
	private int employRootAtr;
	private boolean common;
	private Integer appTypeValue;
	private String appTypeName;
	private String approvalId;
	private String historyId;
	private String branchId;
	private ApprovalPhaseDto appPhase1;
	private ApprovalPhaseDto appPhase2;
	private ApprovalPhaseDto appPhase3;
	private ApprovalPhaseDto appPhase4;
	private ApprovalPhaseDto appPhase5;
}
