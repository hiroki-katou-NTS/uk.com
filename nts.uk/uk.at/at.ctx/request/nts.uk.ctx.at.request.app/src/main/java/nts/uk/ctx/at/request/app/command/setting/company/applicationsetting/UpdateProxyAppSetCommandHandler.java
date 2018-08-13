package nts.uk.ctx.at.request.app.command.setting.company.applicationsetting;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.ProxyAppSet;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.ProxyAppSetRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * update Proxy App Set
 * @author yennth
 *
 */
@Stateless
@Transactional
public class UpdateProxyAppSetCommandHandler extends CommandHandler<ProxyAppSetCommand>{
	@Inject
	private ProxyAppSetRepository proxyRep;

	@Override
	protected void handle(CommandHandlerContext<ProxyAppSetCommand> context) {
		String companyId = AppContexts.user().companyId();
		ProxyAppSetCommand data = context.getCommand();
		proxyRep.delete(companyId);
		for(Integer item : data.getAppType()){
			ProxyAppSet proxy = ProxyAppSet.createFromJavaType(companyId, item);
			proxy.validate();
			proxyRep.insert(proxy);
		}
	}
}
