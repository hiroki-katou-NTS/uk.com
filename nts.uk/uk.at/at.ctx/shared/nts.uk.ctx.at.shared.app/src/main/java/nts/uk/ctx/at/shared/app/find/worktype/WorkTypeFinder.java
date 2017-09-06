/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.worktype;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.uk.ctx.at.shared.dom.worktime.WorkTime;
import nts.uk.ctx.at.shared.dom.worktime.WorkTimeRepository;
import nts.uk.ctx.at.shared.dom.worktype.DeprecateClassification;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeSymbolicName;
import nts.uk.ctx.at.shared.dom.worktype.language.WorkTypeLanguage;
import nts.uk.ctx.at.shared.dom.worktype.language.WorkTypeLanguageRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

@Stateless
public class WorkTypeFinder {

	/** The work type repo. */
	@Inject
	private WorkTypeRepository workTypeRepo;

	/** The work time repository. */
	@Inject
	private WorkTimeRepository workTimeRepository;

	@Inject
	private WorkTypeLanguageRepository workTypeLanguageRepo;

	/** The company id. */
	// user contexts
	String companyId = AppContexts.user().companyId();

	public List<WorkTypeDto> getPossibleWorkType(List<String> lstPossible) {
		List<WorkTypeDto> lst = this.workTypeRepo.getPossibleWorkType(companyId, lstPossible).stream()
				.map(c -> WorkTypeDto.fromDomain(c)).collect(Collectors.toList());
		return lst;
	}

	/**
	 * Find not deprecated by list code.
	 *
	 * @param codes
	 *            the codes
	 * @return the list
	 */
	public List<WorkTypeDto> findNotDeprecatedByListCode(List<String> codes) {
		return this.workTypeRepo.findNotDeprecatedByListCode(companyId, codes).stream()
				.map(dom -> WorkTypeDto.fromDomain(dom)).collect(Collectors.toList());
	}

	/**
	 * Find by company id.
	 *
	 * @return the list
	 */
	public List<WorkTypeDto> findByCompanyId() {
		return this.workTypeRepo.findByCompanyId(companyId).stream().map(c -> {
			List<WorkTypeSetDto> workTypeSetList = c.getWorkTypeSetList().stream()
					.map(x -> WorkTypeSetDto.fromDomain(x)).collect(Collectors.toList());

			WorkTypeDto workType = WorkTypeDto.fromDomain(c);
			workType.setWorkTypeSets(workTypeSetList);
			return workType;
		}).collect(Collectors.toList());
	}

	/**
	 * Find not deprecated.
	 *
	 * @return the list
	 */
	public List<WorkTypeDto> findNotDeprecated() {
		return this.workTypeRepo.findNotDeprecated(companyId).stream().map(dom -> WorkTypeDto.fromDomain(dom))
				.collect(Collectors.toList());
	}

	/**
	 * Find by companyId and DeprecateClassification = Deprecated (added by
	 * sonnh1)
	 * 
	 * @return List WorkTypeDto
	 */
	public List<WorkTypeDto> findByCIdAndDisplayAtr() {
		return this.workTypeRepo.findByCIdAndDisplayAtr(companyId, DeprecateClassification.NotDeprecated.value).stream()
				.map(c -> WorkTypeDto.fromDomain(c)).collect(Collectors.toList());
	}

	/**
	 * Find by id.
	 *
	 * @param workTypeCode
	 *            the work type code
	 * @return the work type dto
	 */
	public WorkTypeDto findById(String workTypeCode) {
		Optional<WorkType> workType = this.workTypeRepo.findByPK(companyId, workTypeCode);
		if (workType.isPresent()) {
			return WorkTypeDto.fromDomain(workType.get());
		}

		return null;
	}

	/**
	 * Check pair.
	 *
	 * @param pairDto
	 *            the pair dto
	 */
	public void checkPair(WorkTypeCheckPairDto pairDto) {
		// get login user
		LoginUserContext loginUserContext = AppContexts.user();

		// get company id
		String companyId = loginUserContext.companyId();

		Optional<WorkType> opWorkType = this.workTypeRepo.findByPK(companyId, pairDto.getWorkTypeCode());

		// check exist data
		if (!opWorkType.isPresent()) {
			throw new BusinessException("Msg_023");
		}

		Optional<WorkTime> opWorkTime = this.workTimeRepository.findByCode(companyId, pairDto.getWorkTimeCode());

		WorkType workType = opWorkType.get();

		// if(workType.getDailyWork().getWorkTypeUnit().equals(WorkTypeUnit.OneDay)){
		//
		// }
	}

	/**
	 * Check language base on language Id
	 * 
	 * @param langId
	 * @return List WorkTypeDtos
	 */
	public List<WorkTypeDto> checkLanguageWorkType(String langId) {
		if (langId.equals("jp")) {
			List<WorkType> workType = workTypeRepo.findByCompanyId(companyId);
			return workType.stream().map(x -> {
				return WorkTypeDto.fromDomain(x);
			}).collect(Collectors.toList());
		} else {
			List<WorkTypeLanguage> workTypeLanguage = workTypeLanguageRepo.findByCIdAndLangId(companyId, langId);
			return workTypeLanguage.stream().map(x -> {
				WorkType wT = new WorkType(companyId, x.getWorkTypeCode(), x.getName(), x.getAbbreviationName());
				return WorkTypeDto.fromDomainWorkTypeLanguage(wT);
			}).collect(Collectors.toList());
		}
	}
}
