package nts.uk.ctx.sys.portal.dom.mypage.setting;

import java.util.Optional;

public interface MyPageSettingRepository {
	
	/**
	 * Find by company id.
	 *
	 * @param CompanyId the company id
	 * @return the optional
	 */
	Optional<MyPageSetting> findByCompanyId(String CompanyId);
	
	/**
	 * Update.
	 *
	 * @param myPageSetting the my page setting
	 */
	void update(MyPageSetting myPageSetting);
}
