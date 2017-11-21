package nts.uk.ctx.sys.auth.app.command.grant.rolesetperson;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.sys.auth.dom.grant.rolesetperson.RoleSetGrantedPersonRepository;

@Stateless
@Transactional
public class DeleteRoleSetGrantedPersonCommandHandler extends CommandHandler<RoleSetGrantedPersonCommand> {
	@Inject
	private RoleSetGrantedPersonRepository roleSetPersonRepo;

	@Override
	protected void handle(CommandHandlerContext<RoleSetGrantedPersonCommand> context) {
		roleSetPersonRepo.delete(context.getCommand().getEmployeeId());
	}
}
