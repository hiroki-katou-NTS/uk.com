package nts.uk.ctx.sys.portal.dom.logsettings;

import java.util.List;

public interface LogSettingRepository {
	/**
	 * システムからログ設定を取得
	 * 
	 * @param システム system
	 * @param 会社ID companyId
	 * @return List＜ログ設定＞
	 */
	List<LogSetting> findBySystem(String companyId, int systemType);

	/**
	 * 
	 * @param companyId
	 * @param systemType
	 */
	void deleteLogSetting(String companyId, int systemType);

	void addLogSetting(LogSetting logSetting);
}
