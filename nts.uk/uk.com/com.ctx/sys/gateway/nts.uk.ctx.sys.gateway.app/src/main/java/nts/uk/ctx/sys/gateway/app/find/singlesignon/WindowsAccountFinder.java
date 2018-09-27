/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.gateway.app.find.singlesignon;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.sys.gateway.dom.singlesignon.WindowsAccount;
import nts.uk.ctx.sys.gateway.dom.singlesignon.WindowsAccountRepository;

/**
 * The Class WindowAccountFinder.
 */
@Stateless
public class WindowsAccountFinder {

	/** The window account repository. */
	@Inject
	private WindowsAccountRepository windowAccountRepository;

	/**
	 * Find window account by user id.
	 *
	 * @param userId
	 *            the user id
	 * @return the windown account finder dto
	 */
	public List<WindowsAccountFinderDto> findWindowAccountByUserId(String userId) {

		Optional<WindowsAccount> optWindowAccount = this.windowAccountRepository
				.findListWindowAccountByUserId(userId);

		// Check exist
		if (optWindowAccount.isPresent()) {
			WindowsAccount windowsAccount = optWindowAccount.get();
			return windowsAccount.getAccountInfos().stream()
					.map(accInfo -> new WindowsAccountFinderDto(userId, accInfo.getHostName().v(),
							accInfo.getUserName().v(), accInfo.getNo(), accInfo.getUseAtr().value))
					.collect(Collectors.toList());
		}

		// Return
		return Collections.emptyList();

	}

	/**
	 * Find already setting.
	 *
	 * @param userIds the user ids
	 * @return the list
	 */
	public List<String> findAlreadySetting(List<String> userIds) {
		return windowAccountRepository.findByListUserId(userIds).stream().map(WindowsAccount::getUserId)
				.collect(Collectors.toList());
	}

}
