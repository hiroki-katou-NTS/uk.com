package nts.uk.ctx.workflow.app.find.approvermanagement.workroot;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
@Data
@AllArgsConstructor
public class ApprovalPhaseDto {

	private List<ApproverDto> approver;
	/**分岐ID*/
	private String branchId;
	/**承認フェーズID*/
	private String approvalPhaseId;
	/**承認形態*/
	private Integer approvalForm;
	/**承認形態 Name*/
	private String appFormName;
	/**閲覧フェーズ*/
	private Integer browsingPhase;
	/**順序*/
	private Integer orderNumber;
	
}
