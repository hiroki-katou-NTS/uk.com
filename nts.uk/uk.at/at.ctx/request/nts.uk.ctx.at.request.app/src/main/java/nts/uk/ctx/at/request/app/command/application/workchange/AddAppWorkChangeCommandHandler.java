package nts.uk.ctx.at.request.app.command.application.workchange;

import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;

import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.ProcessResult;
import nts.uk.ctx.at.request.dom.application.workchange.IWorkChangeRegisterService;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@Transactional
public class AddAppWorkChangeCommandHandler extends CommandHandlerWithResult<AddAppWorkChangeCommand, ProcessResult> {
	
	@Inject
	private IWorkChangeRegisterService workChangeRegisterService;
	
	@Override
	protected ProcessResult handle(CommandHandlerContext<AddAppWorkChangeCommand> context) {
		// error EA refactor 4
		AddAppWorkChangeCommand command = context.getCommand();
		command.getApplicationDto().setEmployeeID(AppContexts.user().employeeId());
		Application application = command.getApplicationDto().toDomain();
		if (StringUtils.isBlank(application.getAppID())) {

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
		    }
		
		return workChangeRegisterService.registerProcess(
				command.getMode(),
				command.getCompanyId(),
				application,
				command.getAppWorkChangeDto().toDomain(application),
				command.getHolidayDates() == null ? null : command.getHolidayDates().stream().map(x -> GeneralDate.fromString(x, "yyyy/MM/dd")).collect(Collectors.toList()),
				command.getIsMail(),
				command.getAppDispInfoStartupDto().toDomain());
		
	}
}
