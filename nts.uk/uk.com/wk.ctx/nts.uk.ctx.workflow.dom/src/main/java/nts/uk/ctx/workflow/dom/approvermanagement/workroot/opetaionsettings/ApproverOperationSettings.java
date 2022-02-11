package nts.uk.ctx.workflow.dom.approvermanagement.workroot.opetaionsettings;

import lombok.Getter;

import java.util.List;

import lombok.AllArgsConstructor;
import nts.arc.layer.dom.AggregateRoot;

/**
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.ワークフロー.承認者管理.就業人事申請承認ルート.自分の承認者の運用設定.自分の承認者の運用設定
 * @author NWS-DungDV
 *
 */
@AllArgsConstructor
@Getter
public class ApproverOperationSettings extends AggregateRoot {
	
	/** 運用モード	 */
	private OperationMode operationMode;
	
	/** 利用するレベル	 */
	private ApprovalLevelNo approvalLevelNo;
	
	/** 利用する種類設定 */
	private List<SettingTypeUsed> settingTypeUseds;
	
	/** 自分の承認者設定画面に表示情報 */
	
	
}
