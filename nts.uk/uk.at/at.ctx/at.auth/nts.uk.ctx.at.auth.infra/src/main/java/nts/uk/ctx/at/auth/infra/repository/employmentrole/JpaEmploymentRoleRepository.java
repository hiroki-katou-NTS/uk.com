package nts.uk.ctx.at.auth.infra.repository.employmentrole;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.auth.dom.employmentrole.EmploymentRole;
import nts.uk.ctx.at.auth.dom.employmentrole.EmploymentRoleRepository;
import nts.uk.ctx.at.auth.infra.entity.employmentrole.KacmtRoleAttendance;

@Stateless
public class JpaEmploymentRoleRepository extends JpaRepository implements EmploymentRoleRepository {

	private static final String GET_ALL_BY_COMPANY_ID = "SELECT e"
			+ " FROM KacmtRoleAttendance e"
			+ " WHERE e.kacmtEmploymentRolePK.companyID = :companyID";
	//TODO 削除お願いいたします。	
	/*	private static final String GET_EMPLOYMENT_BY_ID = GET_ALL_BY_COMPANY_ID
			+ " AND e.kacmtEmploymentRolePK.roleID = :roleID"*/;

	@Override
	public List<EmploymentRole> getAllByCompanyId(String companyId) {
		return this.queryProxy().query(GET_ALL_BY_COMPANY_ID, KacmtRoleAttendance.class)
		.setParameter("companyID", companyId)
		.getList(c -> c.toDomain());
	}
	
	@Override
	public Optional<EmploymentRole> getEmploymentRoleById( String roleId) {
		//TODO get by roleID 修正お願いいたします。
		/*		return this.queryProxy().query(GET_EMPLOYMENT_BY_ID, KacmtRoleAttendance.class)
				.setParameter("companyID", companyId)
				.setParameter("roleID", roleId)
				.getSingle(c -> c.toDomain());*/
		return Optional.empty();
	}

	@Override
	public void addEmploymentRole(EmploymentRole employmentRole) {
		this.commandProxy().insert(KacmtRoleAttendance.toEntity(employmentRole));
		
	}

	@Override
	public void updateEmploymentRole(EmploymentRole employmentRole) {
		KacmtRoleAttendance dataUpdate = KacmtRoleAttendance.toEntity(employmentRole);
		KacmtRoleAttendance newData = this.queryProxy().find(dataUpdate.kacmtRoleAttendancePK, KacmtRoleAttendance.class).get();
		newData.setScheduleEmployeeRef(dataUpdate.scheduleEmployeeRef);
		newData.setBookEmployeeRef(dataUpdate.bookEmployeeRef);
		newData.setEmployeeRefSpecAgent(dataUpdate.employeeRefSpecAgent);
		newData.setPresentInqEmployeeRef(dataUpdate.presentInqEmployeeRef);
		newData.setFutureDateRefPermit(dataUpdate.futureDateRefPermit);
		this.commandProxy().update(newData);
	}

	@Override
	public void deleteEmploymentRole(String roleId) {
		//TODO delete by roleID -> 修正お願いいたします。
/*		KacmtRoleAttendancePK kacmtRoleAttendancePK = new  KacmtRoleAttendancePK(companyId,roleId);
		this.commandProxy().remove(KacmtRoleAttendance.class,kacmtRoleAttendancePK);*/
	}

}