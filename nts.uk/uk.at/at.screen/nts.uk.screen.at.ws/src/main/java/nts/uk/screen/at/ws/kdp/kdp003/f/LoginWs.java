/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.screen.at.ws.kdp.kdp003.f;

import java.util.List;

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
import nts.uk.screen.at.app.query.kdp.kdp003.f.GetLoginSettingsStampInput;
import nts.uk.screen.at.app.query.kdp.kdp003.f.LoginAsEmbossingAdministrator;
import nts.uk.screen.at.app.query.kdp.kdp003.f.dto.GetListCompanyHasStampedDto;
import nts.uk.shr.com.context.AppContexts;

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
	
	 @POST
	 @Path("getLogginSetting")
	 public List<GetListCompanyHasStampedDto> getAllCompany() {
		return loginSetting.getLoginSettingsForTimeStampInput();
	 }
	 
	@POST
	@Path("adminmode")
	public TimeStampInputLoginDto loginAdminMode(@Context HttpServletRequest request, TimeStampLoginCommand command) {
		
		String programId = AppContexts.programId().substring(0, 6);
		String screenId = AppContexts.programId().substring(6);
		String url = AppContexts.requestedWebApi().getFullRequestPath();
		
		TimeStampInputLoginDto result = loginWithAdmin.loginAsEmbossingAdmin(command.getCompanyId(),
				command.getEmployeeCode(), command.getPassword(),command.getCompanyCode(), true, true, request);
		return result;
	}
	
	@POST
	@Path("employeemode")
	public TimeStampInputLoginDto loginEmployeeMode(@Context HttpServletRequest request, TimeStampLoginCommand command) {
		return null;
		 
		
//		return this.submitForm.authenticateStampedEmployees(
//						command.getCompanyId(), 
//						Optional.ofNullable(command.getEmployeeCode()), 
//						Optional.ofNullable(command.getSid()), 
//						Optional.ofNullable(command.getPassword()), 
//						command.getPasswordInvalid(), 
//						command.getIsAdminMode(), 
//						command.getRuntimeEnvironmentCreat());
//		return new CheckChangePassDto();
	}
	

	
}
