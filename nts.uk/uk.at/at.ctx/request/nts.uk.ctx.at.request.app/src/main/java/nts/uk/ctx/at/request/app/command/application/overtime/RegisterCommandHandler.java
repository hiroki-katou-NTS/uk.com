package nts.uk.ctx.at.request.app.command.application.overtime;

import java.util.Collections;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.gul.collection.CollectionUtil;

import nts.uk.ctx.at.request.dom.application.Application;

import nts.uk.ctx.at.request.dom.application.common.service.other.output.ProcessResult;
import nts.uk.ctx.at.request.dom.application.overtime.AppOverTime;
import nts.uk.ctx.at.request.dom.application.overtime.service.OverTimeRegisterService;


@Stateless
@Transactional
public class RegisterCommandHandler extends CommandHandlerWithResult<RegisterCommand, ProcessResult> {
	@Inject
	private OverTimeRegisterService overTimeRegisterService;
	@Override
	protected ProcessResult handle(CommandHandlerContext<RegisterCommand> context) {
		RegisterCommand param = context.getCommand();
		Application application = param.appOverTime.application.toDomain();
		AppOverTime appOverTime = param.appOverTime.toDomain();
		if (appOverTime.getDetailOverTimeOp().isPresent()) {
			appOverTime.getDetailOverTimeOp().get().setAppId(application.getAppID());
		}
		appOverTime.setApplication(application);
		
		return overTimeRegisterService.register(
				param.companyId,
				appOverTime,
				param.appDispInfoStartupDto.toDomain(),
				param.isMail,
				param.appTypeSetting.toDomain());
	}
	
//	private Application createApplication(ApplicationDto application) {
//		
//		return Application.createFromNew(
//				EnumAdaptor.valueOf(application.getPrePostAtr(), PrePostAtr.class),
//				application.getEmployeeID(),
//				ApplicationType.OVER_TIME_APPLICATION,
//				new ApplicationDate(GeneralDate.fromString(application.getAppDate(), AppOvertimeFinder.PATTERN_DATE)),
//				application.getEnteredPerson(),
//				application.getOpStampRequestMode() == null ? Optional.empty() : Optional.of(EnumAdaptor.valueOf(application.getOpStampRequestMode(), StampRequestMode.class)),
//				application.getOpReversionReason() == null ? Optional.empty() : Optional.of(new ReasonForReversion(application.getOpReversionReason())),
//				application.getOpAppStartDate() == null ? Optional.empty() : Optional.of(new ApplicationDate(GeneralDate.fromString(application.getOpAppStartDate(), AppOvertimeFinder.PATTERN_DATE))),
//				application.getOpAppEndDate() == null ? Optional.empty() : Optional.of(new ApplicationDate(GeneralDate.fromString(application.getOpAppEndDate(), AppOvertimeFinder.PATTERN_DATE))),
//				application.getOpAppReason() == null ? Optional.empty() : Optional.of(new AppReason(application.getOpAppReason())),
//				application.getOpAppStandardReasonCD() == null ? Optional.empty() : Optional.of(new AppStandardReasonCode(application.getOpAppStandardReasonCD())));
//	}

}
