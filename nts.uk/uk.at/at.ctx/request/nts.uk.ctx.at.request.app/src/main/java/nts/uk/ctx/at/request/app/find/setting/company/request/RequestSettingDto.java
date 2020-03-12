package nts.uk.ctx.at.request.app.find.setting.company.request;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.app.find.setting.company.request.applicationsetting.ApplicationSettingDto;
import nts.uk.ctx.at.request.app.find.setting.company.request.appreflect.AppReflectionSettingDto;
import nts.uk.ctx.at.request.app.find.setting.company.request.approvallistsetting.AppReflectAfterConfirmDto;
import nts.uk.ctx.at.request.app.find.setting.company.request.approvallistsetting.ApprovalListDisplaySetDto;
import nts.uk.ctx.at.request.dom.setting.company.request.AuthorizationSetting;
import nts.uk.ctx.at.request.dom.setting.company.request.RequestSetting;

@AllArgsConstructor
@NoArgsConstructor
public class RequestSettingDto {
	
	public String companyID;
	
	/**
	 * 申請設定
	 */
	public ApplicationSettingDto applicationSetting;
	
	/**
	 * 申請反映設定
	 */
	public AppReflectionSettingDto appReflectionSetting;
	
	/**
	 * 承認一覧表示設定
	 */
	public ApprovalListDisplaySetDto approvalListDisplaySetting;
	
	/**
	 * 承認設定
	 */
	public AuthorizationSetting authorizationSetting;
	
	/**
	 * データが確立が確定されている場合の承認済申請の反映
	 */
	public AppReflectAfterConfirmDto appReflectAfterConfirm;
	
	public static RequestSettingDto fromDomain(RequestSetting requestSetting) {
		RequestSettingDto requestSettingDto = new RequestSettingDto();
		requestSettingDto.companyID = requestSetting.getCompanyID();
		requestSettingDto.applicationSetting = ApplicationSettingDto.fromDomain(requestSetting.getApplicationSetting());
		requestSettingDto.appReflectionSetting = AppReflectionSettingDto.fromDomain(requestSetting.getAppReflectionSetting());
		requestSettingDto.approvalListDisplaySetting = ApprovalListDisplaySetDto.fromDomain(requestSetting.getApprovalListDisplaySetting());
		requestSettingDto.authorizationSetting = requestSetting.getAuthorizationSetting();
		requestSettingDto.appReflectAfterConfirm = AppReflectAfterConfirmDto.fromDomain(requestSetting.getAppReflectAfterConfirm());
		return requestSettingDto;
	}
}
