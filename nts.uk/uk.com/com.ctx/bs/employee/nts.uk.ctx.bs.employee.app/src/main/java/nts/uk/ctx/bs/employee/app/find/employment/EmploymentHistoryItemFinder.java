/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.app.find.employment;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.app.find.employment.dto.EmploymentHistoryItemDto;
import nts.uk.ctx.bs.employee.dom.employment.history.EmploymentHistoryItem;
import nts.uk.ctx.bs.employee.dom.employment.history.EmploymentHistoryItemRepository;
import nts.uk.ctx.bs.employee.dom.employment.history.EmploymentHistoryRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.history.DateHistoryItem;

/**
 * The Class EmploymentHistoryFinder.
 */
@Stateless
public class EmploymentHistoryItemFinder {

	/** The emp his repo. */
	@Inject
	private EmploymentHistoryRepository empHisRepo;

	/** The emp his item repo. */
	@Inject
	private EmploymentHistoryItemRepository empHisItemRepo;

	/**
	 * Find current history item.
	 *
	 * @return the employment history item dto
	 */
	public EmploymentHistoryItemDto findCurrentHistoryItem() {
		DateHistoryItem historyItem = this.empHisRepo
				.getByEmployeeIdAndStandardDate(AppContexts.user().employeeId(), GeneralDate.today()).get();
		EmploymentHistoryItem empHisItem = this.empHisItemRepo.getByHistoryId(historyItem.identifier()).get();

		return EmploymentHistoryItemDto.builder()
				.employeeId(empHisItem.getEmployeeId())
				.employmentCode(empHisItem.getEmploymentCode().v())
				.historyId(empHisItem.getHistoryId())
				.salarySegment(empHisItem.getSalarySegment().value).build();
	}
}
