package nts.uk.ctx.workflow.app.command.approvermanagement.setting;

import lombok.AllArgsConstructor;
import nts.uk.ctx.workflow.dom.approvermanagement.setting.HrApprovalRouteSettingWF;

@AllArgsConstructor
public class HrApprovalRouteSettingWFCommand {
	// 会社
	public boolean comMode;
	// 会社ID
	public String cid; 
	// 個人
	public boolean empMode; 
	// 部門
	public boolean devMode;
	
	public HrApprovalRouteSettingWF toDomain() {
		return new HrApprovalRouteSettingWF(comMode, cid, empMode, devMode);
	}
}
