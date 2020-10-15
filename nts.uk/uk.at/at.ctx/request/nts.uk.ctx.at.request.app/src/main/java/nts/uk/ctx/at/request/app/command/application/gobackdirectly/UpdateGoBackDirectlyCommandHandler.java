package nts.uk.ctx.at.request.app.command.application.gobackdirectly;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.uk.ctx.at.request.app.find.application.ApplicationDto;
import nts.uk.ctx.at.request.app.find.application.gobackdirectly.GoBackDirectlyDto;
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
		ApplicationDto application = command.getApplicationDto();
		GoBackDirectlyDto goBackApplication = command.getGoBackDirectlyDto();
		goBackApplication.setAppID(application.getAppID());
		return goBackDirectlyRegisterService.update(AppContexts.user().companyId(),
				application.toDomain(), goBackApplication.toDomain() ,
				command.getInforGoBackCommonDirectDto().toDomain());
	}

}
