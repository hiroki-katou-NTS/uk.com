package nts.uk.ctx.at.request.app.find.setting.company.request.applicationsetting.apptypesetting;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.dom.setting.company.request.applicationsetting.apptypesetting.AppTypeSetting;

@AllArgsConstructor
@NoArgsConstructor
public class AppTypeSettingDto {
	/**
	 * 事前事後区分の初期表示
	 */
	public int displayInitialSegment;
	
	/**
	 * 事前事後区分を変更できる
	 */
	public Boolean canClassificationChange;
	
	/**
	 * 定型理由の表示
	 */
	public int displayFixedReason;
	
	/**
	 * 承認処理時に自動でメールを送信する
	 */
	public Boolean sendMailWhenApproval;
	
	/**
	 * 新規登録時に自動でメールを送信する
	 */
	public Boolean sendMailWhenRegister;
	
	/**
	 * 申請理由の表示
	 */
	public int displayAppReason;
	
	/**
	 * 申請種類
	 */
	public int appType;
	
	public static AppTypeSettingDto fromDomain(AppTypeSetting appTypeSetting) {
		AppTypeSettingDto appTypeSettingDto = new AppTypeSettingDto();
		appTypeSettingDto.displayInitialSegment = appTypeSetting.getDisplayInitialSegment().value;
		appTypeSettingDto.canClassificationChange = appTypeSetting.getCanClassificationChange();
		appTypeSettingDto.displayFixedReason = appTypeSetting.getDisplayFixedReason().value;
		appTypeSettingDto.sendMailWhenApproval = appTypeSetting.getSendMailWhenApproval();
		appTypeSettingDto.sendMailWhenRegister = appTypeSetting.getSendMailWhenRegister();
		appTypeSettingDto.displayAppReason = appTypeSetting.getDisplayAppReason().value;
		appTypeSettingDto.appType = appTypeSetting.getAppType().value;
		return appTypeSettingDto;
	}
}
