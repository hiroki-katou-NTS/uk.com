package nts.uk.screen.com.app.command.sys.auth.roleset;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.sys.auth.app.command.roleset.RoleSetCommand;
import nts.uk.ctx.sys.auth.app.command.roleset.UpdateRoleSetCommandHandler;
import nts.uk.ctx.sys.portal.app.command.webmenu.WebMenuCommandBase;
import nts.uk.ctx.sys.portal.app.command.webmenu.webmenulinking.RoleSetLinkWebMenuCommand;
import nts.uk.ctx.sys.portal.app.command.webmenu.webmenulinking.UpdateRoleSetLinkWebMenuCommandHandler;

@Stateless
@javax.transaction.Transactional
public class UpdateRoleSetCommandBaseHandler extends CommandHandlerWithResult<RoleSetCommandBase, String> {

	@Inject
	private UpdateRoleSetCommandHandler updateRoleSetCommandHandler;
	
	@Inject
	private UpdateRoleSetLinkWebMenuCommandHandler updateRoleSetLinkWebMenuCommandHandler;

	/**
	 * //アルゴリズム「更新登録」を実行する
	 */
	@Override
	protected String handle(CommandHandlerContext<RoleSetCommandBase> context) {
		RoleSetCommandBase command = context.getCommand();
		
		RoleSetCommand roleSetCommand = new RoleSetCommand(
				command.getRoleSetCd()
				, command.getCompanyId()
				, command.getRoleSetName()
				, command.isApprovalAuthority()
				, command.getOfficeHelperRoleId()
				, command.getMyNumberRoleId()
				, command.getHRRoleId()
				, command.getPersonInfRoleId()
				, command.getEmploymentRoleId()
				, command.getSalaryRoleId());
		// call command to add Role Set base
		this.updateRoleSetCommandHandler.handle(roleSetCommand);
		
		// build Role Set Link Web Menu command
		List<WebMenuCommandBase> listWebMenus = command.getWebMenus();
		
		List<String> listWebMenuCds = !CollectionUtil.isEmpty(listWebMenus) ?
				listWebMenus.stream().map(item -> item.getWebMenuCode()).collect(Collectors.toList()) : new ArrayList<String>();
		RoleSetLinkWebMenuCommand roleSetLinkWebMenuCommand = new RoleSetLinkWebMenuCommand(
				command.getRoleSetCd()
				, command.getCompanyId()
				, listWebMenuCds);
		this.updateRoleSetLinkWebMenuCommandHandler.handle(roleSetLinkWebMenuCommand);

		return command.getRoleSetCd();
	}
}
