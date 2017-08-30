/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.pubimp.company.organization.employee;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.basic.dom.company.organization.employee.Employee;
import nts.uk.ctx.basic.dom.company.organization.employee.EmployeeRepository;
import nts.uk.ctx.basic.dom.company.organization.employee.employment.AffEmploymentHistory;
import nts.uk.ctx.basic.dom.company.organization.employee.employment.AffEmploymentHistoryRepository;
import nts.uk.ctx.basic.dom.company.organization.employee.jobtile.AffJobTitleHistory;
import nts.uk.ctx.basic.dom.company.organization.employee.jobtile.AffJobTitleHistoryRepository;
import nts.uk.ctx.basic.dom.company.organization.employee.workplace.AffWorkplaceHistory;
import nts.uk.ctx.basic.dom.company.organization.employee.workplace.AffWorkplaceHistoryRepository;
import nts.uk.ctx.bs.employee.dom.access.employment.EmploymentAdapter;
import nts.uk.ctx.bs.employee.dom.access.employment.dto.AcEmploymentDto;
import nts.uk.ctx.bs.employee.dom.access.jobtitle.JobTitleAdapter;
import nts.uk.ctx.bs.employee.dom.access.jobtitle.dto.AcJobTitleDto;
import nts.uk.ctx.bs.employee.dom.access.workplace.WorkplaceAdapter;
import nts.uk.ctx.bs.employee.dom.access.workplace.dto.AcWorkplaceDto;
import nts.uk.ctx.bs.employee.pub.employee.EmployeeDto;
import nts.uk.ctx.bs.employee.pub.employee.EmployeePub;
import nts.uk.ctx.bs.employee.pub.employee.PubJobTitleDto;

/**
 * The Class EmployeePubImp.
 */
@Stateless
public class EmployeePubImp implements EmployeePub {

	/** The employment adapter. */
	@Inject
	private JobTitleAdapter jobTitleAdapter;

	/** The workplace adapter. */
	@Inject
	private WorkplaceAdapter workplaceAdapter;

	/** The employment adapter. */
	@Inject
	private EmploymentAdapter employmentAdapter;

	/** The employee repository. */
	@Inject
	private EmployeeRepository employeeRepository;

	/** The workplace history repository. */
	@Inject
	private AffWorkplaceHistoryRepository workplaceHistoryRepository;

	/** The employment history repository. */
	@Inject
	private AffEmploymentHistoryRepository employmentHistoryRepository;

