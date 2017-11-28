package nts.uk.ctx.sys.auth.app.command.roleset;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.uk.ctx.sys.auth.dom.roleset.service.RoleSetService;

@Stateless
@javax.transaction.Transactional
public class DeleteRoleSetCommandHandler extends CommandHandlerWithResult<DeleteRoleSetCommand, String> {

	@Inject
	private RoleSetService roleSetService;

	@Override
	protected String handle(CommandHandlerContext<DeleteRoleSetCommand> context) {
		DeleteRoleSetCommand command = context.getCommand();

		//アルゴリズム「削除」を実行する - Execute algorithm "delete"
		this.roleSetService.executeDelete(command.getRoleSetCd());
		
		return command.getRoleSetCd();
	}
}
