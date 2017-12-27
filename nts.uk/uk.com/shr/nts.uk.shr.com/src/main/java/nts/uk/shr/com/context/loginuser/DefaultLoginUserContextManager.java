package nts.uk.shr.com.context.loginuser;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.scoped.session.SessionContextProvider;
import nts.uk.shr.com.context.LoginUserContext;
import nts.uk.shr.com.context.loginuser.role.DefaultLoginUserRoles;

@Stateless
public class DefaultLoginUserContextManager implements LoginUserContextManager {

	@Inject
	private SessionLowLayer sessionLowLayer;
	
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
		
		this.sessionLowLayer.loggedIn();
	}

	@Override
	public RoleIdSetter roleIdSetter() {
		
		DefaultLoginUserContext context = SessionContextProvider.get().get(LoginUserContext.KEY_SESSION_SCOPED);
		DefaultLoginUserRoles roles = (DefaultLoginUserRoles)context.roles();

		return new RoleIdSetter() {
			@Override
			public RoleIdSetter forAttendance(String roleId) {
				roles.setRoleIdForAttendance(roleId);
				return this;
			}
			@Override
			public RoleIdSetter forPayroll(String roleId) {
				roles.setRoleIdForPayroll(roleId);
				return this;
			}
			@Override
			public RoleIdSetter forPersonnel(String roleId) {
				roles.setRoleIdForPersonnel(roleId);
				return this;
			}
			@Override
			public RoleIdSetter forPersonalInfo(String roleId) {
				roles.setRoleIdforPersonalInfo(roleId);
				return this;
			}
			@Override
			public RoleIdSetter forOfficeHelper(String roleId) {
				roles.setRoleIdforOfficeHelper(roleId);
				return this;
			}
			@Override
			public RoleIdSetter forSystemAdmin(String roleId) {
				roles.setRoleIdforSystemAdmin(roleId);
				return this;
			}
			@Override
			public RoleIdSetter forCompanyAdmin(String roleId) {
				roles.setRoleIdforCompanyAdmin(roleId);
				return this;
			}
		};
	}

	@Override
	public void setLanguage(String basic, String forPersonName) {
		// TODO Auto-generated method stub
		
	}
}
