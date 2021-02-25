package nts.uk.ctx.at.record.dom.stamp.application;

import java.util.List;
import java.util.Optional;

/**
 * 
 * @author sonnlb
 * 
 *         打刻機能の利用設定Repository
 *
 */
public interface SettingsUsingEmbossingRepository {
	/**
	 * [1] insert(打刻機能の利用設定)
	 * 
	 * @param 打刻機能の利用設定
	 *            domain
	 */
	public void insert(SettingsUsingEmbossing domain);

	/**
	 * [2] save(打刻機能の利用設定)
	 * 
	 * @param 打刻機能の利用設定
	 *            domain
	 */
	public void save(SettingsUsingEmbossing domain);

	/**
	 * [3] get
	 * 
	 * @param 会社ID
	 *            comppanyID
	 * @return 打刻機能の利用設定 Optional<打刻機能の利用設定>
	 * 
	 *         打刻機能の利用設定を取得する
	 */
	public Optional<SettingsUsingEmbossing> get(String comppanyID);
	
	
	/**
	 * <<ScreenQuery>> 打刻入力の会社一覧を取得する
	 * @param companyIds
	 * @return List<SettingsUsingEmbossing>
	 */
	public List<SettingsUsingEmbossing> getSettingEmbossingByComIds(List<String> companyIds);
}
