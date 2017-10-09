/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.ot.autocalsetting.wkpjob;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.ot.autocalsetting.wkpjob.WkpJobAutoCalSetting;
import nts.uk.ctx.at.shared.dom.ot.autocalsetting.wkpjob.WkpJobAutoCalSettingRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class WkpJobAutoCalSetFinder.
 */
@Stateless
public class WkpJobAutoCalSetFinder {

	/** The wkp job auto cal setting repository. */
	@Inject
	private WkpJobAutoCalSettingRepository wkpJobAutoCalSettingRepository;

	/**
	 * Gets the wkp job auto cal setting.
	 *
	 * @param wkpId the wkp id
	 * @param jobId the job id
	 * @return the wkp job auto cal setting
	 */
	public WkpJobAutoCalSettingDto getWkpJobAutoCalSetting(String wkpId, String jobId) {
		String companyId = AppContexts.user().companyId();

		Optional<WkpJobAutoCalSetting> opt = this.wkpJobAutoCalSettingRepository.getAllWkpJobAutoCalSetting(companyId,
				wkpId, jobId);

		if (!opt.isPresent()) {
			return null;
		}

		WkpJobAutoCalSettingDto dto = new WkpJobAutoCalSettingDto();

		opt.get().saveToMemento(dto);

		return dto;
	}

}
