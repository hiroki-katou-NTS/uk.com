/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.app.find.shift.autocalsetting.unit;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.schedule.dom.shift.autocalsetting.UseUnitAutoCalSetting;
import nts.uk.ctx.at.schedule.dom.shift.autocalsetting.UseUnitAutoCalSettingRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class UnitAutoCalSetFinder.
 */
@Stateless
public class UnitAutoCalSetFinder {

	/** The use unit auto cal setting repository. */
	@Inject
	private UseUnitAutoCalSettingRepository useUnitAutoCalSettingRepository;
	
	/**
	 * Gets the use unit auto cal setting.
	 *
	 * @return the use unit auto cal setting
	 */
	public UseUnitAutoCalSettingDto getUseUnitAutoCalSetting() {
		String companyId = AppContexts.user().companyId();

		Optional<UseUnitAutoCalSetting> optUseUnitAutoCalSetting = this.useUnitAutoCalSettingRepository.getAllUseUnitAutoCalSetting(companyId);

		if (!optUseUnitAutoCalSetting.isPresent()) {
			return null;
		}

		UseUnitAutoCalSettingDto dto = new UseUnitAutoCalSettingDto();

		optUseUnitAutoCalSetting.get().saveToMemento(dto);

		return dto;
	}
	
	
}
