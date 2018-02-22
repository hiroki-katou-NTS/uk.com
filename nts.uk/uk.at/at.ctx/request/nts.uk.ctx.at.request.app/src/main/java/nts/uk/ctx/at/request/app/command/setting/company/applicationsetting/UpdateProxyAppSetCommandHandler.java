package nts.uk.ctx.at.request.app.command.setting.company.applicationsetting;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.ProxyAppSet;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.ProxyAppSetRepository;

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
		ProxyAppSetCommand data = context.getCommand();
		if(!data.getAppType().isEmpty()){
			proxyRep.delete(data.getCompanyId());
			for(Integer item : data.getAppType()){
				ProxyAppSet proxy = ProxyAppSet.createFromJavaType(data.getCompanyId(), item);
				proxy.validate();
				proxyRep.insert(proxy);
			}
		}
	}
	
}
