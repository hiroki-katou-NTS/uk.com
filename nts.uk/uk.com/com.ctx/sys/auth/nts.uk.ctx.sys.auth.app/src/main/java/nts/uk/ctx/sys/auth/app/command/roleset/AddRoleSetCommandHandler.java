package nts.uk.ctx.sys.auth.app.command.roleset;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.sys.auth.dom.roleset.ApprovalAuthority;
import nts.uk.ctx.sys.auth.dom.roleset.RoleSet;
import nts.uk.ctx.sys.auth.dom.roleset.service.RoleSetService;
import nts.uk.ctx.sys.auth.dom.roleset.webmenu.webmenulinking.RoleSetLinkWebMenuImport;
import nts.uk.ctx.sys.auth.dom.roleset.webmenu.webmenulinking.RoleSetLinkWebMenuAdapter;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@javax.transaction.Transactional
public class AddRoleSetCommandHandler extends CommandHandlerWithResult<RoleSetCommand, String> {

	@Inject
	private RoleSetService roleSetService;
	
	@Inject
	private RoleSetLinkWebMenuAdapter roleSetAndWebMenuAdapter;
	
	@Override
	protected String handle(CommandHandlerContext<RoleSetCommand> context) {
		RoleSetCommand command = context.getCommand();
		String companyId = AppContexts.user().companyId();
		if (!StringUtils.isNoneEmpty(companyId)) {
			// build webMenuCode
			List<WebMenuCommand> listWebMenus = command.getWebMenus();
			List<String> listWebMenuCds = CollectionUtil.isEmpty(listWebMenus) ?
					listWebMenus.stream().map(item -> item.getWebMenuCd()).collect(Collectors.toList()) : new ArrayList<>();

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
	
			// register to DB - ドメインモデル「ロールセット」を新規登録する
			this.roleSetService.registerRoleSet(roleSetDom);
	
			// pre-check : メニューが１件以上選択されていなければならない: Msg_583, メニュー
			if (CollectionUtil.isEmpty(listWebMenuCds)) {
				throw new BusinessException("Msg_583");
			}

			// register to web menu link - ドメインモデル「ロールセット別紐付け」を新規登録する
			listWebMenuCds.forEach(webMenuCd-> {
				this.roleSetAndWebMenuAdapter.addRoleSetAndWebMenu(new RoleSetLinkWebMenuImport(
						roleSetDom.getCompanyId()
						, roleSetDom.getRoleSetCd().v()
						, webMenuCd));
			});
			return command.getRoleSetCd();
		}
		return null;
	}
}
