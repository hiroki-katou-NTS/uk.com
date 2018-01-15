/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.worktime.flexset;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.app.find.worktime.flexset.dto.FlexWorkSettingDto;
import nts.uk.ctx.at.shared.dom.worktime.flexset.FlexWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.flexset.FlexWorkSettingRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class FlexWorkSettingFinder.
 */
@Stateless
public class FlexWorkSettingFinder {

	/** The repository. */
	@Inject
	private FlexWorkSettingRepository repository;

	/**
	 * Find by id.
	 *
	 * @param workTimeCode
	 *            the work time code
	 * @return the optional
	 */
	public FlexWorkSettingDto findById(String workTimeCode) {

		// get company id
		String companyId = AppContexts.user().companyId();

		// call repository find by id
		Optional<FlexWorkSetting> flexWorkSetting = this.repository.find(companyId, workTimeCode);

		if (flexWorkSetting.isPresent()) {
			FlexWorkSettingDto dto = new FlexWorkSettingDto();
			flexWorkSetting.get().saveToMemento(dto);
			return dto;
		}
		return null;
	}

}
