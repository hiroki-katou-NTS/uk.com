package nts.uk.ctx.sys.auth.app.command.grant.rolesetjob;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.sys.auth.dom.grant.rolesetjob.RoleSetGrantedJobTitleRepository;

/**
 * 
 * @author HungTT
 *
 */

@Stateless
@Transactional
public class RegisterRoleSetGrantedJobTitleCommandHandler extends CommandHandler<RoleSetGrantedJobTitleCommand> {

	@Inject
	private RoleSetGrantedJobTitleRepository roleSetJobRepo;

	@Override
	// TODO update please
	protected void handle(CommandHandlerContext<RoleSetGrantedJobTitleCommand> context) {
	}
}
