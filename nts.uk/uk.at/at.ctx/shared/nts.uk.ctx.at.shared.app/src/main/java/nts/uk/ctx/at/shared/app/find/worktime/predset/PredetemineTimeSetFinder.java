/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.worktime.predset;

import java.util.Optional;

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
	 * Find by work time code.
	 *
	 * @param workTimeCode the work time code
	 * @return the predetemine time setting dto
	 */
	public PredetemineTimeSettingDto findByWorkTimeCode(String workTimeCode) {
		String companyId = AppContexts.user().companyId();
		Optional<PredetemineTimeSetting> optionalpredset = this.predetemineTimeSetRepository
				.findByWorkTimeCode(companyId, workTimeCode);
		PredetemineTimeSettingDto dto = new PredetemineTimeSettingDto();
		if (optionalpredset.isPresent()) {
			optionalpredset.get().saveToMemento(dto);
		}
		return dto;
	}

}
