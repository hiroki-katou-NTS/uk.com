package nts.uk.ctx.workflow.dom.approvermanagement.setting;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Refactor5
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".ワークフロー.承認者管理.承認設定.承認設定
 * @author hoangnd
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ApproveSetting {
	// 会社ID
	private String companyId;
	
	// 承認単位の利用設定
	private ApproverRegisterSet approverRegsterSet;
	
	// 本人による承認
	private Boolean principalApprovalFlg;
	
}
