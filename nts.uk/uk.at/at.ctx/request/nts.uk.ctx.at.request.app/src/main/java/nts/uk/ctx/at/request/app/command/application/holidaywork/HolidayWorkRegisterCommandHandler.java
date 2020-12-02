package nts.uk.ctx.at.request.app.command.application.holidaywork;

import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.app.find.application.ApplicationDto;
import nts.uk.ctx.at.request.app.find.application.overtime.AppOvertimeFinder;
import nts.uk.ctx.at.request.dom.application.AppReason;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.ApplicationDate;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.PrePostAtr;
import nts.uk.ctx.at.request.dom.application.ReasonForReversion;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.ProcessResult;
import nts.uk.ctx.at.request.dom.application.holidayworktime.AppHolidayWork;
import nts.uk.ctx.at.request.dom.application.holidayworktime.service.HolidayWorkRegisterService;
import nts.uk.ctx.at.request.dom.application.stamp.StampRequestMode;
import nts.uk.ctx.at.request.dom.setting.company.appreasonstandard.AppStandardReasonCode;

/**
 * Refactor5
 * @author huylq
 *
 */
@Stateless
@Transactional
public class HolidayWorkRegisterCommandHandler extends CommandHandlerWithResult<RegisterCommand, ProcessResult>{
	
	@Inject
	private HolidayWorkRegisterService holidayWorkRegisterService;

	@Override
	protected ProcessResult handle(CommandHandlerContext<RegisterCommand> context) {
		RegisterCommand param = context.getCommand();
		Application application = this.createApplication(param.getAppHolidayWork().getApplication());
		AppHolidayWork appHolidayWork = param.getAppHolidayWork().toDomain();
		appHolidayWork.setApplication(application);
		
		return holidayWorkRegisterService.register(param.getCompanyId(), 
				appHolidayWork, 
				param.getAppTypeSetting().toDomain(), 
				param.getAppHdWorkDispInfo().toDomain(), 
				!param.getApprovalPhaseState().isEmpty() ? 
						param.getApprovalPhaseState().stream().map(approval -> approval.toDomain()).collect(Collectors.toList())
						: Collections.emptyList()
				);
	}
	
	private Application createApplication(ApplicationDto application) {
		if(application == null) return null;
		return Application.createFromNew(
				EnumAdaptor.valueOf(application.getPrePostAtr(), PrePostAtr.class),
				application.getEmployeeID(),
				ApplicationType.OVER_TIME_APPLICATION,
				new ApplicationDate(GeneralDate.fromString(application.getAppDate(), AppOvertimeFinder.PATTERN_DATE)),
				application.getEnteredPerson(),
				application.getOpStampRequestMode() == null ? Optional.empty() : Optional.of(EnumAdaptor.valueOf(application.getOpStampRequestMode(), StampRequestMode.class)),
				application.getOpReversionReason() == null ? Optional.empty() : Optional.of(new ReasonForReversion(application.getOpReversionReason())),
				application.getOpAppStartDate() == null ? Optional.empty() : Optional.of(new ApplicationDate(GeneralDate.fromString(application.getOpAppStartDate(), AppOvertimeFinder.PATTERN_DATE))),
				application.getOpAppEndDate() == null ? Optional.empty() : Optional.of(new ApplicationDate(GeneralDate.fromString(application.getOpAppEndDate(), AppOvertimeFinder.PATTERN_DATE))),
				application.getOpAppReason() == null ? Optional.empty() : Optional.of(new AppReason(application.getOpAppReason())),
				application.getOpAppStandardReasonCD() == null ? Optional.empty() : Optional.of(new AppStandardReasonCode(application.getOpAppStandardReasonCD())));
	}

}
