/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.ot.autocalsetting.wkp;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.ot.autocalsetting.wkp.WkpAutoCalSetting;
import nts.uk.ctx.at.shared.dom.ot.autocalsetting.wkp.WkpAutoCalSettingRepository;
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
}
