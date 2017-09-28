/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.app.find.shift.autocalsetting.wkp;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.schedule.dom.shift.autocalsetting.WkpAutoCalSetting;
import nts.uk.ctx.at.schedule.dom.shift.autocalsetting.WkpAutoCalSettingRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class WkpAutoCalSetFinder.
 */
@Stateless
public class WkpAutoCalSetFinder {

	/** The wkp auto cal setting repository. */
	@Inject
	private WkpAutoCalSettingRepository wkpAutoCalSettingRepository;

	/**
	 * Gets the wkp auto cal setting.
	 *
	 * @param wkpId the wkp id
	 * @return the wkp auto cal setting
	 */
	public WkpAutoCalSettingDto getWkpAutoCalSetting(String wkpId) {
		String companyId = AppContexts.user().companyId();

		Optional<WkpAutoCalSetting> opt = this.wkpAutoCalSettingRepository.getAllWkpAutoCalSetting(companyId, wkpId);

		if (!opt.isPresent()) {
			return null;
		}

		WkpAutoCalSettingDto dto = new WkpAutoCalSettingDto();

		opt.get().saveToMemento(dto);

		return dto;
	}

	/**
	 * Delete by code.
	 *
	 * @param wkpId the wkp id
	 */
	public void deleteByCode(String wkpId) {
		String companyId = AppContexts.user().companyId();
		wkpAutoCalSettingRepository.delete(companyId, wkpId);
	}
}
