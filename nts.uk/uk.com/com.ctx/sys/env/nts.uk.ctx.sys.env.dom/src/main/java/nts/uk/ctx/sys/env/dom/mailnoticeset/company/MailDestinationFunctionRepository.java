/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.env.dom.mailnoticeset.company;

import nts.uk.ctx.sys.env.dom.mailnoticeset.employee.UserInfoItem;
<<<<<<< HEAD
=======
<<<<<<< HEAD
import nts.uk.shr.com.enumcommon.NotUseAtr;
=======
>>>>>>> delivery/release_user
>>>>>>> pj/at/dev/Team_D/KDL030

/**
 * The Interface MailDestinationFunctionRepository.
 */
public interface MailDestinationFunctionRepository {

	/**
	 * Find by cid and setting item.
	 *
	 * @param companyId the company id
	 * @param userInfoItem the user info item
	 * @return the mail destination function
	 */
	public MailDestinationFunction findByCidAndSettingItem(String companyId, UserInfoItem userInfoItem);

	/**
	 * Adds the.
	 *
	 * @param domain the domain
	 */
	void add(MailDestinationFunction domain);

	/**
	 * Removes the.
	 *
	 * @param companyId the company id
	 * @param userInfoItem the user info item
	 */
	void remove(String companyId, UserInfoItem userInfoItem);
<<<<<<< HEAD
=======
<<<<<<< HEAD
	
	

	
	/**
	 * @param cID
	 * @param functionID
	 * @param use
	 * @return メール送信先機能
	 */
	public MailDestinationFunction findByCidSettingItemAndUse(String cID, Integer functionID, NotUseAtr use);
=======
>>>>>>> delivery/release_user
>>>>>>> pj/at/dev/Team_D/KDL030

}
