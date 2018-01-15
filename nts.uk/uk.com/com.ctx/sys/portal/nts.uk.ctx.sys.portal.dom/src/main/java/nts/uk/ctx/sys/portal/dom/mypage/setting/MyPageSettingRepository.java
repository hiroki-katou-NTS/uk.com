/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.portal.dom.mypage.setting;

import java.util.List;
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
	 * Find top page part use setting by company id.
	 *
	 * @param CompanyId the company id
	 * @return the list
	 */
	List<TopPagePartUseSetting> findTopPagePartUseSettingByCompanyId(String companyId);
	
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
	 * Adds the top page part use setting.
	 *
	 * @param topPagePartUseSetting the top page part use setting
	 */
	void addTopPagePartUseSetting(TopPagePartUseSetting topPagePartUseSetting);
	/**
	 * hoatt
	 * find my page setting
	 * @param companyId
	 * @return
	 */
	Optional<MyPageSetting> findMyPageSet(String companyId);
	
}
