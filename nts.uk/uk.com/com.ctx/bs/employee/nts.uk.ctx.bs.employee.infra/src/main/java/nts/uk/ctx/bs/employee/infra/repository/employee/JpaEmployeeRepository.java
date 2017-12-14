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

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.bs.employee.dom.employeeinfo.Employee;
import nts.uk.ctx.bs.employee.dom.employeeinfo.EmployeeRepository;
import nts.uk.ctx.bs.employee.dom.employeeinfo.JobEntryHistory;
import nts.uk.ctx.bs.employee.infra.entity.employee.BsymtEmployee;
import nts.uk.ctx.bs.employee.infra.entity.employee.BsymtEmployeePk;
import nts.uk.ctx.bs.employee.infra.entity.employee.jobentryhistory.BsymtJobEntryHistory;

@Stateless
public class JpaEmployeeRepository extends JpaRepository implements EmployeeRepository {

	// public final String SELECT_BY_EMP_CODE = SELECT_NO_WHERE + " WHERE
	// c.companyId = :companyId"
	// + " AND c.employeeCode =:employeeCode "
	// + " AND d.bsydtJobEntryHistoryPk.entryDate <= :entryDate "
	// + " AND d.retireDate >= :entryDate ";

	public final String SELECT_NO_WHERE = "SELECT c FROM BsymtEmployee c";

	/*
	 * public final String SELECT_BY_EMP_CODE = SELECT_NO_WHERE +
	 * " WHERE c.companyId = :companyId" + " AND c.employeeCode =:employeeCode "
	 * + " AND  c.listEntryHist.bsymtJobEntryHistoryPk.entryDate <= :entryDate "
	 * + " AND d.retireDate >= :entryDate ";
	 */

	public final String SELECT_BY_LIST_EMP_CODE = SELECT_NO_WHERE + " WHERE c.companyId = :companyId"
			+ " AND c.employeeCode IN :listEmployeeCode ";

	public final String SELECT_BY_LIST_EMP_ID = SELECT_NO_WHERE + " WHERE c.companyId = :companyId"
			+ " AND c.bsymtEmployeePk.sId IN :employeeIds ";

	public final String SELECT_BY_LIST_EMP_ID_2 = SELECT_NO_WHERE + " WHERE c.bsymtEmployeePk.sId IN :employeeIds ";

	public final String SELECT_BY_COMPANY_ID = SELECT_NO_WHERE + " WHERE c.companyId = :companyId";

	public final String SELECT_BY_SID = SELECT_NO_WHERE + " WHERE c.bsymtEmployeePk.sId = :sId";

	public final String SELECT_BY_CID_SID = SELECT_NO_WHERE + " WHERE c.companyId = :companyId"
			+ " AND c.bsymtEmployeePk.sId = :sId";

	// public final String SELECT_BY_SID = SELECT_NO_WHERE + " WHERE
	// c.bsydtEmployeePk.sId = :sId";

	public final String GET_LAST_EMPLOYEE = "SELECT c.employeeCode FROM BsymtEmployeeDataMngInfo c "
			+ " WHERE c.companyId = :companyId AND c.employeeCode LIKE CONCAT(:emlCode, '%')"
			+ " ORDER BY  c.employeeCode DESC";

	public final String SELECT_BY_EMP_CODE = "SELECT c FROM BsymtEmployee c "
			+ " JOIN BsymtJobEntryHistory d ON c.bsymtEmployeePk.sId = d.bsymtJobEntryHistoryPk.sId "
			+ " WHERE c.companyId = :companyId " + " AND c.employeeCode =:employeeCode"
			+ " AND d.bsymtJobEntryHistoryPk.entryDate <= :standardDate" + " AND d.retireDate >= :standardDate";

	public final String CHECK_DUPLICATE_EMPLOYEE_CODE_STRING_QUERY = "SELECT c FROM BsymtEmployee c"
			+ " WHERE c.companyId = :companyId " + " AND c.employeeCode =:employeeCode";

