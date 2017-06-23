package nts.uk.ctx.sys.portal.dom.toppagesetting;

import java.util.Optional;

/**
 * 
 * @author sonnh1
 *
 */
public interface TopPageSettingRepository {
	/**
	 * 
	 * @param companyId
	 * @return toppageSetting or null
	 */
	Optional<TopPageSetting> findByCId(String companyId);

	/**
	 * insert data to TOPPAGE_SETTING
	 * 
	 * @param topPageSetting
	 */
	public void insert(TopPageSetting topPageSetting);

	/**
	 * update data to TOPPAGE_SETTING
	 * 
	 * @param topPageSetting
	 */
	public void update(TopPageSetting topPageSetting);
}
