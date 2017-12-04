package nts.uk.screen.com.app.command.sys.auth.role;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.sys.auth.app.command.role.DeleteRoleCommand;
import nts.uk.ctx.sys.auth.app.command.role.DeleteRoleCommandHandler;
import nts.uk.ctx.sys.portal.app.command.webmenu.webmenulinking.DeleteRoleByRoleTiesCommand;
import nts.uk.ctx.sys.portal.app.command.webmenu.webmenulinking.DeleteRoleByRoleTiesCommandHanndler;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class DeleteRoleCas005CommandHandler extends CommandHandler<DeleteRoleCas005Command> {

	@Inject
	private DeleteRoleCommandHandler deleteRoleCommandHandler;
	
	@Inject
	private DeleteRoleByRoleTiesCommandHanndler deleteRoleByRoleTiesCommandHanndler;
	
//	@Inject
//	private DeleteEmploymentRoleCmdHandler deleteEmploymentRoleCmdHandler;
	
//	@Inject
//	private DeleteWorkPlaceAuthorityCmdHandler deleteWorkPlaceAuthorityCmdHandler;
	
	
	@Override
	protected void handle(CommandHandlerContext<DeleteRoleCas005Command> context) {
		String roleId = context.getCommand().getRoleId();
		String companyId = AppContexts.user().companyId();
		//delete Role
		DeleteRoleCommand deleteRoleCommand = new DeleteRoleCommand(
				roleId
				);
		deleteRoleCommandHandler.handle(deleteRoleCommand);
		//delete RoleByRoleTies
		DeleteRoleByRoleTiesCommand DeleteRoleByRoleTiesCommand = new  DeleteRoleByRoleTiesCommand(
				roleId
				);
		deleteRoleByRoleTiesCommandHanndler.handle(DeleteRoleByRoleTiesCommand);
		
		//delete EmploymentRole
//		DeleteEmploymentRoleCmd deleteEmploymentRoleCmd = new  DeleteEmploymentRoleCmd(
//				companyId,
//				roleId
//				);
//		deleteEmploymentRoleCmdHandler.handle(deleteEmploymentRoleCmd);
		
		//delete WorkPlaceAuthority
		
//		DeleteWorkPlaceAuthorityCmd deleteWorkPlaceAuthorityCmd = new DeleteWorkPlaceAuthorityCmd(
//				workPlaceAuthorityCommand.getRoleId(),
//				companyId
//				);
//		deleteWorkPlaceAuthorityCmdHandler.handle(deleteWorkPlaceAuthorityCmd);
	}

}
