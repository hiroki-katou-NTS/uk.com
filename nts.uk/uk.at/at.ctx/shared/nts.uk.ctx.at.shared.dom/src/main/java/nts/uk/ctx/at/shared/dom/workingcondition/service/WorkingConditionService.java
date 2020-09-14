/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.workingcondition.service;

import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingCondition;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class WorkingConditionService.
 */
public class WorkingConditionService {
	
	/**
	 * Find work condition by employee.
	 * 社員の労働条件を取得する
	 * @param employeeId the employee id
	 * @param baseDate the base date
	 * @return the optional
	 */
	public static Optional<WorkingConditionItem> findWorkConditionByEmployee(RequireM1 require, 
			String employeeId, GeneralDate baseDate) {
		String companyId = AppContexts.user().companyId();
		Optional<WorkingCondition> workConditionOpt = require.workingCondition(companyId, employeeId, baseDate);
		if (!workConditionOpt.isPresent()) {
			return Optional.empty();
		}
		return require.workingConditionItem(workConditionOpt.get().items().get(0).identifier());
	}
	
	public static interface RequireM1 {
		
		Optional<WorkingCondition> workingCondition(String companyId, String employeeId, GeneralDate baseDate);
		
		Optional<WorkingConditionItem> workingConditionItem(String historyId);
		
	}
	
}
