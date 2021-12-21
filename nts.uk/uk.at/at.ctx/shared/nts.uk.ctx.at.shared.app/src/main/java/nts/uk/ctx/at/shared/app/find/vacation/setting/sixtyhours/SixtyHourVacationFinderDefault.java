/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.vacation.setting.sixtyhours;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.app.find.vacation.setting.sixtyhours.dto.SixtyHourVacationSettingCheckDto;
import nts.uk.ctx.at.shared.app.find.vacation.setting.sixtyhours.dto.SixtyHourVacationSettingDto;
import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;
import nts.uk.ctx.at.shared.dom.vacation.setting.sixtyhours.Com60HourVacation;
import nts.uk.ctx.at.shared.dom.vacation.setting.sixtyhours.Com60HourVacationRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class SixtyHourVacationFinderDefault.
 */
@Stateless
public class SixtyHourVacationFinderDefault implements SixtyHourVacationFinder {

	/** The com sv repository. */
	@Inject
	private Com60HourVacationRepository comSvRepository;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.app.vacation.setting.subst.find.60HourVacationFinder#
	 * findComSetting(java.lang.String)
	 */
	@Override
	public SixtyHourVacationSettingDto findComSetting() {
		String companyId = AppContexts.user().companyId();
		Optional<Com60HourVacation> optCom60HVacation = this.comSvRepository.findById(companyId);

		if (!optCom60HVacation.isPresent()) {
			return null;
		}

		Com60HourVacation vacation = optCom60HVacation.get();
		return new SixtyHourVacationSettingDto(vacation.getTimeVacationDigestUnit().getManage().value,
				vacation.getSixtyHourExtra().value,
				vacation.getTimeVacationDigestUnit().getDigestUnit().value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.app.find.vacation.setting.sixtyhours.
	 * SixtyHourVacationFinder#checkManangeSetting()
	 */
	@Override
	public SixtyHourVacationSettingCheckDto checkManangeSetting() {
		// get company id
		String companyId = AppContexts.user().companyId();
		
		Optional<Com60HourVacation> optCom60HVacation = this.comSvRepository.findById(companyId);

		if (!optCom60HVacation.isPresent()) {
			return null;
		}

		SixtyHourVacationSettingCheckDto dto = new SixtyHourVacationSettingCheckDto();
		dto.setManage(optCom60HVacation.get().getTimeVacationDigestUnit().getManage() == ManageDistinct.YES);
		return dto;
	}

}
