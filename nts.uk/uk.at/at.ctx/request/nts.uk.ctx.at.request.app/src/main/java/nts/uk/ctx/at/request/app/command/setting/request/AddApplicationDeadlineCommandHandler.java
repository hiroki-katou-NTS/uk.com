package nts.uk.ctx.at.request.app.command.setting.request;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.request.dom.setting.request.application.ApplicationDeadline;
import nts.uk.ctx.at.request.dom.setting.request.application.ApplicationDeadlineRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * add application deadline
 * @author yennth
 */
@Stateless
@Transactional
public class AddApplicationDeadlineCommandHandler extends CommandHandler<ApplicationDeadlineCommand>{
	@Inject
	private ApplicationDeadlineRepository appRep;
	/**
	 * add application deadline
	 */

	@Override
	protected void handle(CommandHandlerContext<ApplicationDeadlineCommand> context) {
		ApplicationDeadlineCommand data = context.getCommand();
		String companyId = AppContexts.user().companyId();
		Optional<ApplicationDeadline> appDeadline = appRep.getDeadlineByClosureId(companyId, data.getClosureId());
		if(appDeadline.isPresent()){
			throw new BusinessException("Msg_3");
		}
		ApplicationDeadline appDead = data.toDomain(data.getClosureId());
		appDead.validate();
		appRep.insert(appDead);
	}
	
}
