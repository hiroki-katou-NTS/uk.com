/**
 * 
 */
package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput;

import java.util.Optional;

/**
 * @author laitv
 * スマホ打刻の打刻設定Repository
 *
 */
public interface SettingsSmartphoneStampRepository {

	/**
	 * [1]  insert(スマホ打刻の打刻設定)
	 * @param SettingsSmartphoneStamp
	 */
	public void insert(SettingsSmartphoneStamp settingsSmartphoneStamp);

	/**
	 * [2]  update(スマホ打刻の打刻設定)																									
	 * @param settingsSmartphoneStamp
	 */
	public void update(SettingsSmartphoneStamp settingsSmartphoneStamp);
	
	
	/**
	 * [3]  取得する
	 * @param cid
	 */
	public Optional<SettingsSmartphoneStamp> get(String cid);

}
