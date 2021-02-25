package nts.uk.ctx.workflow.dom.approvermanagement.setting;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Refactor5
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.申請承認.設定.人事承認ルート設定
 * @author hoangnd
 *
 */
@NoArgsConstructor
@Data
public class HrApprovalRouteSettingWF {
	// 会社
	public boolean comMode;
	// 会社ID
	public String cid; 
	// 個人
	public boolean empMode; 
	// 部門
	public boolean devMode;

	public HrApprovalRouteSettingWF(boolean comMode, String cid, boolean empMode, boolean devMode) {
		super();
		this.cid = cid;
		this.comMode = comMode;
		this.devMode = devMode;
		this.empMode = empMode;
	}

	public static HrApprovalRouteSettingWF createFromJavaType(boolean comMode, String cid, boolean empMode, boolean devMode) {
		return new HrApprovalRouteSettingWF(comMode, cid, empMode, devMode);
	}
}
