package nts.uk.ctx.basic.infra.repository.organization.employee;

import java.util.List;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.basic.dom.organization.employee.Employee;
import nts.uk.ctx.basic.dom.organization.employee.EmployeeRepository;
import nts.uk.ctx.basic.infra.entity.organization.employee.KmnmtEmployee;

@Stateless
public class JpaEmployeeRepository extends JpaRepository implements EmployeeRepository {
	public final String SELECT_NO_WHERE = "SELECT c FROM KmnmtEmployee c";

	public final String SELECT_BY_EMP_CODE = SELECT_NO_WHERE + " WHERE c.KmnmtEmployeePK.companyId = :companyId"
			+ " AND c.KmnmtEmployeePK.employeeCd =:employeeCd";

	private static Employee toDomain(KmnmtEmployee entity) {
		Employee domain = Employee.createFromJavaStyle(entity.kmnmtEmployeePK.companyId,
				entity.kmnmtEmployeePK.personId, 
				entity.kmnmtEmployeePK.employeeId, 
				entity.kmnmtEmployeePK.employeeCd,
				entity.employeeMail, 
				entity.retirementDate, 
				entity.joinDate);
		return domain;
	}

	@Override
	public List<Employee> findByEmployeeCode(String companyId, String employeeCode) {
		return this.queryProxy().query(SELECT_BY_EMP_CODE, KmnmtEmployee.class)
				.setParameter("companyId", companyId)
				.setParameter("employeeCode", employeeCode)
				.getList(c -> toDomain(c));
	}

}
