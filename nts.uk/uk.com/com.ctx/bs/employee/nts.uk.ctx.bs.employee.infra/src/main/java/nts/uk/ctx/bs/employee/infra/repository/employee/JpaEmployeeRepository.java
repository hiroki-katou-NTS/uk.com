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
import entity.employeeinfo.BsymtEmployeePk;
import entity.employeeinfo.jobentryhistory.BsymtJobEntryHistory;
import entity.layout.PpemtMaintenanceLayout;
import entity.layout.PpemtMaintenanceLayoutPk;
import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.bs.employee.dom.deleteempmanagement.DeleteEmpManagement;
import nts.uk.ctx.bs.employee.dom.employeeinfo.Employee;
import nts.uk.ctx.bs.employee.dom.employeeinfo.EmployeeRepository;
import nts.uk.ctx.bs.employee.dom.employeeinfo.JobEntryHistory;
import nts.uk.ctx.bs.employee.infra.entity.empdeletemanagement.BsymtDeleteEmpManagement;
import nts.uk.ctx.bs.employee.infra.entity.empdeletemanagement.BsymtDeleteEmpManagementPK;
import nts.uk.ctx.bs.person.dom.person.info.category.PersonInfoCategory;

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

	public final String SELECT_BY_COMPANY_ID = SELECT_NO_WHERE + " WHERE c.companyId = :companyId";

	public final String SELECT_BY_SID = SELECT_NO_WHERE + " WHERE c.bsymtEmployeePk.sId = :sId";

	public final String SELECT_BY_CID_SID = SELECT_NO_WHERE + " WHERE c.companyId = :companyId"
			+ " AND c.bsymtEmployeePk.sId = :sId";

	// public final String SELECT_BY_SID = SELECT_NO_WHERE + " WHERE
	// c.bsydtEmployeePk.sId = :sId";

	public final String GET_LAST_EMPLOYEE = "SELECT c.employeeCode FROM BsymtEmployee c "
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
	
	public final String GET_ALL_EMPLOYEE_INFO_TO_DELETE = 
			" SELECT c.employeeCode, d.personName, a.bsymtDeleteEmpManagementPK.sid "
			+ " FROM BsymtDeleteEmpManagement a "
			+ " JOIN BsymtEmployee c ON a.bsymtDeleteEmpManagementPK.sid =  c.bsymtEmployeePk.sId "
			+ " JOIN BpsmtPerson d ON c.personId = d.bpsmtPersonPk.pId ";
	
	public final String GET_EMPLOYEE_DETAIL_INFO_TO_DELETE = 
			" SELECT a.deleteDate, a.reason, c.employeeCode, d.personName "
			+ " FROM BsymtDeleteEmpManagement a "
			+ " JOIN BsymtEmployee c ON a.bsymtDeleteEmpManagementPK.sid =  c.bsymtEmployeePk.sId "
			+ " JOIN BpsmtPerson d ON c.personId = d.bpsmtPersonPk.pId "
			+ " WHERE a.bsymtDeleteEmpManagementPK.sid = :sid";
	
	public final String SELECT_BY_SID_CID_SYSTEMDATE = "SELECT c FROM BsymtEmployee c "
			+ " JOIN BsymtJobEntryHistory d ON c.bsymtEmployeePk.sId = d.bsymtJobEntryHistoryPk.sId "
			+ " WHERE c.companyId = :companyId " + " AND c.personId = :personId"
			+ " AND d.bsymtJobEntryHistoryPk.entryDate <= :systemDate" + " AND d.retireDate >= :systemDate";

	private final static String SELECT_CATEGORY_BY_COMPANY_ID_QUERY_1 = "SELECT ca.ppemtPerInfoCtgPK.perInfoCtgId,"
			+ " ca.categoryCd, ca.categoryName, ca.abolitionAtr,"
			+ " co.categoryParentCd, co.categoryType, co.personEmployeeType, co.fixedAtr, po.disporder"
			+ " FROM PpemtPerInfoCtg ca "
			+ " INNER JOIN PpemtPerInfoCtgCm co ON ca.categoryCd = co.ppemtPerInfoCtgCmPK.categoryCd"
			+ " INNER JOIN PpemtPerInfoCtgOrder po ON ca.cid = po.cid AND ca.ppemtPerInfoCtgPK.perInfoCtgId = po.ppemtPerInfoCtgPK.perInfoCtgId"
			+ " WHERE ca.cid = :cid AND co.categoryParentCd IS NULL ORDER BY po.disporder";

	
	/**
	 * convert entity BsymtEmployee to domain Employee
	 * 
	 * @param entity
	 * @return
	 */
	private Employee toDomainEmployee(BsymtEmployee entity) {
		val domain = Employee.createFromJavaType(entity.companyId, entity.personId, entity.bsymtEmployeePk.sId,
				entity.employeeCode, entity.companyMail, entity.companyMobileMail, entity.companyMobile);
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
	
	// mapping 
		private PersonInfoCategory createDomainPerInfoCtgFromEntity(Object[] c) {
			String personInfoCategoryId = String.valueOf(c[0]);
			String categoryCode = String.valueOf(c[1]);
			String categoryName = String.valueOf(c[2]);
			int abolitionAtr = Integer.parseInt(String.valueOf(c[3]));
			String categoryParentCd = (c[4] != null) ? String.valueOf(c[4]) : null;
			int categoryType = Integer.parseInt(String.valueOf(c[5]));
			int personEmployeeType = Integer.parseInt(String.valueOf(c[6]));
			int fixedAtr = Integer.parseInt(String.valueOf(c[7]));
			return PersonInfoCategory.createFromEntity(personInfoCategoryId, null, categoryCode, categoryParentCd,
					categoryName, personEmployeeType, abolitionAtr, categoryType, fixedAtr);
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

	// sonnlb code start

	@Override
	public Boolean isDuplicateEmpCode(String companyId, String employeeCode) {
		return this.queryProxy().query(CHECK_DUPLICATE_EMPLOYEE_CODE_STRING_QUERY, BsymtEmployee.class)
				.setParameter("companyId", companyId)
				.setParameter("employeeCode", employeeCode)
				.getSingle().isPresent();
	}

	@Override
	public Boolean isDuplicateCardNo(String companyId, String cardNumber) {

		return false;
	}
	
	@Override
	public void addNewEmployee(Employee domain) {
		this.commandProxy().insert(toEntityEmployee(domain));
		
	}
	
	//sonnlb code end

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

	
	/**
	 * case : Employee Selected trùng với employee đang nhập 
	 */
	@Override
	public List<PersonInfoCategory> getAllPerInfoCtg(String companyId) {
		// TODO Auto-generated method stub
		return this.queryProxy().query(SELECT_CATEGORY_BY_COMPANY_ID_QUERY_1, Object[].class)
				.setParameter("cid", companyId).getList(c -> {
					return createDomainPerInfoCtgFromEntity(c);
				});
		
	}

	
	/**
	 * case : Employee Selected khác với employee đang nhập 
	 */
	@Override
	public List<PersonInfoCategory> getAllPerInfoCtgOtherEmp(String companyId) {
		// TODO Auto-generated method stub
		return null;
	}

}
