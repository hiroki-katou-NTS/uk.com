/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.infra.repository.employee;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import entity.employeeinfo.BsymtEmployee;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.bs.employee.dom.employeeinfo.Employee;
import nts.uk.ctx.bs.employee.dom.employeeinfo.EmployeeRepository;
import nts.uk.ctx.bs.employee.dom.employeeinfo.HiringType;
import nts.uk.ctx.bs.employee.dom.employeeinfo.JobEntryHistory;
import nts.uk.ctx.bs.employee.infra.entity.employee.KmnmtEmployee;

@Stateless
public class JpaEmployeeRepository extends JpaRepository implements EmployeeRepository {
	public final String SELECT_NO_WHERE = ""
			+ " SELECT c.companyId,c.personId,c.bsydtEmployeePk.sId,"
			+ " c.employeeCode,c.companyMail,c.companyMobileMail,"
			+ " c.companyMobile,d.retireDate , d.bsydtJobEntryHistoryPk.entryDate , "
			+ " d.hiringType , d.adoptDate"
			+ " FROM BsydtEmployee c , BsymtJobEntryHistory d";

	public final String SELECT_BY_EMP_CODE = SELECT_NO_WHERE + " WHERE c.companyId = :companyId"
			+ " AND c.employeeCode =:employeeCode "
			+ " AND  d.bsydtJobEntryHistoryPk.entryDate <= :entryDate "
			+ " AND d.retireDate >= :entryDate ";

	
	public final String SELECT_BY_LIST_EMP_CODE = SELECT_NO_WHERE + " WHERE c.companyId = :companyId"
			+ " AND c.employeeCode IN :listEmployeeCode "
			+ " AND  d.bsydtJobEntryHistoryPk.entryDate <= :entryDate "
			+ " AND d.retireDate >= :entryDate ";
			

	public final String SELECT_BY_LIST_EMP_ID = SELECT_NO_WHERE + " WHERE c.companyId = :companyId"
			+ " AND c.bsydtEmployeePk.sId IN :employeeIds ";

	public final String SELECT_BY_COMPANY_ID = SELECT_NO_WHERE + " WHERE c.companyId = :companyId";

	public final String SELECT_BY_SID = SELECT_NO_WHERE + " WHERE c.bsydtEmployeePk.sId = :sId";
	
	public final String GET_LAST_EMPLOYEE = "SELECT c.kmnmtEmployeePK.employeeCode FROM KmnmtEmployee c "
			+ " WHERE c.kmnmtEmployeePK.employeeCode LIKE CONCAT(:emlCode, '%')"
			+ " ORDER BY  c.kmnmtEmployeePK.employeeCode DESC";

	/** The Constant DATE_FORMAT FROM Client */
	private static final String DATE_FORMAT = "yyyy/MM/dd";

	/*
	 * private static Employee toDomain(BsymtEmployee entity) { Employee domain =
	 * Employee.createFromJavaType(entity.companyId, entity.personId,
	 * entity.bsydtEmployeePk.sId, entity.employeeCode, entity.companyMail,
	 * entity.retireDate, entity.entryDate); return domain; }
	 */

	private static Employee toDomain(Object[] c) {
		String companyId = String.valueOf(c[0]);
		String personId = String.valueOf(c[1]);
		String employeeId = String.valueOf(c[2]);
		String employeeCode = String.valueOf(c[3]);
		String companyMail = String.valueOf(c[4]);
		String mobileMail = String.valueOf(c[5]);
		String companyMobile = String.valueOf(c[6]);
		String retireDate = String.valueOf(c[7]);
		String entryDate = String.valueOf(c[8]);
		String hiringType = String.valueOf(c[9]);
		String adoptDate = String.valueOf(c[10]);
		List<JobEntryHistory> listEntryHist = new ArrayList<>();
		JobEntryHistory jobEntryHis = new JobEntryHistory();
		jobEntryHis.setRetirementDate(GeneralDate.fromString(retireDate, DATE_FORMAT));
		jobEntryHis.setJoinDate(GeneralDate.fromString(entryDate, DATE_FORMAT));
		jobEntryHis.setAdoptDate(GeneralDate.fromString(adoptDate, DATE_FORMAT));
		jobEntryHis.setHiringType(new HiringType(Integer.valueOf(hiringType)));
		listEntryHist.add(jobEntryHis);

		Employee domain = Employee.createFromJavaType(companyId, personId, employeeId, employeeCode, companyMail,
				mobileMail, companyMobile, listEntryHist);
		return domain;
	}
	
	

	/*
	 * private static Employee toDomainKmnmtEmployee(KmnmtEmployee entity) {
	 * Employee domain =
	 * Employee.createFromJavaType(entity.kmnmtEmployeePK.companyId,
	 * entity.kmnmtEmployeePK.personId, entity.kmnmtEmployeePK.employeeId,
	 * entity.kmnmtEmployeePK.employeeCode, entity.employeeMail,
	 * entity.retirementDate, entity.joinDate); return domain; }
	 */

	@Override
	public Optional<Employee> findByEmployeeCode(String companyId, String employeeCode, GeneralDate entryDate) {
		Optional<Employee> person = this.queryProxy().query(SELECT_BY_EMP_CODE, Object[].class)
				.setParameter("companyId", companyId)
				.setParameter("employeeCode", employeeCode)
				.setParameter("entryDate", entryDate)
				.getSingle(c -> toDomain(c));
		return person;
	}

	@Override
	public List<Employee> findByListEmployeeCode(String companyId, List<String> listEmployeeCode ) {

		// fix bug empty list
		if (CollectionUtil.isEmpty(listEmployeeCode)) {
			return new ArrayList<>();
		}

		List<Employee> lstPerson = this.queryProxy().query(SELECT_BY_LIST_EMP_CODE, Object[].class)
				.setParameter("companyId", companyId)
				.setParameter("listEmployeeCode", listEmployeeCode)
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
		List<Employee> lstPerson = this.queryProxy().query(SELECT_BY_COMPANY_ID, Object[].class)
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

		List<Employee> lstPerson = this.queryProxy().query(SELECT_BY_LIST_EMP_ID, Object[].class)
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
		return this.queryProxy().query(SELECT_BY_SID, Object[].class).setParameter("companyId", companyId)
				.setParameter("sId", employeeId).getSingle(c -> toDomain(c));
	}


	/*vinhpx*/
	@Override
	public String findLastEml(String startLetter) {
		if(startLetter == null) startLetter = "";
		List<Object[]> lst =  this.queryProxy().query(GET_LAST_EMPLOYEE).setParameter("emlCode", startLetter)
				.getList();	
		String returnStr = "";
		if(lst.size() > 0) {
			Object obj =  lst.get(0);
			returnStr = obj.toString();
		}
		
		return returnStr;
	}

}
