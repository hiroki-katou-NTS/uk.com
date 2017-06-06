/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.vacation.setting.subst.find;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.uk.ctx.at.shared.app.vacation.setting.subst.find.dto.EmpSubstVacationDto;
import nts.uk.ctx.at.shared.app.vacation.setting.subst.find.dto.SubstVacationSettingDto;
import nts.uk.ctx.at.shared.dom.vacation.setting.subst.ComSubstVacation;
import nts.uk.ctx.at.shared.dom.vacation.setting.subst.ComSubstVacationRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.subst.EmpSubstVacation;
import nts.uk.ctx.at.shared.dom.vacation.setting.subst.EmpSubstVacationRepository;

/**
 * The Class SubstVacationFinderDefault.
 */
@Stateless
public class SubstVacationFinderDefault implements SubstVacationFinder {

	/** The com sv repository. */
	@Inject
	private ComSubstVacationRepository comSvRepository;

	/** The emp sv repository. */
	@Inject
	private EmpSubstVacationRepository empSvRepository;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.app.vacation.setting.subst.find.SubstVacationFinder#
	 * findComSetting(java.lang.String)
	 */
	public SubstVacationSettingDto findComSetting(String companyId) {

		Optional<ComSubstVacation> optComSubVacation = this.comSvRepository.findById(companyId);

		if (!optComSubVacation.isPresent()) {
			// TODO: find msg id
			throw new BusinessException("");
		}

		SubstVacationSettingDto dto = new SubstVacationSettingDto();

		optComSubVacation.get().getSetting().saveToMemento(dto);

		return dto;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.app.vacation.setting.subst.find.SubstVacationFinder#
	 * findEmpSetting(java.lang.String, java.lang.String)
	 */
	public EmpSubstVacationDto findEmpSetting(String companyId, String contractTypeCode) {
		// Find setting
		Optional<EmpSubstVacation> optEmpSubVacation = this.empSvRepository.findById(companyId,
				contractTypeCode);

		// Check exist
		if (!optEmpSubVacation.isPresent()) {
			// TODO: find msg id
			throw new BusinessException("");
		}

		EmpSubstVacationDto dto = new EmpSubstVacationDto();

		optEmpSubVacation.get().saveToMemento(dto);

		return dto;
	}

}
