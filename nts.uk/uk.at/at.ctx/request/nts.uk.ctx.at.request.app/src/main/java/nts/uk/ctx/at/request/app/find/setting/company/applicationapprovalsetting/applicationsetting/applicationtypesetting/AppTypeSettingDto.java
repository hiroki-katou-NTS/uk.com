package nts.uk.ctx.at.request.app.find.setting.company.applicationapprovalsetting.applicationsetting.applicationtypesetting;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.applicationtypesetting.AppTypeSetting;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.applicationtypesetting.PrePostInitAtr;

/**
 * refactor 4
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Getter
public class AppTypeSettingDto {
	/**
	 * 申請種類
	 */
	private int appType;
	
	/**
	 * 新規登録時に自動でメールを送信する
	 */
	private boolean sendMailWhenRegister;
	
	/**
	 * 承認処理時に自動でメールを送信する
	 */
	private boolean sendMailWhenApproval;
	
	/**
	 * 事前事後区分の初期表示
	 */
	private int displayInitialSegment;
	
	/**
	 * 事前事後区分を変更できる
	 */
	private boolean canClassificationChange;
	
	public static AppTypeSettingDto fromDomain(AppTypeSetting appTypeSetting) {
		return new AppTypeSettingDto(
				appTypeSetting.getAppType().value, 
				appTypeSetting.isSendMailWhenRegister(), 
				appTypeSetting.isSendMailWhenApproval(), 
				appTypeSetting.getDisplayInitialSegment().value, 
				appTypeSetting.isCanClassificationChange());
	}
	
	public AppTypeSetting toDomain() {
		return new AppTypeSetting(
				EnumAdaptor.valueOf(appType, ApplicationType.class), 
				sendMailWhenRegister, 
				sendMailWhenApproval, 
				EnumAdaptor.valueOf(displayInitialSegment, PrePostInitAtr.class), 
				canClassificationChange);
	}
}
