package nts.uk.ctx.sys.portal.app.command.webmenu.webmenulinking;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.uk.ctx.sys.portal.dom.webmenu.webmenulinking.service.RoleSetLinkWebMenuService;

@Stateless
@javax.transaction.Transactional
public class UpdateRoleSetLinkWebMenuCommandHandler extends CommandHandlerWithResult<RoleSetLinkWebMenuCommand, String> {

	@Inject
	private RoleSetLinkWebMenuService roleSetLinkWebMenuService;
	
	@Override
	protected String handle(CommandHandlerContext<RoleSetLinkWebMenuCommand> context) {
		RoleSetLinkWebMenuCommand command = context.getCommand();

		this.roleSetLinkWebMenuService.executeUpdate(
				command.getCompanyId()
				, command.getRoleSetCd()
				, command.getWebMenuCds());
		
		return command.getRoleSetCd();
	}
}
