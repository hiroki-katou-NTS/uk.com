package nts.uk.ctx.at.record.dom.stamp.application;

import java.util.Optional;

import nts.uk.ctx.at.record.dom.stamp.application.SettingsUsingEmbossing;

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
}
