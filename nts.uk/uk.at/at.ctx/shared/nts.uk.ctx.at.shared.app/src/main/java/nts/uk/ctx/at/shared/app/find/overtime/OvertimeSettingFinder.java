/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.overtime;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.app.find.overtime.dto.OvertimeSettingDto;
import nts.uk.ctx.at.shared.dom.overtime.OvertimeSetting;
import nts.uk.ctx.at.shared.dom.overtime.OvertimeSettingRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

/**
 * The Class CompanyEstablishmentFinder.
 */
@Stateless
public class OvertimeSettingFinder {
	
	/** The repository. */
	@Inject
	private OvertimeSettingRepository repository;
	
	/**
	 * Find estimate time.
	 *
	 * @param targetYear
	 *            the target year
	 * @return the company establishment dto
	 */
	public OvertimeSettingDto findById() {

		// get login user
		LoginUserContext loginUserContext = AppContexts.user();

		// get company id
		String companyId = loginUserContext.companyId();

		// call repository find data
		Optional<OvertimeSetting> overtimeSetting = this.repository.findById(companyId);

		OvertimeSettingDto dto = new OvertimeSettingDto();
		if (overtimeSetting.isPresent()) {
			overtimeSetting.get().saveToMemento(dto);
		}

		return dto;

	}
	
	
}
