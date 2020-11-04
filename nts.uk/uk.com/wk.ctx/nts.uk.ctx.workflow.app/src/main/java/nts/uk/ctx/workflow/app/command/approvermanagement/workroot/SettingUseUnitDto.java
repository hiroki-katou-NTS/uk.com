package nts.uk.ctx.workflow.app.command.approvermanagement.workroot;


import lombok.AllArgsConstructor;
import nts.uk.ctx.workflow.app.find.approvermanagement.setting.ApprovalSettingDto;
import nts.uk.ctx.workflow.app.find.approvermanagement.setting.HrApprovalRouteSettingWFDto;
import nts.uk.ctx.workflow.dom.service.output.SettingUseUnitOutput;

@AllArgsConstructor
public class SettingUseUnitDto {
	// 起動時新規モードにする
	public Boolean mode;
	// 承認設定
	public ApprovalSettingDto approvalSetting;
	// 人事承認ルート設定
	public HrApprovalRouteSettingWFDto hrApprovalRouteSetting;
	
	public static SettingUseUnitDto fromDomain(SettingUseUnitOutput settingUseUnitOutput) {
		return new SettingUseUnitDto(
				settingUseUnitOutput.getMode(), 
				ApprovalSettingDto.fromDomain(settingUseUnitOutput.getApprovalSetting().orElse(null)), 
				HrApprovalRouteSettingWFDto.fromDomain(settingUseUnitOutput.getHrApprovalRouteSetting().orElse(null)));
	}
}
