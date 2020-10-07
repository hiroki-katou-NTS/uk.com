/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.ot.autocalsetting.wkp;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.wkp.WkpAutoCalSetting;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.wkp.WkpAutoCalSettingRepository;
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

		Optional<WkpAutoCalSetting> opt = this.wkpAutoCalSettingRepository.getWkpAutoCalSetting(companyId, wkpId);

		if (!opt.isPresent()) {
			return null;
		}

		WkpAutoCalSettingDto dto = new WkpAutoCalSettingDto();

		opt.get().saveToMemento(dto);

		return dto;
	}
	
	
	/**
	 * Gets the all wkp auto cal setting.
	 *
	 * @return the all wkp auto cal setting
	 */
	public List<WkpAutoCalSettingDto> getAllWkpAutoCalSetting() {
		String companyId = AppContexts.user().companyId();

		List<WkpAutoCalSetting> listAll = this.wkpAutoCalSettingRepository.getAllWkpAutoCalSetting(companyId);
		
		return listAll.stream().map(e -> {
			WkpAutoCalSettingDto dto = new WkpAutoCalSettingDto();
			e.saveToMemento(dto);
			
			return dto;
		}).collect(Collectors.toList());
	}
}
