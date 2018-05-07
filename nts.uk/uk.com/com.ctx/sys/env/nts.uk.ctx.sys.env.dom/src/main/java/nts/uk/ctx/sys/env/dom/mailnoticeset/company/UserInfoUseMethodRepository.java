/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
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
}
