/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.worktime.predset;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.app.find.worktime.predset.dto.PredetemineTimeSettingDto;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSettingRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class PredFinder.
 */
@Stateless
public class PredetemineTimeSetFinder {

	/** The predetemine time set repository. */
	@Inject
	private PredetemineTimeSettingRepository predetemineTimeSetRepository;

	/**
	 * Find by code.
	 *
	 * @param workTimeCode the work time code
	 * @return the pred dto
	 */
	public PredetemineTimeSettingDto findByWorkTimeCode(String workTimeCode) {
		String companyId = AppContexts.user().companyId();
		PredetemineTimeSetting pred = this.predetemineTimeSetRepository.findByWorkTimeCode(companyId, workTimeCode);
		PredetemineTimeSettingDto dto = new PredetemineTimeSettingDto();
		pred.saveToMemento(dto);
		return dto;
	}

}
