/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.screen.at.ws.kdp.kdp003.f;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.screen.at.app.query.kdp.kdp003.f.EmployeeRoleStamping;
import nts.uk.screen.at.app.query.kdp.kdp003.f.GetLoginSettingsStampInput;
import nts.uk.screen.at.app.query.kdp.kdp003.f.GetLoginSettingsStampParam;
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
	private EmployeeRoleStamping employeeRoleStamping;
	
	 @POST
	 @Path("getLogginSetting")
	 public List<GetListCompanyHasStampedDto> getAllCompany(GetLoginSettingsStampParam input) {
		return loginSetting.getLoginSettingsForTimeStampInput(input);
	 }
	 
	@POST
	@Path("employeeRoleStamping")
	public RoleEmployeeStampingDto getRole() {
		return employeeRoleStamping.getRoleEmployee();
	}
}
