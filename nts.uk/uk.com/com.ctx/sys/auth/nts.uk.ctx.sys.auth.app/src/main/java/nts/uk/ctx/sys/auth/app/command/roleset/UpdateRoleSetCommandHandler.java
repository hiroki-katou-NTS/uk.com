package nts.uk.ctx.sys.auth.app.command.roleset;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.sys.auth.dom.roleset.ApprovalAuthority;
import nts.uk.ctx.sys.auth.dom.roleset.RoleSet;
import nts.uk.ctx.sys.auth.dom.roleset.service.RoleSetService;

@Stateless
@javax.transaction.Transactional
public class UpdateRoleSetCommandHandler extends CommandHandlerWithResult<RoleSetCommand, String> {

	@Inject
	private RoleSetService roleSetService;

	@Override
	protected String handle(CommandHandlerContext<RoleSetCommand> context) {
		RoleSetCommand command = context.getCommand();
		
		// build webMenuCode
		List<WebMenuCommand> listWebMenus = command.getWebMenus();
		List<String> listWebMenuCds = !CollectionUtil.isEmpty(listWebMenus) ?
				listWebMenus.stream().map(item -> item.getWebMenuCd()).collect(Collectors.toList()) : new ArrayList<>();

		RoleSet roleSetDom = new RoleSet(command.getRoleSetCd()
				, command.getCompanyId()
				, command.getRoleSetName()
				, command.isApprovalAuthority() ? ApprovalAuthority.HasRight : ApprovalAuthority.HasntRight
				, command.getOfficeHelperRoleId()
				, command.getMyNumberRoleId()
				, command.getHRRoleId()
				, command.getPersonInfRoleId()
				, command.getEmploymentRoleId()
				, command.getSalaryRoleId());
		
		//アルゴリズム「更新登録」を実行する - Execute algorithm "update registration"
		this.roleSetService.executeUpdate(roleSetDom, listWebMenuCds);

		return roleSetDom.getRoleSetCd().v();
	}
}
