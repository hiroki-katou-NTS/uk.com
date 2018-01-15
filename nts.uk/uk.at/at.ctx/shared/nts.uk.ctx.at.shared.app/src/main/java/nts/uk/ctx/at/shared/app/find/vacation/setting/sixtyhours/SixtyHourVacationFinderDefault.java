/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.vacation.setting.sixtyhours;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.app.find.vacation.setting.sixtyhours.dto.Emp60HourVacationDto;
import nts.uk.ctx.at.shared.app.find.vacation.setting.sixtyhours.dto.SixtyHourVacationSettingCheckDto;
import nts.uk.ctx.at.shared.app.find.vacation.setting.sixtyhours.dto.SixtyHourVacationSettingDto;
import nts.uk.ctx.at.shared.dom.vacation.setting.sixtyhours.Com60HourVacation;
import nts.uk.ctx.at.shared.dom.vacation.setting.sixtyhours.Com60HourVacationRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.sixtyhours.Emp60HourVacation;
import nts.uk.ctx.at.shared.dom.vacation.setting.sixtyhours.Emp60HourVacationRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class SixtyHourVacationFinderDefault.
 */
@Stateless
public class SixtyHourVacationFinderDefault implements SixtyHourVacationFinder {

	/** The com sv repository. */
	@Inject
	private Com60HourVacationRepository comSvRepository;

	/** The emp sv repository. */
	@Inject
	private Emp60HourVacationRepository empSvRepository;

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

		SixtyHourVacationSettingDto dto = new SixtyHourVacationSettingDto();

		optCom60HVacation.get().getSetting().saveToMemento(dto);

		return dto;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.app.vacation.setting.subst.find.60HourVacationFinder#
	 * findEmpSetting(java.lang.String, java.lang.String)
	 */
	@Override
	public Emp60HourVacationDto findEmpSetting(String companyId, String contractTypeCode) {
		// Find setting
		Optional<Emp60HourVacation> optEmpSubVacation = this.empSvRepository.findById(companyId,
				contractTypeCode);

		// Check exist
		if (!optEmpSubVacation.isPresent()) {
			return null;
		}

		Emp60HourVacationDto dto = new Emp60HourVacationDto();

		optEmpSubVacation.get().saveToMemento(dto);

		return dto;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.app.find.vacation.setting.sixtyhours.
	 * SixtyHourVacationFinder#findAllEmployment()
	 */
	@Override
	public List<String> findAllEmployment() {
		String companyId = AppContexts.user().companyId();
		return empSvRepository.findAll(companyId).stream()
				.map(item -> item.getEmpContractTypeCode()).collect(Collectors.toList());
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

		optCom60HVacation.get().getSetting().saveToMemento(dto);

		return dto;
	}

}
