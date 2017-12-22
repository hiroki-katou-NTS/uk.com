/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.pubimp.employee.employeeindesignated;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.bs.employee.dom.workplace.affiliate.AffWorkplaceHistoryRepository_v1;
import nts.uk.ctx.bs.employee.dom.workplace.affiliate.AffWorkplaceHistory_ver1;
import nts.uk.ctx.bs.employee.pub.employee.employeeindesignated.EmpInDesignatedPub;
import nts.uk.ctx.bs.employee.pub.employee.employeeindesignated.EmployeeInDesignatedExport;
import nts.uk.ctx.bs.employee.pub.employment.statusemployee.StatusOfEmploymentExport;
import nts.uk.ctx.bs.employee.pub.employment.statusemployee.StatusOfEmploymentPub;

/**
 * The Class EmpInDesignatedPubImp.
 */
public class EmpInDesignatedPubImp implements EmpInDesignatedPub {
	
	/** The aff workplace history repo v 1. */
	@Inject
	private AffWorkplaceHistoryRepository_v1 affWorkplaceHistoryRepo_v1;

	/** The employment status pub. */
	@Inject
	private StatusOfEmploymentPub employmentStatusPub;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.bs.employee.pub.employee.employeeindesignated.
	 * EmpInDesignatedPub#getEmpInDesignated(java.lang.String,
	 * nts.arc.time.GeneralDate, java.util.List)
	 */
	@Override
	public List<EmployeeInDesignatedExport> getEmpInDesignated(String workplaceId, GeneralDate referenceDate,
			List<Integer> empStatus) {
		// old
		//List<AffWorkplaceHistory> affWorkplaceHistList = this.affWorkplaceHistoryRepo.getByWorkplaceID(workplaceId,
		//		referenceDate);
		
		List<AffWorkplaceHistory_ver1> affWorkplaceHistList = 
				this.affWorkplaceHistoryRepo_v1.getWorkplaceHistoryByWorkplaceIdAndDate(referenceDate, workplaceId);
		// check exist data
		if (CollectionUtil.isEmpty(affWorkplaceHistList)) {
			return null;
		}
		// Get List of Employee Id from AffWorkplaceHistory List - old
		//List<String> empIdList = affWorkplaceHistList.stream().map(AffWorkplaceHistory::getEmployeeId)
		//		.collect(Collectors.toList());
		
		List<String> empIdList = affWorkplaceHistList.stream().map(AffWorkplaceHistory_ver1::getEmployeeId)
				.collect(Collectors.toList());
		
		// Output List
		List<EmployeeInDesignatedExport> empsInDesignated = new ArrayList<>();
		//
		empIdList.stream().forEach(empId -> {
			// 在職状態を取得
			StatusOfEmploymentExport employmentStatus = this.employmentStatusPub.getStatusOfEmployment(empId,
					referenceDate);
			// check if null
			if (employmentStatus != null) {
				// Every EmpStatus Acquired from screen. Compare to empStatus
				// Acquired above
				empStatus.stream().forEach(s -> {
					if (employmentStatus.getStatusOfEmployment() == s) {
						// Add to output list
						EmployeeInDesignatedExport empExport = EmployeeInDesignatedExport.builder().employeeId(empId)
								.statusOfEmp(employmentStatus.getStatusOfEmployment()).build();
						empsInDesignated.add(empExport);
					}
				});
			}
		});
		return empsInDesignated;
	}

}
