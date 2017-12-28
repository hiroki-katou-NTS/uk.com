/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.pubimp.employment;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.bs.employee.dom.employment.Employment;
import nts.uk.ctx.bs.employee.dom.employment.EmploymentRepository;
import nts.uk.ctx.bs.employee.dom.employment.affiliate.AffEmploymentHistory;
import nts.uk.ctx.bs.employee.dom.employment.affiliate.AffEmploymentHistoryRepository;
import nts.uk.ctx.bs.employee.pub.employment.EmpCdNameExport;
import nts.uk.ctx.bs.employee.pub.employment.SEmpHistExport;
import nts.uk.ctx.bs.employee.pub.employment.ShEmploymentExport;
import nts.uk.ctx.bs.employee.pub.employment.SyEmploymentPub;

/**
 * The Class EmploymentPubImp.
 */
@Stateless
public class EmploymentPubImp implements SyEmploymentPub {

	/** The Constant FIRST_ITEM_INDEX. */
	private static final int FIRST_ITEM_INDEX = 0;

	/** The employment repository. */
	@Inject
	private EmploymentRepository employmentRepository;

	/** The employment history repository. */
	@Inject
	private AffEmploymentHistoryRepository employmentHistoryRepository;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.basic.pub.company.organization.employee.EmployeePub#
	 * getEmployeeCode(java.lang.String, nts.arc.time.GeneralDate)
	 */
	@Override
	public String getEmploymentCode(String companyId, String employeeId, GeneralDate baseDate) {
		// Query
		List<AffEmploymentHistory> affEmploymentHistories = employmentHistoryRepository
				.searchEmploymentOfSids(Arrays.asList(employeeId), baseDate);

		List<String> employmentCodes = affEmploymentHistories.stream()
				.map(item -> item.getEmploymentCode().v()).collect(Collectors.toList());

		List<Employment> acEmploymentDtos = employmentRepository.findByEmpCodes(companyId,
				employmentCodes);

		Map<String, String> comEmpMap = acEmploymentDtos.stream()
				.collect(Collectors.toMap((item) -> {
					return item.getCompanyId().v();
				}, (item) -> {
					return item.getEmploymentCode().v();
				}));

		// Return EmploymentCode
		return comEmpMap.get(companyId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.bs.employee.pub.employment.SyEmploymentPub#findAll(java.lang.
	 * String)
	 */
	@Override
	public List<EmpCdNameExport> findAll(String companyId) {
		return employmentRepository.findAll(companyId).stream()
				.map(item -> EmpCdNameExport.builder().code(item.getEmploymentCode().v())
						.name(item.getEmploymentName().v()).build())
				.collect(Collectors.toList());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.bs.employee.pub.employment.SyEmploymentPub#findSEmpHistBySid(
	 * java.lang.String, java.lang.String, nts.arc.time.GeneralDate)
	 */
	@Override
	public Optional<SEmpHistExport> findSEmpHistBySid(String companyId, String employeeId,
			GeneralDate baseDate) {
		// Query
		List<AffEmploymentHistory> affEmploymentHistories = employmentHistoryRepository
				.searchEmploymentOfSids(Arrays.asList(employeeId), baseDate);

		// Check exist
		if (CollectionUtil.isEmpty(affEmploymentHistories)) {
			return Optional.empty();
		}

		AffEmploymentHistory empHist = affEmploymentHistories.get(FIRST_ITEM_INDEX);

		// Find emp by empCd
		List<Employment> employments = employmentRepository.findByEmpCodes(companyId,
				Arrays.asList(empHist.getEmploymentCode().v()));

		// Get employment info
		Employment employment = employments.get(FIRST_ITEM_INDEX);

		// Return
		return Optional.of(SEmpHistExport.builder().employeeId(employeeId)
				.employmentCode(employment.getEmploymentCode().v())
				.employmentName(employment.getEmploymentName().v())
				.period(empHist.getPeriod()).build());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.bs.employee.pub.employment.SyEmploymentPub#findByEmpCodes(java.lang.String, java.util.List)
	 */
	@Override
	public List<ShEmploymentExport> findByEmpCodes(String companyId, List<String> empCodes) {
		List<Employment> empList = this.employmentRepository.findByEmpCodes(companyId, empCodes);
		return empList.stream().map(emp -> {
			ShEmploymentExport empExport = new ShEmploymentExport();
			empExport.setCompanyId(emp.getCompanyId().v());
			empExport.setEmploymentCode(emp.getEmploymentCode().v());
			empExport.setEmploymentName(emp.getEmploymentName().v());
			empExport.setEmpExternalCode(emp.getEmpExternalCode().v());
			empExport.setMemo(emp.getMemo().v());
			return empExport;
		}).collect(Collectors.toList());
	}

}
