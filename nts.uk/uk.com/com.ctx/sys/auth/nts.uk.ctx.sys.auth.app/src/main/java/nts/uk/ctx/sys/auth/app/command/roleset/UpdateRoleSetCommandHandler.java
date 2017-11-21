package nts.uk.ctx.sys.auth.app.command.roleset;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.sys.auth.dom.roleset.ApprovalAuthority;
import nts.uk.ctx.sys.auth.dom.roleset.RoleSet;
import nts.uk.ctx.sys.auth.dom.roleset.RoleSetUtils;
import nts.uk.ctx.sys.auth.dom.roleset.service.RoleSetService;
import nts.uk.ctx.sys.auth.dom.roleset.webmenu.webmenulinking.RoleSetAndWebMenuAdapter;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@javax.transaction.Transactional
public class UpdateRoleSetCommandHandler extends CommandHandlerWithResult<RoleSetCommand, String> {

	@Inject
	private RoleSetService roleSetService;

	@Inject
	private RoleSetAndWebMenuAdapter roleSetAndWebMenuAdapter;

	@Override
	protected String handle(CommandHandlerContext<RoleSetCommand> context) {
		RoleSetCommand command = context.getCommand();
		String companyId = AppContexts.user().companyId();
		if (!StringUtils.isNoneEmpty(companyId)) {
			return null;
		}
		
		// build webMenuCode
		List<WebMenuCommand> listWebMenus = command.getWebMenus();
		List<String> listWebMenuCds = CollectionUtil.isEmpty(listWebMenus) ?
				listWebMenus.stream().map(item -> item.getWebMenuCd()).collect(Collectors.toList()) : new ArrayList<>();

		RoleSet roleSetDom = new RoleSet(command.getRoleSetCd()
				, command.getCompanyId()
				, command.getRoleSetName()
				, command.isApprovalAuthority() ? ApprovalAuthority.HasRight : ApprovalAuthority.HasntRight
				, command.getOfficeHelperRoleCd()
				, command.getMyNumberRoleCd()
				, command.getHRRoleCd()
				, command.getPersonInfRoleCd()
				, command.getEmploymentRoleCd()
				, command.getSalaryRoleCd()
				, listWebMenuCds);
			
		// update to DB - ロールセット更新登録
		this.roleSetService.updateRoleSet(roleSetDom);

		// update to web menu link - アルゴリズム「ロールセット別紐付け更新登録」を実行する
		// step 1: delete all web menu
		roleSetAndWebMenuAdapter.deleteAllRoleSetAndWebMenu(roleSetDom.getRoleSetCd().v());
		// step 2: register
		roleSetAndWebMenuAdapter.addListOfRoleSetAndWebMenu(
				RoleSetUtils.buildRoleSetAndWebMenu(roleSetDom.getCompanyId(),
						roleSetDom.getRoleSetCd().v(), roleSetDom.getRoleSetAndWebMenuCds()));
		
		return roleSetDom.getRoleSetCd().v();
	}
}
