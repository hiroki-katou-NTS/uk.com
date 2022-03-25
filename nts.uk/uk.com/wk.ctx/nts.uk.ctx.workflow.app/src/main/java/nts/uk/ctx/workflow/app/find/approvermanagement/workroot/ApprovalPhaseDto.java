package nts.uk.ctx.workflow.app.find.approvermanagement.workroot;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ApprovalPhase;

@Data
@AllArgsConstructor
public class ApprovalPhaseDto {

	private List<ApproverDto> approver;
	/** 承認ID */
	private String approvalId;
	/** 承認フェーズ順序 */
	private int phaseOrder;
	/** 承認形態 */
	private Integer approvalForm;
	/** 承認形態 Name */
	private String appFormName;
	/** 閲覧フェーズ */
	private Integer browsingPhase;
	/** 承認者指定区分 */
	private int approvalAtr;

	public static ApprovalPhaseDto fromDomain(ApprovalPhase domain) {
		return new ApprovalPhaseDto(
				domain.getApprovers().stream().map(data -> ApproverDto.fromDomain(data, null, null, null))
						.collect(Collectors.toList()),
				domain.getApprovalId(), domain.getPhaseOrder(), domain.getApprovalForm().value, null,
				domain.getBrowsingPhase(), domain.getApprovalAtr().value);
	}
}
