/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.infra.repository.company.organization.employee;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import entity.employeeinfo.BsydtEmployee;
import nts.arc.layer.infra.data.JpaRepository;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.basic.infra.entity.company.organization.employee.KmnmtEmployee;
import nts.uk.ctx.bs.employee.dom.employeeinfo.Employee;
import nts.uk.ctx.bs.employee.dom.employeeinfo.EmployeeRepository;

@Stateless
public class JpaEmployeeRepository extends JpaRepository implements EmployeeRepository {
	public final String SELECT_NO_WHERE = "SELECT c FROM BsydtEmployee c";

	public final String SELECT_BY_EMP_CODE = SELECT_NO_WHERE + " WHERE c.companyId = :companyId"
			+ " AND c.employeeCode =:employeeCode";

	public final String SELECT_BY_LIST_EMP_CODE = SELECT_NO_WHERE + " WHERE c.companyId = :companyId"
			+ " AND c.employeeCode IN :listEmployeeCode";

	public final String SELECT_BY_LIST_EMP_ID = SELECT_NO_WHERE + " WHERE c.companyId = :companyId"
			+ " AND c.bsydtEmployeePk.sId IN :employeeIds";

	public final String SELECT_BY_COMPANY_ID = SELECT_NO_WHERE + " WHERE c.companyId = :companyId";

	public final String SELECT_BY_SID = SELECT_NO_WHERE + " WHERE c.bsydtEmployeePk.sId = :sId";

	private static Employee toDomain(BsydtEmployee entity) {
		Employee domain = Employee.createFromJavaType(entity.companyId, entity.personId,
				entity.bsydtEmployeePk.sId, entity.employeeCode, entity.companyMail, entity.retireDate,
				entity.entryDate);
		return domain;
	}

	private static Employee toDomainKmnmtEmployee(KmnmtEmployee entity) {
		Employee domain = Employee.createFromJavaType(entity.kmnmtEmployeePK.companyId,
				entity.kmnmtEmployeePK.personId, entity.kmnmtEmployeePK.employeeId, entity.kmnmtEmployeePK.employeeCode,
				entity.employeeMail, entity.retirementDate, entity.joinDate);
		return domain;
	}

	@Override
	public Optional<Employee> findByEmployeeCode(String companyId, String employeeCode) {
		Optional<Employee> person = this.queryProxy().query(SELECT_BY_EMP_CODE, BsydtEmployee.class)
				.setParameter("companyId", companyId).setParameter("employeeCode", employeeCode)
				.getSingle(c -> toDomain(c));
		return person;
	}

	@Override
	public List<Employee> findByListEmployeeCode(String companyId, List<String> listEmployeeCode) {

		// fix bug empty list
		if (CollectionUtil.isEmpty(listEmployeeCode)) {
			return new ArrayList<>();
		}

		List<Employee> lstPerson = this.queryProxy().query(SELECT_BY_LIST_EMP_CODE, BsydtEmployee.class)
				.setParameter("companyId", companyId).setParameter("listEmployeeCode", listEmployeeCode)
				.getList(c -> toDomain(c));
		return lstPerson;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.basic.dom.company.organization.employee.EmployeeRepository#
	 * getAllEmployee(java.lang.String)
	 */
	@Override
	public List<Employee> findAll(String companyId) {
		List<Employee> lstPerson = this.queryProxy().query(SELECT_BY_COMPANY_ID, BsydtEmployee.class)
				.setParameter("companyId", companyId).getList(c -> toDomain(c));
		return lstPerson;
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

		List<Employee> lstPerson = this.queryProxy().query(SELECT_BY_LIST_EMP_ID, BsydtEmployee.class)
				.setParameter("companyId", companyId).setParameter("employeeIds", employeeIds)
				.getList(c -> toDomain(c));
		return lstPerson;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.basic.dom.company.organization.employee.EmployeeRepository#
	 * findBySid(java.lang.String)
	 */
	@Override
	public Optional<Employee> findBySid(String companyId, String employeeId) {
		return this.queryProxy().query(SELECT_BY_SID, BsydtEmployee.class).setParameter("companyId", companyId)
				.setParameter("sId", employeeId).getSingle(c -> toDomain(c));
	}

}
