package nts.uk.ctx.sys.portal.app.find.logsettings;

import lombok.Data;
import nts.uk.ctx.sys.portal.dom.enums.MenuClassification;
import nts.uk.ctx.sys.portal.dom.logsettings.LogSetting;
import nts.uk.shr.com.enumcommon.NotUseAtr;

@Data
public class LogSettingDto implements LogSetting.MementoSetter {

	/**
	 * システム
	 */
	private int system;

	/** プログラムID **/
	private String programId;

	/**
	 * メニュー分類
	 */
	private int menuClassification;

	/**
	 * ログイン履歴記録
	 */
	private int loginHistoryRecord;

	/**
	 * 会社ID
	 */
	private String companyId;

	/**
	 * 修正履歴（データ）記録
	 */
	private int editHistoryRecord;

	/**
	 * 起動履歴記録
	 */
	private int bootHistoryRecord;
	
	public static LogSettingDto fromDomain(LogSetting logSetting) {
		LogSettingDto logSettingDto = new LogSettingDto();
		logSettingDto.system = logSetting.getSystem();
		logSettingDto.programId = logSetting.getProgramId();
		logSettingDto.menuClassification = logSetting.getMenuClassification().value;
		logSettingDto.loginHistoryRecord = logSetting.getLoginHistoryRecord().value;
		logSettingDto.companyId = logSetting.getCompanyId();
		logSettingDto.editHistoryRecord = logSetting.getEditHistoryRecord().value;
		logSettingDto.bootHistoryRecord = logSetting.getBootHistoryRecord().value;
		return logSettingDto;
	}
	
	public static LogSetting toDomain(LogSettingDto logSettingDto) {
		LogSetting logSetting = new LogSetting();
		logSetting.setSystem(logSettingDto.getSystem());
		logSetting.setProgramId(logSettingDto.getProgramId());
		logSetting.setMenuClassification(MenuClassification.valueOf(logSettingDto.getMenuClassification()));
		logSetting.setLoginHistoryRecord(NotUseAtr.valueOf(logSettingDto.getLoginHistoryRecord()));
		logSetting.setCompanyId(logSettingDto.getCompanyId());
		logSetting.setEditHistoryRecord(NotUseAtr.valueOf(logSettingDto.getEditHistoryRecord()));
		logSetting.setBootHistoryRecord(NotUseAtr.valueOf(logSettingDto.getBootHistoryRecord()));
		return logSetting;
	}

	@Override
	public void setMenuClassification(Integer menuClassification) {
		this.setMenuClassification(menuClassification);
	}

	@Override
	public void setLoginHistoryRecord(Integer loginHistoryRecord) {
		this.setLoginHistoryRecord(loginHistoryRecord);
	}

	@Override
	public void setEditHistoryRecord(Integer editHistoryRecord) {
		this.setEditHistoryRecord(editHistoryRecord);
	}

	@Override
	public void setBootHistoryRecord(Integer bootHistoryRecord) {
		this.setBootHistoryRecord(bootHistoryRecord);
	}
	

	@Override
	public void setSystem(int system) {
		this.setSystem(system);
	}

	@Override
	public void setProgramId(String programId) {
		this.setProgramId(programId);
	}

	@Override
	public void setCompanyId(String companyId) {
		this.setCompanyId(companyId);
	}
}
