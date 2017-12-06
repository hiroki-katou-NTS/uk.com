package nts.uk.screen.com.app.command.sys.auth.role;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.sys.auth.app.command.role.AddRoleCommand;
import nts.uk.ctx.sys.auth.app.command.role.AddRoleCommandHandler;
import nts.uk.ctx.sys.auth.dom.role.Role;
import nts.uk.ctx.sys.portal.app.command.webmenu.webmenulinking.AddRoleByRoleTiesCommandHandler;
import nts.uk.ctx.sys.portal.app.command.webmenu.webmenulinking.RoleByRoleTiesCommand;

@Stateless
public class CreateRoleCas005CmdHandler extends CommandHandler<RoleCas005Command> {

	@Inject
	private AddRoleCommandHandler addRoleCommandHandler;
	
	@Inject
	private AddRoleByRoleTiesCommandHandler addRoleByRoleTiesCommandHandler;
	
//	@Inject
//	private CreateEmploymentRoleCmdHandler createEmploymentRoleCmdHandler;
	
//	@Inject
//	private CreateWorkPlaceAuthorityCmdHandler createWorkPlaceAuthorityCmdHandler;
	
	@Override
	protected void handle(CommandHandlerContext<RoleCas005Command> context) {
		RoleCas005Command data = context.getCommand();
		//insert Role
		AddRoleCommand addRoleCommand = new AddRoleCommand(
				data.getRoleId(),
				data.getRoleCode(),
				data.getRoleType(),
				data.getEmployeeReferenceRange(),
				data.getName(),
				data.getContractCode(),
				data.getAssignAtr(),
				data.getCompanyId()
				);		
		addRoleCommandHandler.handle(addRoleCommand);
		//insert  RoleByRoleTies
		RoleByRoleTiesCommand roleByRoleTiesCommand = new RoleByRoleTiesCommand(
				data.getWebMenuCd(),
				data.getRoleId()
				);
		addRoleByRoleTiesCommandHandler.handle(roleByRoleTiesCommand);
		//insert EmploymentRole
		
//		CreateEmploymentRoleCmd createEmploymentRoleCmd  = new CreateEmploymentRoleCmd(
//				data.getCompanyId(),
//				data.getRoleId(),
//				data.getScheduleEmployeeRef(),
//				data.getBookEmployeeRef(),
//				data.getEmployeeRefSpecAgent(),
//				data.getPresentInqEmployeeRef(),
//				data.getFutureDateRefPermit()
//				);
//		createEmploymentRoleCmdHandler.handle(createEmploymentRoleCmd);
		
		//insert WorkPlaceAuthority
//		for(WorkPlaceAuthorityCommand workPlaceAuthorityCommand :data.getListWorkPlaceAuthority()) {
//			CreateWorkPlaceAuthorityCmd createWorkPlaceAuthorityCmd = new CreateWorkPlaceAuthorityCmd(
//					workPlaceAuthorityCommand.getRoleId(),
//					workPlaceAuthorityCommand.getCompanyId(),
//					workPlaceAuthorityCommand.getFunctionNo(),
//					workPlaceAuthorityCommand.isAvailability()
//					);
//			createWorkPlaceAuthorityCmdHandler.handle(createWorkPlaceAuthorityCmd);
//		}
	}

}
