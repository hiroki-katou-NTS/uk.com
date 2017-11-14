package nts.uk.ctx.sys.auth.app.command.person.role;

import java.util.Optional;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;
import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.sys.auth.dom.role.PersonRole;
import nts.uk.ctx.sys.auth.dom.role.PersonRoleRepository;
import nts.uk.ctx.sys.auth.dom.role.Role;
import nts.uk.ctx.sys.auth.dom.role.RoleRepository;
import nts.uk.ctx.sys.auth.dom.role.RoleType;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@Transactional
public class SavePersonRoleCommandHandler extends CommandHandler<SavePersonRoleCommand> {
	@Inject
	private RoleRepository roleRepo;
	@Inject
	private PersonRoleRepository personRoleRepo;

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
		
		Optional<Role> roleOpt = roleRepo.findByType(RoleType.valueOf(command.getRoleType()));
		if (roleOpt.isPresent() && roleOpt.get().getRoleCode().toString().equals(command.getRoleCode()))
			throw new BusinessException("Msg_3");
		
		roleRepo.insert(command.toDomain(AppContexts.user().companyId(), AppContexts.user().contractCode()));
		
		PersonRole personRole = new PersonRole();
		personRole.setRoleId(command.getRoleId());
		personRole.setReferFutureDate(command.getReferFutureDate());
		personRoleRepo.insert(personRole);
	}

	private void updatePersonInfoRole(SavePersonRoleCommand command) {
		roleRepo.update(command.toDomain(AppContexts.user().companyId(), AppContexts.user().contractCode()));
	}
}
