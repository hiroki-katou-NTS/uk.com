/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.infra.repository.employee;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import entity.employeeinfo.BsymtEmployee;
import entity.employeeinfo.jobentryhistory.BsymtJobEntryHistory;
import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.bs.employee.dom.employeeinfo.Employee;
import nts.uk.ctx.bs.employee.dom.employeeinfo.EmployeeRepository;
import nts.uk.ctx.bs.employee.dom.employeeinfo.JobEntryHistory;

@Stateless
public class JpaEmployeeRepository extends JpaRepository implements EmployeeRepository {
	public final String SELECT_NO_WHERE = "SELECT c ,d FROM BsydtEmployee c , BsymtJobEntryHistory d";

	public final String SELECT_BY_EMP_CODE = SELECT_NO_WHERE + " WHERE c.companyId = :companyId"
			+ " AND c.employeeCode =:employeeCode " + " AND  d.bsydtJobEntryHistoryPk.entryDate <= :entryDate "
			+ " AND d.retireDate >= :entryDate ";

	public final String SELECT_BY_LIST_EMP_CODE = SELECT_NO_WHERE + " WHERE c.companyId = :companyId"
			+ " AND c.employeeCode IN :listEmployeeCode " + " AND  d.bsydtJobEntryHistoryPk.entryDate <= :entryDate "
			+ " AND d.retireDate >= :entryDate ";

	public final String SELECT_BY_LIST_EMP_ID = SELECT_NO_WHERE + " WHERE c.companyId = :companyId"
			+ " AND c.bsydtEmployeePk.sId IN :employeeIds ";

	public final String SELECT_BY_COMPANY_ID = SELECT_NO_WHERE + " WHERE c.companyId = :companyId";

	public final String SELECT_BY_SID = SELECT_NO_WHERE + " WHERE c.bsydtEmployeePk.sId = :sId";

	/** The Constant DATE_FORMAT FROM Client */
	private static final String DATE_FORMAT = "yyyy/MM/dd";

	/**
	 * convert entity BsymtEmployee to domain Employee
	 * 
	 * @param entity
	 * @return
	 */
	private Employee toDomainEmployee(BsymtEmployee entity) {
		val domain = Employee.createFromJavaType(
				entity.companyId, 
				entity.personId,
				entity.bsydtEmployeePk.sId,
				entity.employeeCode,
				entity.companyMail, 
				entity.companyMobileMail, 
				entity.companyMobile);
		return domain;
	}

	/**
	 * convert entity BsymtJobEntryHistory to domain JobEntryHistory
	 * 
	 * @param entity
	 * @return
	 */
	private JobEntryHistory toDomainJobEntryHist(BsymtJobEntryHistory entity) {

		val domain = JobEntryHistory.createFromJavaType(
				entity.companyId, 
				entity.bsydtJobEntryHistoryPk.sId,
				entity.hiringType,
				entity.retireDate, 
				entity.bsydtJobEntryHistoryPk.entryDate, 
				entity.adoptDate);
		return domain;
	}

	@Override
	public Optional<Employee> findByEmployeeCode(String companyId, String employeeCode, GeneralDate entryDate) {
		BsymtEmployee entity = this.queryProxy().query(SELECT_BY_EMP_CODE, BsymtEmployee.class)
				.setParameter("companyId", companyId)
				.setParameter("employeeCode", employeeCode)
				.setParameter("entryDate", entryDate)
				.getSingleOrNull();

		Employee person = new Employee();
		if (entity != null) {
			person = toDomainEmployee(entity);

			List<JobEntryHistory> listJobEntry = new ArrayList<>();

			if (!entity.listEntryHist.isEmpty()) {
				person.setListEntryJobHist(
						entity.listEntryHist.stream().map(c -> toDomainJobEntryHist(c)).collect(Collectors.toList()));
			}
		}
		return Optional.of(person);
	}

	@Override
	public List<Employee> findByListEmployeeCode(String companyId, List<String> listEmployeeCode) {

		// fix bug empty list
		if (CollectionUtil.isEmpty(listEmployeeCode)) {
			return new ArrayList<>();
		}

		List<BsymtEmployee> listEmployyEntity = this.queryProxy().query(SELECT_BY_LIST_EMP_CODE, BsymtEmployee.class)
				.setParameter("companyId", companyId)
				.setParameter("listEmployeeCode", listEmployeeCode)
				.getList();

		return toListEmployee(listEmployyEntity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.basic.dom.company.organization.employee.EmployeeRepository#
	 * getAllEmployee(java.lang.String)
	 */
	@Override
	public List<Employee> findAll(String companyId) {
		
		List<BsymtEmployee> listEmpEntity = this.queryProxy().query(SELECT_BY_COMPANY_ID, BsymtEmployee.class)
				.setParameter("companyId", companyId).getList();
		
		return toListEmployee(listEmpEntity);
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.basic.dom.company.organization.employee.EmployeeRepository#
	 * getListPersonByListEmployeeId(java.lang.String, java.util.List)
	 */
	@Override
	public List<Employee> findByListEmployeeId(String companyId, List<String> employeeIds) {
		// fix bug empty list
		if (CollectionUtil.isEmpty(employeeIds)) {
			return new ArrayList<>();
		}

		List<BsymtEmployee> listEmpEntity = this.queryProxy().query(SELECT_BY_LIST_EMP_ID, BsymtEmployee.class)
				.setParameter("companyId", companyId)
				.setParameter("employeeIds", employeeIds)
				.getList();
		return toListEmployee(listEmpEntity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.basic.dom.company.organization.employee.EmployeeRepository#
	 * findBySid(java.lang.String)
	 */
	@Override
	public Optional<Employee> findBySid(String companyId, String employeeId) {
		
		BsymtEmployee entity = this.queryProxy().query(SELECT_BY_SID, BsymtEmployee.class)
				.setParameter("companyId", companyId)
				.setParameter("sId", employeeId)
				.getSingleOrNull();
		
		Employee person = new Employee();
		if (entity != null) {
			person = toDomainEmployee(entity);

			List<JobEntryHistory> listJobEntry = new ArrayList<>();

			if (!entity.listEntryHist.isEmpty()) {
				person.setListEntryJobHist(
						entity.listEntryHist.stream().map(c -> toDomainJobEntryHist(c)).collect(Collectors.toList()));
			}
		}
		return Optional.of(person);
		
	}
	
	/**
	 * @param listEmpEntity
	 * @return
	 */
	private List<Employee> toListEmployee(List<BsymtEmployee> listEmpEntity) {
		List<Employee> lstPerson = new ArrayList<>();
		if (!listEmpEntity.isEmpty()) {
			listEmpEntity.stream().forEach(c -> {
				Employee employee = toDomainEmployee(c);
				employee.setListEntryJobHist(
						c.listEntryHist.stream().map(d -> toDomainJobEntryHist(d)).collect(Collectors.toList()));
				lstPerson.add(employee);
			});
		}
		return lstPerson;
	}

}
