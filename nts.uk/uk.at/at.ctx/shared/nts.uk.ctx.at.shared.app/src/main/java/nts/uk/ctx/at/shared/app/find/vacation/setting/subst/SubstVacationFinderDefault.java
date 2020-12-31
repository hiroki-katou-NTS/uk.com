/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.vacation.setting.subst;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.app.find.vacation.setting.subst.dto.EmpSubstVacationDto;
import nts.uk.ctx.at.shared.app.find.vacation.setting.subst.dto.SubstVacationSettingDto;
import nts.uk.ctx.at.shared.dom.vacation.setting.subst.ComSubstVacation;
import nts.uk.ctx.at.shared.dom.vacation.setting.subst.ComSubstVacationRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.subst.EmpSubstVacation;
import nts.uk.ctx.at.shared.dom.vacation.setting.subst.EmpSubstVacationRepository;
import nts.uk.shr.com.context.AppContexts;

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
	@Override
	public SubstVacationSettingDto findComSetting(String companyId) {

		Optional<ComSubstVacation> optComSubVacation = this.comSvRepository.findById(companyId);

		if (!optComSubVacation.isPresent()) {
			return null;
		}

		SubstVacationSettingDto dto = SubstVacationSettingDto.fromDomain(optComSubVacation.get());
		return dto;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.app.vacation.setting.subst.find.SubstVacationFinder#
	 * findEmpSetting(java.lang.String, java.lang.String)
	 */
	@Override
	public EmpSubstVacationDto findEmpSetting(String companyId, String contractTypeCode) {
		// Find setting
		Optional<EmpSubstVacation> optEmpSubVacation = this.empSvRepository.findById(companyId,
				contractTypeCode);

		// Check exist
		if (!optEmpSubVacation.isPresent()) {
			return null;
		}

		EmpSubstVacationDto dto = new EmpSubstVacationDto();

		optEmpSubVacation.get().saveToMemento(dto);

		return dto;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.app.vacation.setting.subst.find.SubstVacationFinder#findAllEmployment()
	 */
	@Override
	public List<String> findAllEmployment() {
		String companyId = AppContexts.user().companyId();
		return empSvRepository.findAll(companyId).stream().map(item -> item.getEmpContractTypeCode())
				.collect(Collectors.toList());
	}

}
