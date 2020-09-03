package nts.uk.ctx.sys.portal.app.find.logsettings;

import lombok.Data;
import nts.uk.ctx.sys.portal.dom.enums.MenuClassification;
import nts.uk.ctx.sys.portal.dom.logsettings.LogSetting;
import nts.uk.shr.com.enumcommon.NotUseAtr;
import nts.uk.ctx.sys.portal.dom.enums.System;

@Data
public class LogSettingDto implements LogSetting.MementoSetter {

	/**
	 * システム
	 */
	private System system;

	/** プログラムID **/
	private String programId;

	/**
	 * メニュー分類
	 */
	private MenuClassification menuClassification;

	/**
	 * ログイン履歴記録
	 */
	private NotUseAtr loginHistoryRecord;

	/**
	 * 会社ID
	 */
	private String companyId;

	/**
	 * 修正履歴（データ）記録
	 */
	private NotUseAtr editHistoryRecord;

	/**
	 * 起動履歴記録
	 */
	private NotUseAtr bootHistoryRecord;
	
	public static LogSettingDto fromDomain(LogSetting logSetting) {
		LogSettingDto logSettingDto = new LogSettingDto();
		logSettingDto.system = logSetting.getSystem();
		logSettingDto.programId = logSetting.getProgramId();
		logSettingDto.menuClassification = logSetting.getMenuClassification();
		logSettingDto.loginHistoryRecord = logSetting.getLoginHistoryRecord();
		logSettingDto.companyId = logSetting.getCompanyId();
		logSettingDto.editHistoryRecord = logSetting.getEditHistoryRecord();
		logSettingDto.bootHistoryRecord = logSetting.getBootHistoryRecord();
		return logSettingDto;
	}

}
