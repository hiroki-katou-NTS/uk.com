package nts.uk.ctx.workflow.app.find.approvermanagement.setting;

import lombok.AllArgsConstructor;
import nts.uk.ctx.workflow.dom.approvermanagement.setting.HrApprovalRouteSettingWF;
@AllArgsConstructor
public class HrApprovalRouteSettingWFDto {
	// 会社
	public boolean comMode;
	// 会社ID
	public String cid; 
	// 個人
	public boolean empMode; 
	// 部門
	public boolean devMode;
	
	public static HrApprovalRouteSettingWFDto fromDomain(HrApprovalRouteSettingWF approvalRouteSettingWF) {
		if (approvalRouteSettingWF == null) return null;
		return new HrApprovalRouteSettingWFDto(
				approvalRouteSettingWF.comMode, 
				approvalRouteSettingWF.cid, 
				approvalRouteSettingWF.empMode, 
				approvalRouteSettingWF.devMode);
	}
}