	public final String SELECT_BY_STANDARDDATE = "SELECT c FROM BsymtEmployee c "
			+ " JOIN BsymtJobEntryHistory d ON c.bsymtEmployeePk.sId = d.bsymtJobEntryHistoryPk.sId "
			+ " WHERE c.companyId = :companyId " + " AND d.bsymtJobEntryHistoryPk.entryDate <= :standardDate"
			+ " AND d.retireDate >= :standardDate";

	public final String GET_EMPLOYEE_INFO_TO_DELETE = "SELECT c.employeeCode, d.personName FROM BsymtEmployee c "
			+ " JOIN BpsmtPerson d ON c.personId = d.bpsmtPersonPk.pId " + " WHERE c.bsymtEmployeePk.sId = :sId";

	public final String GET_ALL_EMPLOYEE_INFO_TO_DELETE = " SELECT c.employeeCode, d.personName, a.bsymtDeleteEmpManagementPK.sid "
			+ " FROM BsymtDeleteEmpManagement a "
			+ " JOIN BsymtEmployee c ON a.bsymtDeleteEmpManagementPK.sid =  c.bsymtEmployeePk.sId "
			+ " JOIN BpsmtPerson d ON c.personId = d.bpsmtPersonPk.pId ";

	public final String GET_EMPLOYEE_DETAIL_INFO_TO_DELETE = " SELECT a.deleteDate, a.reason, c.employeeCode, d.personName "
			+ " FROM BsymtDeleteEmpManagement a "
			+ " JOIN BsymtEmployee c ON a.bsymtDeleteEmpManagementPK.sid =  c.bsymtEmployeePk.sId "
			+ " JOIN BpsmtPerson d ON c.personId = d.bpsmtPersonPk.pId "
			+ " WHERE a.bsymtDeleteEmpManagementPK.sid = :sid";

	public final String SELECT_BY_SID_CID_SYSTEMDATE = "SELECT c FROM BsymtEmployee c "
			+ " JOIN BsymtJobEntryHistory d ON c.bsymtEmployeePk.sId = d.bsymtJobEntryHistoryPk.sId "
			+ " WHERE c.companyId = :companyId " + " AND c.personId = :personId"
			+ " AND d.bsymtJobEntryHistoryPk.entryDate <= :systemDate" + " AND d.retireDate >= :systemDate";

	private final String SELECT_EMPLOYEE_BY_EMP_ID = SELECT_NO_WHERE + " WHERE c.bsymtEmployeePk.sId = :employeeId";

	public final String SELECT_BY_EMP_ID = "SELECT distinct c FROM BsymtEmployee c "
			+ " INNER JOIN BsymtJobEntryHistory d " + " ON c.bsymtEmployeePk.sId = d.bsymtJobEntryHistoryPk.sId "
			+ " AND c.companyId = d.companyId" + " WHERE c.companyId = :companyId " + " AND c.bsymtEmployeePk.sId =:sId"
			+ " AND d.bsymtJobEntryHistoryPk.entryDate <= :standardDate" + " AND d.retireDate >= :standardDate";

	/**
	 * convert entity BsymtEmployee to domain Employee
	 * 
	 * @param entity
	 * @return
	 */
	private Employee toDomainEmployee(BsymtEmployee entity) {
		val domain = Employee.createFromJavaType(entity.companyId, entity.personId, entity.bsymtEmployeePk.sId,
				entity.employeeCode, entity.companyMail, entity.companyMobileMail, entity.companyMobile);

		List<JobEntryHistory> lstEntryHistory = entity.listEntryHist.stream().map(m -> toDomainJobEntryHist(m))
				.collect(Collectors.toList());

		domain.setListEntryJobHist(lstEntryHistory);
		return domain;
	}

