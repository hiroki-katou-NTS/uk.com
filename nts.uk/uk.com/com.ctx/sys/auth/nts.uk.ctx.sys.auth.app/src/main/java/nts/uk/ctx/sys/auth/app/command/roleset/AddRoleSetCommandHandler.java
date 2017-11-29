package nts.uk.ctx.sys.auth.app.command.roleset;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.uk.ctx.sys.auth.dom.roleset.ApprovalAuthority;
import nts.uk.ctx.sys.auth.dom.roleset.RoleSet;
import nts.uk.ctx.sys.auth.dom.roleset.service.RoleSetService;
import nts.uk.shr.com.context.AppContexts;

@Stateless
//@javax.transaction.Transactional
public class AddRoleSetCommandHandler extends CommandHandlerWithResult<RoleSetCommand, String> {

	@Inject
	private RoleSetService roleSetService;
	
	@Override
	protected String handle(CommandHandlerContext<RoleSetCommand> context) {
		RoleSetCommand command = context.getCommand();
		// build webMenuCode

		RoleSet roleSetDom = new RoleSet(command.getRoleSetCd()
				, AppContexts.user().companyId()
				, command.getRoleSetName()
				, command.isApprovalAuthority() ? ApprovalAuthority.HasRight : ApprovalAuthority.HasntRight
				, command.getOfficeHelperRoleId()
				, command.getMyNumberRoleId()
				, command.getHumanResourceRoleId()
				, command.getPersonInfRoleId()
				, command.getEmploymentRoleId()
				, command.getSalaryRoleId());

		//アルゴリズム「新規登録」を実行する - Execute the algorithm "new registration"
		this.roleSetService.registerRoleSet(roleSetDom);
		
		return command.getRoleSetCd();
	}
}
