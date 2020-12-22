package nts.uk.ctx.at.request.app.command.application.lateleaveearly;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.ProcessResult;
import nts.uk.ctx.at.request.dom.application.lateleaveearly.LateLeaveEarlyService;
import nts.uk.shr.com.context.AppContexts;

/**
 * @author anhnm
 *
 */
@Stateless
public class LateEarlyUpdateCommandHandler extends CommandHandlerWithResult<LateEarlyUpdateCommand, ProcessResult> {

	@Inject
	private LateLeaveEarlyService service;

	@Override
	protected ProcessResult handle(CommandHandlerContext<LateEarlyUpdateCommand> context) {
		String companyId = AppContexts.user().companyId();
		LateEarlyUpdateCommand command = context.getCommand();

		return this.service.update(companyId, command.getApplication().toDomain(),
				command.getArrivedLateLeaveEarlyDto().convertDomain(), command.getAppDispInfoStartupDto().toDomain());
	}

}
