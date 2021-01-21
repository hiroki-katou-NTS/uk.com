package nts.uk.ctx.at.request.app.command.application.gobackdirectly;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.ProcessResult;
import nts.uk.ctx.at.request.dom.application.gobackdirectly.GoBackDirectly;
import nts.uk.ctx.at.request.dom.application.gobackdirectly.service.GoBackDirectlyRegisterService;

@Stateless
@Transactional
public class InsertGoBackDirectlyCommandHandler extends CommandHandlerWithResult<InsertGoBackDirectlyCommand, ProcessResult>{

	@Inject
	private GoBackDirectlyRegisterService goBackDirectlyRegisterService;

	@Override
	protected ProcessResult handle(CommandHandlerContext<InsertGoBackDirectlyCommand> context) {
		InsertGoBackDirectlyCommand data = context.getCommand();
		Application application  = data.getApplicationDto().toDomain();
		application = Application.createFromNew(
				application.getPrePostAtr(),
				application.getEmployeeID(),
				application.getAppType(),
				application.getAppDate(),
				application.getEnteredPersonID(),
				application.getOpStampRequestMode(),
				application.getOpReversionReason(),
				application.getOpAppStartDate(),
				application.getOpAppEndDate(),
				application.getOpAppReason(),
				application.getOpAppStandardReasonCD());
		GoBackDirectly goBackDirectly = data.getGoBackDirectlyDto().toDomain();
		goBackDirectly.setAppID(application.getAppID());
		return goBackDirectlyRegisterService.register(goBackDirectly,
				application, data.getInforGoBackCommonDirectDto().toDomain());
	}

}
