package nts.uk.shr.com.context.loginuser;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.scoped.session.SessionContextProvider;
import nts.uk.shr.com.context.LoginUserContext;
import nts.uk.shr.com.context.loginuser.role.DefaultLoginUserRoles;

@Stateless
public class DefaultLoginUserContextManager implements LoginUserContextManager {

	@Override
	public void loggedInAsEmployee(
			String userId,
			String personId,
			String contractCode,
			String companyId,
			String companyCode,
			String employeeId,
			String employeeCode) {
		
		val context = new DefaultLoginUserContext(userId, true);
		context.setPersonId(personId);
		context.setContractCode(contractCode);
		context.setCompanyId(companyId);
		context.setCompanyCode(companyCode);
		context.setEmployeeId(employeeId);
		context.setEmployeeCode(employeeCode);
		
		SessionContextProvider.get().put(LoginUserContext.KEY_SESSION_SCOPED, context);
	}

	@Override
	public RoleIdSetter roleIdSetter() {
		
		DefaultLoginUserContext context = SessionContextProvider.get().get(LoginUserContext.KEY_SESSION_SCOPED);
		DefaultLoginUserRoles roles = (DefaultLoginUserRoles)context.roles();

		return new RoleIdSetter() {
			@Override
			public RoleIdSetter forAttendance(String forPersonInCharge, String forGeneral) {
				roles.setRoleIdsForAttendance(forPersonInCharge, forGeneral);
				return this;
			}
			@Override
			public RoleIdSetter forPayroll(String forPersonInCharge, String forGeneral) {
				roles.setRoleIdsForPayroll(forPersonInCharge, forGeneral);
				return this;
			}
			@Override
			public RoleIdSetter forPersonnel(String forPersonInCharge, String forGeneral) {
				roles.setRoleIdsForPersonnel(forPersonInCharge, forGeneral);
				return this;
			}
			@Override
			public RoleIdSetter forPersonalInfo(String forPersonInCharge, String forGeneral) {
				roles.setRoleIdsforPersonalInfo(forPersonInCharge, forGeneral);
				return this;
			}
		};
	}
}
