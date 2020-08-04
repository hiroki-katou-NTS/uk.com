/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.app.find.workrecord.workfixed;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.adapter.workplace.affiliate.AffWorkplaceAdapter;
import nts.uk.ctx.at.record.dom.adapter.workplace.affiliate.AffWorkplaceDto;
import nts.uk.ctx.at.shared.dom.workrule.closure.Closure;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.UseClassification;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.ClosureService;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;
import nts.arc.time.calendar.period.DatePeriod;

/**
 * The Class WorkPlaceInfoFinder.
 */

@Stateless
public class WorkPlaceInfFinder {

	/** The aff workplace adapter. */
	@Inject
	private AffWorkplaceAdapter affWorkplaceAdapter;

	/** The closure repository. */
	@Inject
	private ClosureRepository closureRepository;

	/** The closure service. */
	@Inject
	private ClosureService closureService;

	/**
	 * Find work place info.
	 *
	 * @param employeeId
	 *            the employee id
	 * @param baseDate
	 *            the base date
	 * @return the work place info dto
	 */
	public List<WorkPlaceInfoDto> findWorkPlaceInfo() {

		// get user login
		LoginUserContext loginUserContext = AppContexts.user();

		// get employee id
		String employeeId = loginUserContext.employeeId();

		// get company id
		String companyId = loginUserContext.companyId();

		List<WorkPlaceInfoDto> result = new ArrayList<>();
		
		// get basedate
		List<Closure> listClosure = this.closureRepository.findAllActive(companyId, UseClassification.UseClass_Use);
		WorkPlaceInfoDto workPlaceInfoDto = new WorkPlaceInfoDto();

		if (listClosure.isEmpty()) {
			return result;	
		}
		
		DatePeriod currentPeriod = this.closureService.getClosurePeriod(listClosure.get(0).getClosureId().value,
				listClosure.get(0).getClosureMonth().getProcessingYm());

		Optional<AffWorkplaceDto> opAffWorkPlaceDto = affWorkplaceAdapter.findBySid(employeeId,
				currentPeriod.end());

		workPlaceInfoDto = WorkPlaceInfoDto.builder().workplaceId(opAffWorkPlaceDto.get().getWorkplaceId())
				.workplaceCode(opAffWorkPlaceDto.get().getWorkplaceCode())
				.workplaceName(opAffWorkPlaceDto.get().getWorkplaceName()).build();
		
		result.add(workPlaceInfoDto);
		return result;
	}
}
