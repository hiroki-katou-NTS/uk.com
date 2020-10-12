/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.app.service.workrecord.erroralarm.recordcheck;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.adapter.query.employee.RegulationInfoEmployeeQuery;
import nts.uk.ctx.at.record.dom.adapter.query.employee.RegulationInfoEmployeeQueryAdapter;
import nts.uk.ctx.at.record.dom.adapter.query.employee.RegulationInfoEmployeeQueryR;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.AlCheckTargetCondition;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.affiliationinfor.ClassificationCode;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.primitivevalue.BusinessTypeCode;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.EmploymentCode;

/**
 * The Class NarrowDownTheSubject.
 */
// 対象者をしぼり込む
@Stateless
public class NarrowDownTheSubject {

	/** The employee finder. */
	@Inject
	private RegulationInfoEmployeeQueryAdapter employeeFinder;

	/**
	 * Narrow down the subject.
	 *
	 * @param referenceDate the reference date
	 * @param employeeIds the employee ids
	 * @param condition the condition
	 * @return the list
	 */
	// 対象者をしぼり込む
	public List<String> narrowDownTheSubject(GeneralDate referenceDate, List<String> employeeIds,
			AlCheckTargetCondition condition) {

		// create query
		RegulationInfoEmployeeQuery query = new RegulationInfoEmployeeQuery();
		query.setBaseDate(referenceDate);
		query.setReferenceRange(null);
		query.setFilterByEmployment(condition.getFilterByEmployment());
		query.setEmploymentCodes(
				condition.getLstEmploymentCode().stream().map(EmploymentCode::v).collect(Collectors.toList()));
		query.setFilterByDepartment(false);
		query.setDepartmentCodes(Collections.emptyList());
		query.setFilterByWorkplace(false);
		query.setWorkplaceCodes(Collections.emptyList());
		query.setFilterByClassification(condition.getFilterByClassification());
		query.setClassificationCodes(
				condition.getLstClassificationCode().stream().map(ClassificationCode::v).collect(Collectors.toList()));
		query.setFilterByJobTitle(condition.getFilterByJobTitle());
		query.setJobTitleCodes(condition.getLstJobTitleId());
		query.setFilterByWorktype(condition.getFilterByBusinessType());
		query.setWorktypeCodes(
				condition.getLstBusinessTypeCode().stream().map(BusinessTypeCode::v).collect(Collectors.toList()));
		query.setPeriodStart(referenceDate);
		query.setPeriodEnd(referenceDate);
		query.setRetireStart(referenceDate);
		query.setRetireEnd(referenceDate);
		query.setIncludeIncumbents(true);
		query.setIncludeWorkersOnLeave(true);
		query.setIncludeOccupancy(true);
		query.setIncludeAreOnLoan(true);
		query.setIncludeGoingOnLoan(false);
		query.setIncludeRetirees(false);
		query.setSortOrderNo(null);

		// search employee by created query
		List<String> filtered = this.employeeFinder.search(query).stream()
				.map(RegulationInfoEmployeeQueryR::getEmployeeId).collect(Collectors.toList());

		// retain all filtered employeeId
		employeeIds.retainAll(filtered);

		return employeeIds;
	}
}
