package nts.uk.ctx.workflow.app.find.approvermanagement.workroot;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
@Data
@AllArgsConstructor
public class ApprovalPhaseDto {

	private List<ApproverDto> approver;
	/**承認ID*/
	private String approvalId;
	/**承認フェーズ順序*/
	private int phaseOrder;
	/**承認形態*/
	private Integer approvalForm;
	/**承認形態 Name*/
	private String appFormName;
	/**閲覧フェーズ*/
	private Integer browsingPhase;
	/**承認者指定区分*/
	private int approvalAtr;
}
