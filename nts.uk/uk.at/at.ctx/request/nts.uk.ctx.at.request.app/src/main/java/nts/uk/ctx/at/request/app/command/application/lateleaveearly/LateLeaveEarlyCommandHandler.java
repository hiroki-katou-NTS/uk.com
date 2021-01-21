package nts.uk.ctx.at.request.app.command.application.lateleaveearly;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.request.app.find.application.ApplicationDto;
import nts.uk.ctx.at.request.dom.application.AppReason;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.ApplicationDate;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.PrePostAtr;
import nts.uk.ctx.at.request.dom.application.ReasonForReversion;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.ProcessResult;
import nts.uk.ctx.at.request.dom.application.lateleaveearly.LateLeaveEarlyService;
import nts.uk.ctx.at.request.dom.application.stamp.StampRequestMode;
import nts.uk.ctx.at.request.dom.setting.company.appreasonstandard.AppStandardReasonCode;

/**
 * @author anhnm
 *
 */
@Stateless
public class LateLeaveEarlyCommandHandler extends CommandHandlerWithResult<LateLeaveEarlyCommand, ProcessResult> {

	@Inject
	private LateLeaveEarlyService service;

	@Override
	protected ProcessResult handle(CommandHandlerContext<LateLeaveEarlyCommand> context) {
		LateLeaveEarlyCommand dto = context.getCommand();
		ApplicationDto applicationDto = dto.getApplication();
		applicationDto.setInputDate(GeneralDateTime.now().toString("yyyy/MM/dd HH:mm:ss"));
		Application application = Application.createFromNew(
				EnumAdaptor.valueOf(dto.getApplication().getPrePostAtr(), PrePostAtr.class),
				dto.getApplication().getEmployeeID(), EnumAdaptor.valueOf(dto.getAppType(), ApplicationType.class),
				new ApplicationDate(GeneralDate.fromString(dto.getApplication().getAppDate(), "yyyy/MM/dd")),
				applicationDto.getEmployeeID(),
				dto.getApplication().getOpStampRequestMode() == null ? Optional.empty()
						: Optional.of(EnumAdaptor.valueOf(dto.getApplication().getOpStampRequestMode(),
								StampRequestMode.class)),
				Optional.of(new ReasonForReversion(dto.getApplication().getOpReversionReason())),
				dto.getApplication().getOpAppStartDate() == null ? Optional.empty()
						: Optional.of(new ApplicationDate(
								GeneralDate.fromString(dto.getApplication().getOpAppStartDate(), "yyyy/MM/dd"))),
				dto.getApplication().getOpAppEndDate() == null ? Optional.empty()
						: Optional.of(new ApplicationDate(
								GeneralDate.fromString(dto.getApplication().getOpAppEndDate(), "yyyy/MM/dd"))),
				Optional.of(new AppReason(dto.getApplication().getOpAppReason())),
				dto.getApplication().getOpAppStandardReasonCD() == null ? Optional.empty() : Optional.of(new AppStandardReasonCode(dto.getApplication().getOpAppStandardReasonCD())));

		return this.service.register(dto.getAppType(), dto.getInfoOutput().toDomain(), application);
	}

}
