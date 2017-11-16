package nts.uk.ctx.sys.auth.app.command.person.role;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.sys.auth.dom.role.RoleAtr;
import nts.uk.ctx.sys.auth.dom.role.RoleService;
import nts.uk.ctx.sys.auth.dom.role.personrole.PersonRoleRepository;

@Stateless
@Transactional
public class RemovePersonRoleCommandHandler extends CommandHandler<RemovePersonRoleCommand> {
	@Inject
	private RoleService roleService;	
	@Inject
	private PersonRoleRepository personRoleRepo;

	@Override
	protected void handle(CommandHandlerContext<RemovePersonRoleCommand> context) {
		final RemovePersonRoleCommand command = context.getCommand();
		roleService.removeRole(command.getRoleId(), RoleAtr.valueOf(command.getAssignAtr()));
		personRoleRepo.remove(command.getRoleId());
		//TODO
	}

}
