package nts.uk.ctx.sys.auth.app.command.roleset;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.uk.ctx.sys.auth.dom.roleset.service.RoleSetService;
import nts.uk.ctx.sys.auth.dom.roleset.webmenu.webmenulinking.RoleSetAndWebMenuAdapter;

@Stateless
@javax.transaction.Transactional
public class DeleteRoleSetCommandHandler extends CommandHandlerWithResult<DeleteRoleSetCommand, String> {

	@Inject
	private RoleSetService roleSetService;

	@Inject
	private RoleSetAndWebMenuAdapter roleSetAndWebMenuAdapter;
	
	@Override
	protected String handle(CommandHandlerContext<DeleteRoleSetCommand> context) {
		DeleteRoleSetCommand command = context.getCommand();

		// remove Role Set from DB - ドメインモデル「ロールセット」を削除する
		this.roleSetService.deleteRoleSet(command.getRoleSetCd());
					
		// remove web menu link - ドメインモデル「ロールセット別紐付け」を削除する
		roleSetAndWebMenuAdapter.deleteAllRoleSetAndWebMenu(command.getRoleSetCd());
		
		return command.getRoleSetCd();
	}
}