	/**
	 * convert entity BsymtJobEntryHistory to domain JobEntryHistory
	 * 
	 * @param entity
	 * @return
	 */
	private JobEntryHistory toDomainJobEntryHist(BsymtJobEntryHistory entity) {

		val domain = JobEntryHistory.createFromJavaType(entity.companyId, entity.bsymtJobEntryHistoryPk.sId,
				entity.hiringType, entity.retireDate, entity.bsymtJobEntryHistoryPk.entryDate, entity.adoptDate);
		return domain;
	}

	private BsymtEmployee toEntityEmployee(Employee domain) {
		BsymtEmployee entity = new BsymtEmployee();
		entity.bsymtEmployeePk = new BsymtEmployeePk(domain.getSId().toString());
		entity.companyId = domain.getCompanyId();
		entity.companyMail = domain.getCompanyMail().v();
		entity.companyMobile = domain.getCompanyMobile().v();
		entity.employeeCode = domain.getSCd().v();
		entity.personId = domain.getPId();
		entity.companyMobileMail = domain.getMobileMail().v();
		return entity;
	}

	@Override
	public Optional<Employee> findByEmployeeCode(String companyId, String employeeCode, GeneralDate standardDate) {
		BsymtEmployee entity = this.queryProxy().query(SELECT_BY_EMP_CODE, BsymtEmployee.class)
				.setParameter("companyId", companyId).setParameter("employeeCode", employeeCode)
				.setParameter("standardDate", standardDate).getSingleOrNull();

		Employee person = new Employee();
		if (entity != null) {
			person = toDomainEmployee(entity);

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
				.setParameter("companyId", companyId).setParameter("listEmployeeCode", listEmployeeCode).getList();

		return toListEmployee(listEmployyEntity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.basic.dom.company.organization.employee.EmployeeRepository#
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
	 * @see
	 * nts.uk.ctx.basic.dom.company.organization.employee.EmployeeRepository#
	 * getListPersonByListEmployeeId(java.lang.String, java.util.List)
	 */
	@Override
	public List<Employee> findByListEmployeeId(String companyId, List<String> employeeIds) {
		// fix bug empty list
		if (CollectionUtil.isEmpty(employeeIds)) {
			return new ArrayList<>();
		}

		List<BsymtEmployee> listEmpEntity = this.queryProxy().query(SELECT_BY_LIST_EMP_ID, BsymtEmployee.class)
				.setParameter("companyId", companyId).setParameter("employeeIds", employeeIds).getList();
		return toListEmployee(listEmpEntity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.basic.dom.company.organization.employee.EmployeeRepository#
	 * findBySid(java.lang.String)
	 */
	@Override
	public Optional<Employee> findBySid(String companyId, String employeeId) {

		BsymtEmployee entity = this.queryProxy().query(SELECT_BY_CID_SID, BsymtEmployee.class)
				.setParameter("companyId", companyId).setParameter("sId", employeeId).getSingleOrNull();

		Employee person = new Employee();
		if (entity != null) {
			person = toDomainEmployee(entity);

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

	@Override
	public List<Employee> getListEmpByStandardDate(String companyId, GeneralDate standardDate) {
		List<BsymtEmployee> listEmpEntity = this.queryProxy().query(SELECT_BY_STANDARDDATE, BsymtEmployee.class)
				.setParameter("companyId", companyId).setParameter("standardDate", standardDate).getList();

		return toListEmployee(listEmpEntity);
	}

	@Override
	public Optional<Employee> getBySid(String employeeId) {
		BsymtEmployee entity = this.queryProxy().query(SELECT_BY_SID, BsymtEmployee.class)
				.setParameter("sId", employeeId).getSingleOrNull();

		Employee person = new Employee();
		if (entity != null) {
			person = toDomainEmployee(entity);

			if (!entity.listEntryHist.isEmpty()) {
				person.setListEntryJobHist(
						entity.listEntryHist.stream().map(c -> toDomainJobEntryHist(c)).collect(Collectors.toList()));
			}
		}
		return Optional.of(person);
	}

	/* vinhpx */
	@Override
	public String findLastEml(String companyId, String startLetter) {
		if (startLetter == null)
			startLetter = "";
		List<Object[]> lst = this.queryProxy().query(GET_LAST_EMPLOYEE).setParameter("companyId", companyId)
				.setParameter("emlCode", startLetter).getList();
		String returnStr = "";
		if (lst.size() > 0) {
			Object obj = lst.get(0);
			returnStr = obj.toString();
		}

		return returnStr;
	}


	// laitv
	@Override
	public Optional<Object[]> getEmployeeInfoToDelete(String employeeId) {

		Optional<Object[]> empInfo = this.queryProxy().query(GET_EMPLOYEE_INFO_TO_DELETE)
				.setParameter("sId", employeeId).getSingle();
		return empInfo;
	}

	@Override
	public List<Object[]> getAllEmployeeInfoToDelete() {
		List<Object[]> lst = this.queryProxy().query(GET_ALL_EMPLOYEE_INFO_TO_DELETE).getList();
		return lst;
	}

	@Override
	public Optional<Object[]> getEmployeeDetailToDelete(String employeeId) {

		Optional<Object[]> empDetailInfo = this.queryProxy().query(GET_EMPLOYEE_DETAIL_INFO_TO_DELETE)
				.setParameter("sid", employeeId).getSingle();
		return empDetailInfo;
	}

	@Override
	public void updateEmployee(Employee domain) {
		this.commandProxy().update(toEntityEmployee(domain));
	}

	@Override
	public Optional<Employee> findBySidCidSystemDate(String companyId, String personId, GeneralDate systemDate) {
		BsymtEmployee entity = this.queryProxy().query(SELECT_BY_SID_CID_SYSTEMDATE, BsymtEmployee.class)
				.setParameter("companyId", companyId).setParameter("personId", personId)
				.setParameter("systemDate", systemDate).getSingleOrNull();

		Employee emp = new Employee();
		if (entity != null) {
			emp = toDomainEmployee(entity);

			if (!entity.listEntryHist.isEmpty()) {
				emp.setListEntryJobHist(
						entity.listEntryHist.stream().map(c -> toDomainJobEntryHist(c)).collect(Collectors.toList()));
			}
		}
		return Optional.of(emp);
	}

	/*
	 * for requestList No.126 (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.bs.employee.dom.employeeinfo.EmployeeRepository#
	 * GetByListEmployeeId(java.util.List)
	 */
	@Override
	public List<Employee> getByListEmployeeId(List<String> employeeIds) {

		if (CollectionUtil.isEmpty(employeeIds)) {
			return new ArrayList<>();
		}
		List<BsymtEmployee> listEmpEntity = this.queryProxy().query(SELECT_BY_LIST_EMP_ID_2, BsymtEmployee.class)
				.setParameter("employeeIds", employeeIds).getList();
		return toListEmployee(listEmpEntity);
	}

	@Override
	public Optional<Employee> getInfoById(String employeeId) {
		return queryProxy().query(SELECT_EMPLOYEE_BY_EMP_ID, BsymtEmployee.class).setParameter("employeeId", employeeId)
				.getSingle().map(m -> toDomainEmployee(m));
	}

	@Override
	public Optional<Employee> findByEmployeeID(String companyId, String sid, GeneralDate standardDate) {
		Optional<BsymtEmployee> entity = this.queryProxy().query(SELECT_BY_EMP_ID, BsymtEmployee.class)
				.setParameter("companyId", companyId).setParameter("sId", sid)
				.setParameter("standardDate", standardDate).getSingle();

		Employee emp = new Employee();
		if (entity.isPresent()) {
			emp = toDomainEmployee(entity.get());

			if (!entity.get().listEntryHist.isEmpty()) {
				emp.setListEntryJobHist(entity.get().listEntryHist.stream().map(c -> toDomainJobEntryHist(c))
						.collect(Collectors.toList()));
			}
		}
		return Optional.of(emp);
	}
}
