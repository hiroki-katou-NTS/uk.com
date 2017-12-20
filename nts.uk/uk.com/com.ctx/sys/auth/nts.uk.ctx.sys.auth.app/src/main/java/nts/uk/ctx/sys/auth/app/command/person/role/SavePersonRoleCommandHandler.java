package nts.uk.ctx.sys.auth.app.command.person.role;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.sys.auth.dom.role.Role;
import nts.uk.ctx.sys.auth.dom.role.RoleService;
import nts.uk.ctx.sys.auth.dom.role.personrole.PersonRole;
import nts.uk.ctx.sys.auth.dom.role.personrole.PersonRoleRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@Transactional
public class SavePersonRoleCommandHandler extends CommandHandler<SavePersonRoleCommand> {
	@Inject
	private PersonRoleRepository personRoleRepo;
	@Inject
	private RoleService roleService;	
	@Override
	protected void handle(CommandHandlerContext<SavePersonRoleCommand> context) {
		final SavePersonRoleCommand command = context.getCommand();

		if (command.getCreateMode()) {
			insertPersonInfoRole(command);
		} else {
			updatePersonInfoRole(command);
		}

	}

	private void insertPersonInfoRole(SavePersonRoleCommand command) {
		Role role = command.toDomain(AppContexts.user().companyId(), AppContexts.user().contractCode());
		roleService.insertRole(role);
		
		PersonRole personRole = new PersonRole();
		personRole.setRoleId(role.getRoleId());
		personRole.setReferFutureDate(command.getReferFutureDate());
		personRoleRepo.insert(personRole);
	}

	private void updatePersonInfoRole(SavePersonRoleCommand command) {
		Role role = command.toDomain(AppContexts.user().companyId(), AppContexts.user().contractCode());
		roleService.updateRole(role);
		
		PersonRole personRole = new PersonRole();
		personRole.setRoleId(role.getRoleId());
		personRole.setReferFutureDate(command.getReferFutureDate());
		personRoleRepo.update(personRole);
	}
}
