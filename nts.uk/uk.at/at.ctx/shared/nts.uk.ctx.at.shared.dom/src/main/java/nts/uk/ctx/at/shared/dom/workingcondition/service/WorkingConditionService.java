/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.workingcondition.service;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingCondition;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItemRepository;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class WorkingConditionService.
 */
@Stateless
public class WorkingConditionService {
	
	/** The wc repo. */
	@Inject
	private WorkingConditionRepository wcRepo;
	
	/** The wc item repo. */
	@Inject
	private WorkingConditionItemRepository wcItemRepo;
	

	
	/**
	 * Find work condition by employee.
	 * 社員の労働条件を取得する
	 * @param employeeId the employee id
	 * @param baseDate the base date
	 * @return the optional
	 */
	public Optional<WorkingConditionItem> findWorkConditionByEmployee(String employeeId, GeneralDate baseDate) {
		String companyId = AppContexts.user().companyId();
		Optional<WorkingCondition> workConditionOpt = this.wcRepo.getBySidAndStandardDate(companyId, employeeId, baseDate);
		if (!workConditionOpt.isPresent()) {
			return Optional.empty();
		}
		return this.wcItemRepo.getByHistoryId(workConditionOpt.get().items().get(0).identifier());
	}
	
}
