package nts.uk.ctx.workflow.app.command.approvermanagement.setting;

import java.util.Optional;

import lombok.AllArgsConstructor;
import nts.uk.ctx.workflow.dom.service.output.SettingUseUnitOutput;

@AllArgsConstructor
public class SettingUseUnitCommand {
	// 起動時新規モードにする
	public Boolean mode;
	// 承認設定
	public ApprovalSettingCommand approvalSetting;
	// 人事承認ルート設定
	public HrApprovalRouteSettingWFCommand hrApprovalRouteSetting;
	
	public SettingUseUnitOutput toDomain() {
		return new SettingUseUnitOutput(
				mode, 
				approvalSetting != null ? Optional.of(approvalSetting.toDomain()) : Optional.empty(), 
				hrApprovalRouteSetting != null ? Optional.of(hrApprovalRouteSetting.toDomain()): Optional.empty());
	}
}
