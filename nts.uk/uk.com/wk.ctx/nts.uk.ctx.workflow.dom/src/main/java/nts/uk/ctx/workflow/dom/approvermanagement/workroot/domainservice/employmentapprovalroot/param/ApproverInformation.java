package nts.uk.ctx.workflow.dom.approvermanagement.workroot.domainservice.employmentapprovalroot.param;

import lombok.AllArgsConstructor;
import lombok.Value;

/**
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.ワークフロー.承認者管理.就業人事申請承認ルート.承認者情報
 */
@Value
@AllArgsConstructor
public class ApproverInformation {

	/**
	 * フェーズの順序
	 */
	private int phaseOrder;
	
	/**
	 * 承認者ID
	 */
	private String approverId;
}
