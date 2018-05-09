/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.env.dom.mailnoticeset.company;

import java.util.List;

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
	 * Update.
	 *
	 * @param userInfo the user info
	 */
	public void update(List<UserInfoUseMethod> lstUserInfo);

	/**
	 * Creates the.
	 *
	 * @param userInfo the user info
	 */
	public void create(List<UserInfoUseMethod> lstUserInfo);
}
