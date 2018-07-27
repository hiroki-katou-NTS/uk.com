/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.env.dom.mailnoticeset.company;

import java.util.List;
import java.util.Optional;

import nts.uk.ctx.sys.env.dom.mailnoticeset.employee.UserInfoItem;

/**
 * The Interface UserInfoUseMethodRepository.
 */
public interface UserInfoUseMethodRepository {

	/**
	 * Find by company id.
	 *
	 * @param companyId the company id
	 * @return the list
	 */
	public List<UserInfoUseMethod> findByCompanyId(String companyId);

	/**
	 * Find by company id and setting item.
	 *
	 * @param companyId the company id
	 * @param settingItem the setting item
	 * @return the optional
	 */
	public Optional<UserInfoUseMethod> findByCompanyIdAndSettingItem(String companyId, UserInfoItem settingItem);

	/**
	 * Update.
	 *
	 * @param lstUserInfo the lst user info
	 */
	public void update(List<UserInfoUseMethod> lstUserInfo);

	/**
	 * Creates the.
	 *
	 * @param lstUserInfo the lst user info
	 */
	public void create(List<UserInfoUseMethod> lstUserInfo);
}
