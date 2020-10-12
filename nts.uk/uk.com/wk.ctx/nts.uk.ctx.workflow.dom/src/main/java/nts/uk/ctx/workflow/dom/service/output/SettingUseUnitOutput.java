package nts.uk.ctx.workflow.dom.service.output;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.workflow.dom.approvermanagement.setting.ApprovalSetting;
import nts.uk.ctx.workflow.dom.approvermanagement.setting.HrApprovalRouteSettingWF;

/**
 * Refactor5
 * UKDesign.UniversalK.共通.CMM_マスタメンテナンス.CMM018_承認者の登録.CMM018_承認者の登録（就業・人事）.Q：利用単位の設定.アルゴリズム.起動処理.利用単位の設定の情報
 * @author hoangnd
 *
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
// 利用単位の設定の情報
public class SettingUseUnitOutput {
	// 起動時新規モードにする
	private Boolean mode;
	// 承認設定
	Optional<ApprovalSetting> approvalSetting = Optional.empty();
	// 人事承認ルート設定
	Optional<HrApprovalRouteSettingWF> hrApprovalRouteSetting = Optional.empty();
}
