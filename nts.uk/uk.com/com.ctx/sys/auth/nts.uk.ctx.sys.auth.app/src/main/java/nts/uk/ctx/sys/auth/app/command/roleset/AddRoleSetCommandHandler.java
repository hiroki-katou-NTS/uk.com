package nts.uk.ctx.sys.auth.app.command.roleset;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BundledBusinessException;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.gul.collection.CollectionUtil;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.sys.auth.dom.roleset.ApprovalAuthority;
import nts.uk.ctx.sys.auth.dom.roleset.RoleSet;
import nts.uk.ctx.sys.auth.dom.roleset.RoleSetRepository;

@Stateless
@javax.transaction.Transactional
public class AddRoleSetCommandHandler extends CommandHandlerWithResult<RoleSetCommand, String> {

	@Inject
	private RoleSetRepository roleSetRepository;

	@Override
	protected String handle(CommandHandlerContext<RoleSetCommand> context) {
		RoleSetCommand command = context.getCommand();

		RoleSet roleSetDom = new RoleSet(command.getRoleSetCd()
				, command.getCompanyId()
				, command.getRoleSetName()
				, command.isApprovalAuthority() ? ApprovalAuthority.HasRight : ApprovalAuthority.HasntRight
				, command.getOfficeHelperRoleCd()
				, command.getMyNumberRoleCd()
				, command.getHRRoleCd()
				, command.getPersonInfRoleCd()
				, command.getEmploymentRoleCd()
				, command.getSalaryRoleCd());

		// pre-check : メニューが１件以上選択されていなければならない: Msg_583, メニュー
		if (CollectionUtil.isEmpty(command.getWebMenuCds())) {
			BundledBusinessException bbe = BundledBusinessException.newInstance();
			bbe.addMessage("Msg_583");
			bbe.throwExceptions();
		}

		// validate
		roleSetDom.validate();
		
		// register to DB - ドメインモデル「ロールセット」を新規登録する
		this.roleSetRepository.insert(roleSetDom);

		// register to web menu link - ドメインモデル「ロールセット別紐付け」を新規登録する
		// TODO how to call from other context???

		return command.getRoleSetCd();
	}
}
