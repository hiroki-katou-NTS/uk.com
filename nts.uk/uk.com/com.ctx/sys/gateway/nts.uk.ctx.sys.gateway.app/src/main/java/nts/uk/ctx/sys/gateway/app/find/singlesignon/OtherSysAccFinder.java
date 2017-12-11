/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.gateway.app.find.singlesignon;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.sys.gateway.dom.singlesignon.OtherSysAccount;
import nts.uk.ctx.sys.gateway.dom.singlesignon.OtherSysAccountRepository;

/**
 * The Class OtherSysAccFinder.
 */
@Stateless
public class OtherSysAccFinder {

	/** The other sys account repository. */
	@Inject
	private OtherSysAccountRepository otherSysAccountRepository;

	/**
	 * Find by user id.
	 *
	 * @param userId
	 *            the user id
	 * @return the other sys acc finder dto
	 */
	public OtherSysAccFinderDto findByUserId(String userId) {
		
		OtherSysAccFinderDto otherSysAccFinderDto = new OtherSysAccFinderDto();
		Optional<OtherSysAccount> opOtherSysAcc = otherSysAccountRepository.findByUserId(userId);
		if (opOtherSysAcc.isPresent()) {
			opOtherSysAcc.get().saveToMemento(otherSysAccFinderDto);
		}
		
		return otherSysAccFinderDto;
	}
	
}
