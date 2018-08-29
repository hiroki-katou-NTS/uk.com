/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.auth.ws.employmentrole;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.arc.error.BusinessException;
import nts.arc.error.RawErrorMessage;
import nts.uk.ctx.at.auth.app.command.employmentrole.CreateEmploymentRoleCmd;
import nts.uk.ctx.at.auth.app.command.employmentrole.CreateEmploymentRoleCmdHandler;
import nts.uk.ctx.at.auth.app.command.employmentrole.DeleteEmploymentRoleCmd;
import nts.uk.ctx.at.auth.app.command.employmentrole.DeleteEmploymentRoleCmdHandler;
import nts.uk.ctx.at.auth.app.command.employmentrole.UpdateEmploymentRoleCmd;
import nts.uk.ctx.at.auth.app.command.employmentrole.UpdateEmploymentRoleCmdHandler;
import nts.uk.ctx.at.auth.app.find.employmentrole.EmploymentRoleDataFinder;
import nts.uk.ctx.at.auth.app.find.employmentrole.dto.EmploymentRoleDataDto;
import nts.uk.shr.com.context.AppContexts;

//import nts.uk.ctx.at.auth.pub.wkpmanager.WorkplaceManagerExport;
//import nts.uk.ctx.at.auth.pub.wkpmanager.WorkplaceManagerPub;

@Path("at/auth/workplace/employmentrole")
@Produces(MediaType.APPLICATION_JSON)
public class EmploymentRoleDataWebService {
	
	@Inject
	private EmploymentRoleDataFinder employmentRoleFinder;
	
	@Inject
	private CreateEmploymentRoleCmdHandler addEmploymentRole;
	
	@Inject
	private UpdateEmploymentRoleCmdHandler updateEmploymentRole;
	
	@Inject
	private DeleteEmploymentRoleCmdHandler deleteEmploymentRole;
	
	/** The Constant HAS_PERMISSION. */
	private static final int HAS_PERMISSION = 0;
	
	/** Finder */
	//get all list employment role
	@POST
	@Path("getlistemploymentrole")
	public List<EmploymentRoleDataDto> getListEmploymentRole(){
		List<EmploymentRoleDataDto> data = this.employmentRoleFinder.getListEmploymentRole();
		return data;
	}
	//get employment role by role Id
	@POST
	@Path("getemploymentrolebyid/{roleId}")
	public EmploymentRoleDataDto getEmploymentRoleById(@PathParam("roleId") String roleId) {
		EmploymentRoleDataDto data = this.employmentRoleFinder.getEmploymentRoleById(roleId);
		return data;
	}
	
	
	/** Handler */
	// add employment role
	@POST
	@Path("addemploymentrole")
	public void addEmploymentRole(CreateEmploymentRoleCmd command) {
		this.addEmploymentRole.handle(command);
	}
	
	// update employment role
	@POST
	@Path("updateemploymentrole")
	public void updateEmploymentRole(UpdateEmploymentRoleCmd command) {
		this.updateEmploymentRole.handle(command);
	}
	
	// update employment role
	@POST
	@Path("deleteemploymentrole")
	public void deleteEmploymentRole(DeleteEmploymentRoleCmd command) {
		this.deleteEmploymentRole.handle(command);
	}

	@POST
	@Path("get/futurerefpermit")
	public boolean getFutureDateRefPermit() {
		String roleId = AppContexts.user().roles().forAttendance(); // 就業
		if (roleId == null) {
			return false;
		}
		return this.employmentRoleFinder.getEmploymentRoleById(roleId).getFutureDateRefPermit() == HAS_PERMISSION ? true
				: false;
	}

}
