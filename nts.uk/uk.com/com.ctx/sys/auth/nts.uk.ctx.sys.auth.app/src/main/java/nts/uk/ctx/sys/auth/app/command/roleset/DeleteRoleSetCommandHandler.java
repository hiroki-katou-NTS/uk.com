package nts.uk.ctx.sys.auth.app.command.roleset;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.uk.ctx.sys.auth.dom.roleset.ApprovalAuthority;
import nts.uk.ctx.sys.auth.dom.roleset.RoleSet;
import nts.uk.ctx.sys.auth.dom.roleset.RoleSetRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@javax.transaction.Transactional
public class DeleteRoleSetCommandHandler extends CommandHandlerWithResult<RoleSetCommand, String> {

	@Inject
	private RoleSetRepository roleSetRepository;

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
					, command.getSalaryRoleCd());
	
			// Confirm preconditions - 事前条件を確認する - ドメインモデル「既定のロールセット」を取得する
			roleSetDom.validateForDelete();
			
			// remove Role Set from DB - ドメインモデル「ロールセット」を削除する
			this.roleSetRepository.delete(command.getRoleSetCd(), companyId);
	
			// remove web menu link - ドメインモデル「ロールセット別紐付け」を削除する
			// TODO how to call from other context???
		}
		return command.getRoleSetCd();
	}
}
