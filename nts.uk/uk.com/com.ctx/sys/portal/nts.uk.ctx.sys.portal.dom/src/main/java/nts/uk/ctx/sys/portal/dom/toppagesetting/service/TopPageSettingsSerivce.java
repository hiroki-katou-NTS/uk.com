package nts.uk.ctx.sys.portal.dom.toppagesetting.service;

import java.util.Optional;

import nts.uk.ctx.sys.portal.dom.toppagesetting.TopPageSettings;

/**
 * The Interface TopPageSettingsSerivce.
 * DomainService トップページ設定を取得する
 */
public interface TopPageSettingsSerivce {

	/**
	 * Gets the top page settings.
	 * 自分のトップページ設定を取得する
	 *
	 * @return the top page settings
	 */
	public Optional<TopPageSettings> getTopPageSettings();
}
