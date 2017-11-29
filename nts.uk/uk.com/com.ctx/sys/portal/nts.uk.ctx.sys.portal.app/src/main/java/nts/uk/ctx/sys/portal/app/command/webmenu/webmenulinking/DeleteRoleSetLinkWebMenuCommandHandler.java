package nts.uk.ctx.sys.portal.app.command.webmenu.webmenulinking;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.uk.ctx.sys.portal.dom.webmenu.webmenulinking.service.RoleSetLinkWebMenuService;

@Stateless
@javax.transaction.Transactional
public class DeleteRoleSetLinkWebMenuCommandHandler extends CommandHandlerWithResult<DeleteRoleSetLinkWebMenuCommand, String> {

	@Inject
	private RoleSetLinkWebMenuService roleSetLinkWebMenuService;

	@Override
	protected String handle(CommandHandlerContext<DeleteRoleSetLinkWebMenuCommand> context) {
		DeleteRoleSetLinkWebMenuCommand command = context.getCommand();

		//アルゴリズム「削除」を実行する - Execute algorithm "delete"
		this.roleSetLinkWebMenuService.deleteRoleSetLinkWebMenuByRoleCd(command.getRoleSetCd());
		
		return command.getRoleSetCd();
	}
}
