/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.resttime;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.app.find.resttime.dto.RestTimeDto;
import nts.uk.ctx.at.shared.dom.resttime.FixedWorkSetting;
import nts.uk.ctx.at.shared.dom.resttime.RestTimeRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class RestTimeFinder.
 */
@Stateless
public class RestTimeFinder {

	/** The rest time repository. */
	@Inject
	private RestTimeRepository restTimeRepository;

	/**
	 * Gets the all rest time.
	 *
	 * @param selectedSiftCode the selected sift code
	 * @return the all rest time
	 */
	public RestTimeDto getAllRestTime(String selectedSiftCode) {
		String companyId = AppContexts.user().companyId();
		FixedWorkSetting restTime = restTimeRepository.getResttime(companyId, selectedSiftCode);
		RestTimeDto restTimeDto = new RestTimeDto();
		restTime.saveToMemento(restTimeDto);
		return restTimeDto;
	}
}
