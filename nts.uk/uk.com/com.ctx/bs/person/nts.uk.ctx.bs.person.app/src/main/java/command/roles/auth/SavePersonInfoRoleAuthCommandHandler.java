package command.roles.auth;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.bs.person.dom.person.role.auth.PersonInfoRoleAuthRepository;
import nts.uk.ctx.bs.person.dom.person.role.auth.category.PersonInfoCategoryAuthRepository;
import nts.uk.ctx.bs.person.dom.person.role.auth.item.PersonInfoItemAuthRepository;

@Stateless
@Transactional
public class SavePersonInfoRoleAuthCommandHandler extends CommandHandler<SavePersonInfoRoleAuthCommand> {
	@Inject
	private PersonInfoRoleAuthRepository personRoleAuthRepository;
	@Inject
	private PersonInfoCategoryAuthRepository personCategoryAuthRepository;
	@Inject
	private PersonInfoItemAuthRepository personItemAuthRepository;

	@Override
	public void handle(CommandHandlerContext<SavePersonInfoRoleAuthCommand> context) {
		SavePersonInfoRoleAuthCommand roleCommand = context.getCommand();
		//this.personRoleAuthRepository.getDetailPersonRoleAuth(roleCommand.getRoleId())
	}

}
