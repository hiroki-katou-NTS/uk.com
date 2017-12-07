package nts.uk.screen.com.app.command.sys.auth.role;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.auth.app.command.employmentrole.UpdateEmploymentRoleCmd;
import nts.uk.ctx.at.auth.app.command.employmentrole.UpdateEmploymentRoleCmdHandler;
import nts.uk.ctx.at.auth.app.command.wplmanagementauthority.UpdateWorkPlaceAuthorityCmd;
import nts.uk.ctx.at.auth.app.command.wplmanagementauthority.UpdateWorkPlaceAuthorityCmdHandler;
import nts.uk.ctx.sys.auth.app.command.role.UpdateRoleCommand;
import nts.uk.ctx.sys.auth.app.command.role.UpdateRoleCommandHandler;
import nts.uk.ctx.sys.portal.app.command.webmenu.webmenulinking.RoleByRoleTiesCommand;
import nts.uk.ctx.sys.portal.app.command.webmenu.webmenulinking.UpdateRoleByRoleTiesCommandHandler;

@Stateless
public class UpdateRoleCas005CmdHandler extends CommandHandler<RoleCas005Command> {
	
	@Inject
	private UpdateRoleCommandHandler updateRoleCommandHandler;
	
	@Inject
	private UpdateRoleByRoleTiesCommandHandler updateRoleByRoleTiesCommandHandler;
	
	@Inject
	private UpdateEmploymentRoleCmdHandler updateEmploymentRoleCmdHandler;

	@Inject
	private UpdateWorkPlaceAuthorityCmdHandler updateWorkPlaceAuthorityCmdHandler;
	@Override
	protected void handle(CommandHandlerContext<RoleCas005Command> context) {
		RoleCas005Command data = context.getCommand();
		//update Role
		UpdateRoleCommand updateRoleCommand = new  UpdateRoleCommand(
				data.getRoleId(),
				data.getRoleCode(),
				data.getRoleType(),
				data.getEmployeeReferenceRange(),
				data.getName(),
				data.getContractCode(),
				data.getAssignAtr(),
				data.getCompanyId()
				);
		updateRoleCommandHandler.handle(updateRoleCommand);
		//update RoleByRoleTies
		RoleByRoleTiesCommand roleByRoleTiesCommand = new RoleByRoleTiesCommand(
				data.getWebMenuCd(),
				data.getRoleId()
				);
		updateRoleByRoleTiesCommandHandler.handle(roleByRoleTiesCommand);
		//update EmploymentRole
		
		UpdateEmploymentRoleCmd updateEmploymentRoleCmd = new UpdateEmploymentRoleCmd(
				data.getCompanyId(),
				data.getRoleId(),
				data.getScheduleEmployeeRef(),
				data.getBookEmployeeRef(),
				data.getEmployeeRefSpecAgent(),
				data.getPresentInqEmployeeRef(),
				data.getFutureDateRefPermit()
				);
		updateEmploymentRoleCmdHandler.handle(updateEmploymentRoleCmd);
		
		//update WorkPlaceAuthority
		for(WorkPlaceAuthorityCommand workPlaceAuthorityCommand :data.getListWorkPlaceAuthority()) {
			UpdateWorkPlaceAuthorityCmd updateWorkPlaceAuthorityCmd = new UpdateWorkPlaceAuthorityCmd(
					workPlaceAuthorityCommand.getRoleId(),
					workPlaceAuthorityCommand.getCompanyId(),
					workPlaceAuthorityCommand.getFunctionNo(),
					workPlaceAuthorityCommand.isAvailability()
					);
			updateWorkPlaceAuthorityCmdHandler.handle(updateWorkPlaceAuthorityCmd);
		}
	}

}