	/** The job title history repository. */
	@Inject
	private AffJobTitleHistoryRepository jobTitleHistoryRepository;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.basic.pub.company.organization.employee.EmployeePub#find(java.
	 * lang.String, nts.arc.time.GeneralDate)
	 */
	@Override
	public String getWorkplaceId(String companyId, String employeeId, GeneralDate baseDate) {
		// Query
		List<AffWorkplaceHistory> affWorkplaceHistories = workplaceHistoryRepository
				.searchWorkplaceHistoryByEmployee(employeeId, baseDate);

		List<String> wkpIds = affWorkplaceHistories.stream().map(item -> item.getWorkplaceId().v())
				.collect(Collectors.toList());

		List<AcWorkplaceDto> acWorkplaceDtos = workplaceAdapter.findByWkpIds(wkpIds);

		Map<String, String> comWkpMap = acWorkplaceDtos.stream().collect(
				Collectors.toMap(AcWorkplaceDto::getCompanyId, AcWorkplaceDto::getWorkplaceId));

		// Return workplace id
		return comWkpMap.get(companyId);
	}

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

		List<AcEmploymentDto> acEmploymentDtos = employmentAdapter.findByEmpCodes(employmentCodes);

		Map<String, String> comEmpMap = acEmploymentDtos.stream().collect(Collectors
				.toMap(AcEmploymentDto::getCompanyId, AcEmploymentDto::getEmploymentCode));

		// Return EmploymentCode
		return comEmpMap.get(companyId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.basic.pub.company.organization.employee.EmployeePub#
	 * findByWpkIds(java.lang.String, java.util.List, nts.arc.time.GeneralDate)
	 */
	@Override
	public List<EmployeeDto> findByWpkIds(String companyId, List<String> workplaceIds,
			GeneralDate baseDate) {
		// Query
		List<AffWorkplaceHistory> affWorkplaceHistories = workplaceHistoryRepository
				.searchWorkplaceHistory(baseDate, workplaceIds);

		List<String> employeeIds = affWorkplaceHistories.stream()
				.map(AffWorkplaceHistory::getEmployeeId).collect(Collectors.toList());

		List<Employee> employeeList = employeeRepository.findByListEmployeeId(companyId,
				employeeIds);

		// Return
		return employeeList.stream().map(item -> {
			EmployeeDto dto = new EmployeeDto();
			dto.setCompanyId(item.getCompanyId());
			dto.setPId(item.getPId());
			dto.setSId(item.getSId());
			dto.setSCd(item.getSCd().v());
			dto.setSMail(item.getSMail().v());
			dto.setRetirementDate(item.getRetirementDate());
			dto.setJoinDate(item.getJoinDate());
			return dto;
		}).collect(Collectors.toList());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.bs.employee.pub.employee.EmployeePub#findWpkIdsBySCode(java.
	 * lang.String, java.lang.String, nts.arc.time.GeneralDate)
	 */
	@Override
	public List<String> findWpkIdsBySid(String companyId, String employeeId, GeneralDate baseDate) {
		// Query
		List<AffWorkplaceHistory> affWorkplaceHistories = workplaceHistoryRepository
				.searchWorkplaceHistoryByEmployee(employeeId, baseDate);

		// Return
		return affWorkplaceHistories.stream().map(item -> {
			return item.getWorkplaceId().v();
		}).collect(Collectors.toList());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.bs.employee.pub.employee.EmployeePub#findJobTitleBySid(String)
	 */
	@Override
	public List<PubJobTitleDto> findJobTitleBySid(String employeeId) {
		// Query
		List<AffJobTitleHistory> affJobTitleHistories = this.jobTitleHistoryRepository
				.findBySid(employeeId);

		List<String> jobIds = affJobTitleHistories.stream().map(item -> item.getJobTitleId().v())
				.collect(Collectors.toList());

		List<AcJobTitleDto> jobTitleDtos = this.jobTitleAdapter.findByJobIds(jobIds);

		// Return
		return jobTitleDtos.stream().map(item -> {
			PubJobTitleDto dto = new PubJobTitleDto();
			dto.setCompanyId(item.getCompanyId());
			dto.setPositionId(item.getPositionId());
			dto.setPositionCode(item.getPositionCode());
			dto.setPositionName(item.getPositionName());
			dto.setSequenceCode(item.getSequenceCode());
			dto.setStartDate(item.getStartDate());
			dto.setEndDate(item.getEndDate());
			return dto;
		}).collect(Collectors.toList());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.bs.employee.pub.employee.EmployeePub#findJobTitleBySid(java.
	 * lang.String, nts.arc.time.GeneralDate)
	 */
	@Override
	public List<PubJobTitleDto> findJobTitleBySid(String employeeId, GeneralDate baseDate) {
		// Query
		List<AffJobTitleHistory> affJobTitleHistories = this.jobTitleHistoryRepository
				.findBySid(employeeId, baseDate);

		List<String> jobIds = affJobTitleHistories.stream().map(item -> item.getJobTitleId().v())
				.collect(Collectors.toList());

		List<AcJobTitleDto> jobTitleDtos = this.jobTitleAdapter.findByJobIds(jobIds);

		// Return
		return jobTitleDtos.stream().map(item -> {
			PubJobTitleDto dto = new PubJobTitleDto();
			dto.setCompanyId(item.getCompanyId());
			dto.setPositionId(item.getPositionId());
			dto.setPositionCode(item.getPositionCode());
			dto.setPositionName(item.getPositionName());
			dto.setSequenceCode(item.getSequenceCode());
			dto.setStartDate(item.getStartDate());
			dto.setEndDate(item.getEndDate());
			return dto;
		}).collect(Collectors.toList());
	}

}
