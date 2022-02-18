/**
 * 
 */
package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.settingforsmartphone;

import java.util.Optional;

/**
 * @author laitv
 * スマホ打刻の打刻設定Repository
 *
 */
public interface SettingsSmartphoneStampRepository {

	/**
	 * [1]  insert(スマホ打刻の打刻設定)
	 * @param StampSettingOfRICOHCopier
	 */
	public void insert(SettingsSmartphoneStamp settingsSmartphoneStamp);

	/**
	 * [2]  save(スマホ打刻の打刻設定)																									
	 * @param settingsSmartphoneStamp
	 */
	public void save(SettingsSmartphoneStamp settingsSmartphoneStamp);
	
	/**
	 * [3]  取得する
	 * @param cid
	 */
	public Optional<SettingsSmartphoneStamp> get(String cid , String sId);

}
