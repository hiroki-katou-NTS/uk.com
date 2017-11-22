package nts.uk.ctx.sys.auth.app.command.roleset;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.uk.ctx.sys.auth.dom.roleset.ApprovalAuthority;
import nts.uk.ctx.sys.auth.dom.roleset.RoleSet;
import nts.uk.ctx.sys.auth.dom.roleset.service.RoleSetService;
import nts.uk.ctx.sys.auth.dom.roleset.webmenu.webmenulinking.RoleSetAndWebMenuAdapter;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@javax.transaction.Transactional
public class AddRoleSetCommandHandler extends CommandHandlerWithResult<RoleSetCommand, String> {

	@Inject
	private RoleSetService roleSetService;
	
	@Inject
	private RoleSetAndWebMenuAdapter roleSetAndWebMenuAdapter;
	
	@Override
	protected String handle(CommandHandlerContext<RoleSetCommand> context) {
		RoleSetCommand command = context.getCommand();
		String companyId = AppContexts.user().companyId();
		if (!StringUtils.isNoneEmpty(companyId)) {
			RoleSet roleSetDom = new RoleSet(command.getRoleSetCd()
					, companyId
					, command.getRoleSetName()
					, command.isApprovalAuthority() ? ApprovalAuthority.HasRight : ApprovalAuthority.HasntRight
					, command.getOfficeHelperRoleCd()
					, command.getMyNumberRoleCd()
					, command.getHRRoleCd()
					, command.getPersonInfRoleCd()
					, command.getEmploymentRoleCd()
					, command.getSalaryRoleCd()
					, command.getWebMenuCds());
	
			// register to DB - ドメインモデル「ロールセット」を新規登録する
			this.roleSetService.registerRoleSet(roleSetDom);
	
			// register to web menu link - ドメインモデル「ロールセット別紐付け」を新規登録する
			roleSetAndWebMenuAdapter.addListOfRoleSetAndWebMenu(roleSetDom.getRoleSetAndWebMenus());
			return command.getRoleSetCd();
		}
		return null;
	}
}
