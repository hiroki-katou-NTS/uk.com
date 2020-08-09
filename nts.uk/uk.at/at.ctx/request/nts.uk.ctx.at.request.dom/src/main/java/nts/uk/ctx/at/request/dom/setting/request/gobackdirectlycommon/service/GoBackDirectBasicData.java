package nts.uk.ctx.at.request.dom.setting.request.gobackdirectlycommon.service;

import java.util.Optional;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.request.dom.application.common.datawork.DataWork;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.output.AppCommonSettingOutput;
import nts.uk.ctx.at.request.dom.setting.request.gobackdirectlycommon.GoBackDirectlyCommonSetting;

/**
 * 直行直帰基本データ
 * @author ducpm
 *
 */
@Getter
@Setter
public class GoBackDirectBasicData {
	Optional<GoBackDirectlyCommonSetting> goBackDirectSet;
	String employeeName;
	String sID;
	// List<ApplicationReason> listAppReason;
	AppCommonSettingOutput appCommonSettingOutput;
	boolean isDutiesMulti;
	/**
	 * 勤務就業ダイアログ用データ取得
	 */
	private DataWork workingData;	
}
