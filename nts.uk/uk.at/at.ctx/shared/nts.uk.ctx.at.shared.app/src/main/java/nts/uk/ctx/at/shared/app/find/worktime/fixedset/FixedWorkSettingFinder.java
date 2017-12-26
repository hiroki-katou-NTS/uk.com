/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.worktime.fixedset;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.app.find.worktime.fixedset.dto.FixedWorkSettingDto;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkSettingRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class FixedWorkSettingFinder.
 */
@Stateless
public class FixedWorkSettingFinder {

	/** The fixed work setting repository. */
	@Inject
	private FixedWorkSettingRepository fixedWorkSettingRepository;
	
	/**
	 * Find by code.
	 *
	 * @param worktimeCode the worktime code
	 * @return the fixed work setting dto
	 */
	public FixedWorkSettingDto findByCode(String worktimeCode) {		
		String companyId = AppContexts.user().companyId();
		
		Optional<FixedWorkSetting> opFixedWorkSetting = this.fixedWorkSettingRepository.findByKey(companyId, worktimeCode);
		if (opFixedWorkSetting.isPresent()) {		
			FixedWorkSettingDto fixedWorkSettingDto = new FixedWorkSettingDto();
			opFixedWorkSetting.get().saveToMemento(fixedWorkSettingDto);
			return fixedWorkSettingDto;
		}
		return null;
	}
	
}
