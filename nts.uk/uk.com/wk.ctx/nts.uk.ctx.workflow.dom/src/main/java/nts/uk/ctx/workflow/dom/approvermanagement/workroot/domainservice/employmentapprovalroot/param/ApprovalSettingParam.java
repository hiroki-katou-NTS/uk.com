package nts.uk.ctx.workflow.dom.approvermanagement.workroot.domainservice.employmentapprovalroot.param;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Value;

/**
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.ワークフロー.承認者管理.就業人事申請承認ルート.承認者設定パラメータ
 */
@Value
@AllArgsConstructor
public class ApprovalSettingParam {
	
	/**
	 * 承認フェーズ
	 */
	private List<ApproverInformation> approvalPhases;

	/**
	 * 承認ルート
	 */
	private ApprovalRootInformation approvalRootInfo;
}
