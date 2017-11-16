package nts.uk.ctx.sys.auth.app.command.roleset;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.uk.ctx.sys.auth.dom.roleset.ApprovalAuthority;
import nts.uk.ctx.sys.auth.dom.roleset.RoleSet;
import nts.uk.ctx.sys.auth.dom.roleset.RoleSetRepository;
import nts.uk.ctx.sys.auth.dom.roleset.service.RoleSetService;
import nts.uk.ctx.sys.auth.dom.roleset.webmenu.webmenulinking.RoleSetAndWebMenuAdapter;
import nts.uk.shr.com.context.AppContexts;

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
