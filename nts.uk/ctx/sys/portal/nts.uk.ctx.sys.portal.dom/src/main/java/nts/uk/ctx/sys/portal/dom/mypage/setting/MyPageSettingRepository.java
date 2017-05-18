/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.portal.dom.mypage.setting;

import java.util.Optional;

/**
 * The Interface MyPageSettingRepository.
 */
public interface MyPageSettingRepository {
	
	/**
	 * Find by company id.
	 *
	 * @param CompanyId the company id
	 * @return the optional
	 */
	Optional<MyPageSetting> findByCompanyId(String CompanyId);
	
	/**
	 * Find top page part use setting by id.
	 *
	 * @param companyId the company id
	 * @param topPagePartId the top page part id
	 * @return the optional
	 */
	Optional<TopPagePartUseSetting> findTopPagePartUseSettingById(String companyId, String topPagePartId);
	
	/**
	 * Removes the top page part use setting by id.
	 *
	 * @param companyId the company id
	 * @param topPagePartId the top page part id
	 */
	void removeTopPagePartUseSettingById(String companyId, String topPagePartId);
	/**
	 * Update.
	 *
	 * @param myPageSetting the my page setting
	 */
	void update(MyPageSetting myPageSetting);
	
	/**
	 * Adds the.
	 *
	 * @param myPageSetting the my page setting
	 */
	void add(MyPageSetting myPageSetting);
}
