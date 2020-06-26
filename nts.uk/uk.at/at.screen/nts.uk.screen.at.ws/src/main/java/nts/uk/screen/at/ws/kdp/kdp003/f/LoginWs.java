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
import nts.uk.ctx.sys.gateway.app.command.login.dto.CheckChangePassDto;
import nts.uk.ctx.sys.gateway.app.command.loginkdp.TimeStampInputLoginCommand;
import nts.uk.screen.at.app.query.kdp.kdp003.f.AuthenStampEmployee;
import nts.uk.screen.at.app.query.kdp.kdp003.f.GetLoginSettingsStampInput;
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
	private AuthenStampEmployee submitForm;

	 @POST
	 @Path("getLogginSetting")
	 public List<GetListCompanyHasStampedDto> getAllCompany() {
		return loginSetting.getLoginSettingsForTimeStampInput();
	 }
	 
	@POST
	@Path("submit")
	public CheckChangePassDto submitLogin(@Context HttpServletRequest request, TimeStampInputLoginCommand command) {
		command.setRequest(request);
		return this.submitForm.authenticateStampedEmployees(
						command.getCompanyId(), 
						Optional.ofNullable(command.getEmployeeCode()), 
						Optional.ofNullable(command.getSid()), 
						Optional.ofNullable(command.getPassword()), 
						command.getPasswordInvalid(), 
						command.getIsAdminMode(), 
						command.getRuntimeEnvironmentCreat());
	}
	

	
}
