/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.screen.at.ws.kdp.kdp003.f;

import java.util.List;
import java.util.Optional;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.sys.gateway.app.command.loginkdp.TimeStampInputLoginDto;
import nts.uk.ctx.sys.gateway.app.command.loginkdp.TimeStampLoginCommand;
import nts.uk.screen.at.app.query.kdp.kdp003.f.AuthenStampEmployee;
import nts.uk.screen.at.app.query.kdp.kdp003.f.EmployeeRoleStamping;
import nts.uk.screen.at.app.query.kdp.kdp003.f.GetLoginSettingsStampInput;
import nts.uk.screen.at.app.query.kdp.kdp003.f.GetLoginSettingsStampParam;
import nts.uk.screen.at.app.query.kdp.kdp003.f.LoginAsEmbossingAdministrator;
import nts.uk.screen.at.app.query.kdp.kdp003.f.RoleEmployeeStampingDto;
import nts.uk.screen.at.app.query.kdp.kdp003.f.dto.GetListCompanyHasStampedDto;

/**
 * The Class LoginWs.
 */
@Path("ctx/sys/gateway/kdp/login")
@Produces("application/json")
public class LoginWs extends WebService {

	@Inject
	private GetLoginSettingsStampInput loginSetting;

	@Inject
	private AuthenStampEmployee loginWithEmployee;

	@Inject
	private LoginAsEmbossingAdministrator loginWithAdmin;
	
	@Inject
	private EmployeeRoleStamping employeeRoleStamping;
	
	 @POST
	 @Path("getLogginSetting")
	 public List<GetListCompanyHasStampedDto> getAllCompany(GetLoginSettingsStampParam input) {
		return loginSetting.getLoginSettingsForTimeStampInput(input);
	 }
	 
	@POST
	@Path("adminmode")
	public TimeStampInputLoginDto loginAdminMode(@Context HttpServletRequest request, TimeStampLoginCommand command) {		
		return loginWithAdmin.loginAsEmbossingAdmin(command.getCompanyId(),
				command.getEmployeeCode(), command.getPassword(),command.getCompanyCode(), command.isPasswordInvalid(), command.isAdminMode(), command.isRuntimeEnvironmentCreate(), request);
	}
	
	@POST
	@Path("employeemode")
	public TimeStampInputLoginDto loginEmployeeMode(@Context HttpServletRequest request, TimeStampLoginCommand command) {
		return loginWithEmployee.authenticateStampedEmployees(command.getCompanyId(), Optional.ofNullable(command.getEmployeeCode()), Optional.ofNullable(command.getEmployeeId()), Optional.ofNullable(command.getPassword()), command.isPasswordInvalid(), command.isAdminMode(), false, request);
	}

	@POST
	@Path("employeeRoleStamping")
	public RoleEmployeeStampingDto getRole() {
		return employeeRoleStamping.getRoleEmployee();
	}
}
