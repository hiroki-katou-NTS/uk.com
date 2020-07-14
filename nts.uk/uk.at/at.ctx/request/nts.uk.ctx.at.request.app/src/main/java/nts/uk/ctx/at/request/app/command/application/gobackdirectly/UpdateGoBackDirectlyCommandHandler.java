package nts.uk.ctx.at.request.app.command.application.gobackdirectly;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.ProcessResult;
import nts.uk.ctx.at.request.dom.application.gobackdirectly.service.GoBackDirectlyRegisterService;
import nts.uk.shr.com.context.AppContexts;
@Stateless
@Transactional
public class UpdateGoBackDirectlyCommandHandler extends CommandHandlerWithResult<UpdateGoBackDirectlyCommand, ProcessResult>{

	@Inject
	private GoBackDirectlyRegisterService goBackDirectlyRegisterService;

	@Override
	protected ProcessResult handle(CommandHandlerContext<UpdateGoBackDirectlyCommand> context) {
		UpdateGoBackDirectlyCommand command = context.getCommand();
		return goBackDirectlyRegisterService.update(AppContexts.user().companyId(),
				command.getApplicationDto().toDomain(), command.getGoBackDirectlyDto().toDomain(),
				command.getInforGoBackCommonDirectDto().toDomain());
	}

}
