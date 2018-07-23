/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.workingconditionitem;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.workingcondition.WorkScheduleBasicCreMethod;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItemRepository;

/**
 * The Class WorkingConditionItemFinder.
 */
@Stateless
public class WorkingConditionItemFinder {

	/** The working condition item repository. */
	@Inject
	private WorkingConditionItemRepository workingConditionItemRepository;

	/**
	 * Find by sids and newest history.
	 *
	 * @param sids
	 *            the sids
	 * @return the list
	 */
	public List<String> findBySidsAndNewestHistory(List<String> sids) {
		List<WorkingConditionItem> lstWCItem = workingConditionItemRepository.getLastWorkingCondItem(sids);
		List<String> listSidReturn = lstWCItem.stream().filter(item -> !item.getMonthlyPattern().isPresent())
				.map(WorkingConditionItem::getEmployeeId).collect(Collectors.toList());
		List<String> listSidNotMonthlyPatern = lstWCItem.stream().filter(item -> item.getMonthlyPattern().isPresent())
				.filter(item -> item.getScheduleMethod().isPresent())
				.filter(item -> !item.getScheduleMethod().get().getBasicCreateMethod()
						.equals(WorkScheduleBasicCreMethod.MONTHLY_PATTERN))
				.map(WorkingConditionItem::getEmployeeId).collect(Collectors.toList());
		listSidReturn.addAll(listSidNotMonthlyPatern);
		return listSidReturn;
	}
}
