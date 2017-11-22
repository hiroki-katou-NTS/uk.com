package nts.uk.ctx.sys.auth.app.command.roleset;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.uk.ctx.sys.auth.dom.roleset.DefaultRoleSet;
import nts.uk.ctx.sys.auth.dom.roleset.DefaultRoleSetRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@javax.transaction.Transactional
public class AddOrUpdateDefaultRoleSetCommandHandler extends CommandHandlerWithResult<DefaultRoleSetCommand, String> {

	@Inject
	private DefaultRoleSetRepository defaultRoleSetRepository;

	@Override
	protected String handle(CommandHandlerContext<DefaultRoleSetCommand> context) {
		DefaultRoleSetCommand command = context.getCommand();
		String companyId = AppContexts.user().companyId();
		if (StringUtils.isNoneEmpty(companyId)){
			DefaultRoleSet defaultRoleSetDom = new DefaultRoleSet(companyId, command.getRoleSetCd());
			defaultRoleSetRepository.addOrUpdate(defaultRoleSetDom);
		}
		return command.getRoleSetCd();
	}
}
