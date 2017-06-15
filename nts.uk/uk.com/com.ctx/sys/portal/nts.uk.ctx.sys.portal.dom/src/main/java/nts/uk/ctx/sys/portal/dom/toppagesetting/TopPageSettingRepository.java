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
	 * @return
	 */
	Optional<TopPageSetting> findByCId(String companyId);

	/**
	 * 
	 * @param topPageSetting
	 */
	public void add(TopPageSetting topPageSetting);
}
