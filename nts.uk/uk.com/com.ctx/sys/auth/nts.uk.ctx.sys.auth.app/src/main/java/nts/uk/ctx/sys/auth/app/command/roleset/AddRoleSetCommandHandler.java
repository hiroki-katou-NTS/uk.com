package nts.uk.ctx.sys.auth.app.command.roleset;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import nts.arc.error.BundledBusinessException;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.sys.auth.dom.roleset.ApprovalAuthority;
import nts.uk.ctx.sys.auth.dom.roleset.RoleSet;
import nts.uk.ctx.sys.auth.dom.roleset.RoleSetRepository;
import nts.uk.ctx.sys.auth.dom.roleset.webmenu.webmenulinking.RoleSetAndWebMenu;
import nts.uk.ctx.sys.auth.dom.roleset.webmenu.webmenulinking.RoleSetAndWebMenuAdapter;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@javax.transaction.Transactional
public class AddRoleSetCommandHandler extends CommandHandlerWithResult<RoleSetCommand, String> {

	@Inject
	private RoleSetRepository roleSetRepository;
	
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
			List<RoleSetAndWebMenu> lstRoleSetAndWebMenu = command.getWebMenuCds().stream()
					.map(webMenuCd -> new RoleSetAndWebMenu(companyId, webMenuCd, command.getRoleSetCd())
					).collect(Collectors.toList());
			roleSetAndWebMenuAdapter.addListOfRoleSetAndWebMenu(lstRoleSetAndWebMenu);
			return command.getRoleSetCd();
		}
		return null;
	}
}
