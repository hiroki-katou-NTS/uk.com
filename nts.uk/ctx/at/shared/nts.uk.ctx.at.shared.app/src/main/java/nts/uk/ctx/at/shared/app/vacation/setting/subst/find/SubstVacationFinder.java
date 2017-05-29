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
 * The Class SubstVacationFinder.
 */
@Stateless
public class SubstVacationFinder {

	/** The com sv repository. */
	@Inject
	private ComSubstVacationRepository comSvRepository;

	/** The emp sv repository. */
	@Inject
	private EmpSubstVacationRepository empSvRepository;

	/**
	 * Find com setting.
	 *
	 * @param companyId
	 *            the company id
	 * @return the subst vacation setting dto
	 */
	public SubstVacationSettingDto findComSetting(String companyId) {

		Optional<ComSubstVacation> optComSubVacation = this.comSvRepository.findById(companyId);

		if (!optComSubVacation.isPresent()) {
			throw new BusinessException("");
		}

		SubstVacationSettingDto dto = new SubstVacationSettingDto();

		optComSubVacation.get().saveToMemento(dto);

		return dto;
	}

	/**
	 * Find emp setting.
	 *
	 * @param companyId
	 *            the company id
	 * @param contractTypeCode
	 *            the contract type code
	 * @return the emp subst vacation dto
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
