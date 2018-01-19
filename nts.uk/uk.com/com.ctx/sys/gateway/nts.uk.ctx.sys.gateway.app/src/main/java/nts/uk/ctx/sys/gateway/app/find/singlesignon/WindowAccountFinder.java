/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.gateway.app.find.singlesignon;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.sys.gateway.dom.singlesignon.UseAtr;
import nts.uk.ctx.sys.gateway.dom.singlesignon.WindowAccount;
import nts.uk.ctx.sys.gateway.dom.singlesignon.WindowAccountRepository;

/**
 * The Class WindowAccountFinder.
 */
@Stateless
public class WindowAccountFinder {

	/** The window account repository. */
	@Inject
	private WindowAccountRepository windowAccountRepository;

	/**
	 * Find window account by user id.
	 *
	 * @param userId
	 *            the user id
	 * @return the windown account finder dto
	 */
	public List<WindownAccountFinderDto> findWindowAccountByUserId(String userId) {

		List<WindownAccountFinderDto> listWindownAccountFinderDto = new ArrayList<>();

		List<WindowAccount> listWindowAccount = this.windowAccountRepository.findByUserIdAndUseAtr(userId,
				UseAtr.Use.value);
		if (!listWindowAccount.isEmpty()) {

			for (WindowAccount windowAccount : listWindowAccount) {
				WindownAccountFinderDto windownAccountFinderDto = new WindownAccountFinderDto();
				windowAccount.saveToMemento(windownAccountFinderDto);
				listWindownAccountFinderDto.add(windownAccountFinderDto);
			}
		}
		
		return listWindownAccountFinderDto;
//		return listWindownAccountFinderDto.stream().sorted((dto1, dto2) -> {
//			return dto1.getNo().compareTo(dto2.getNo());
//		}).collect(Collectors.toList());

	}

}
