package nts.uk.ctx.at.request.app.command.application.stamp;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.app.find.application.ApplicationDto;
import nts.uk.ctx.at.request.app.find.application.stamp.dto.AppRecordImageDto;
import nts.uk.ctx.at.request.app.find.application.stamp.dto.AppStampDto;
import nts.uk.ctx.at.request.dom.application.AppReason;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.ApplicationDate;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.PrePostAtr;
import nts.uk.ctx.at.request.dom.application.ReasonForReversion;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.ProcessResult;
import nts.uk.ctx.at.request.dom.application.stamp.AppCommonDomainServiceRegister;
import nts.uk.ctx.at.request.dom.application.stamp.StampRequestMode;
import nts.uk.ctx.at.request.dom.setting.company.appreasonstandard.AppStandardReasonCode;
@Transactional
@Stateless
public class RegisterAppStampCommandHandler extends CommandHandlerWithResult<RegisterOrUpdateAppStampParam, ProcessResult>{
	@Inject
	private AppCommonDomainServiceRegister appCommonDomainServiceRegister;
	
	public static final String DATE_FORMAT = "yyyy/MM/dd";
	@Override
	protected ProcessResult handle(CommandHandlerContext<RegisterOrUpdateAppStampParam> context) {
		RegisterOrUpdateAppStampParam param = context.getCommand();
		ApplicationDto applicationDto = param.getApplicationDto();
		AppStampDto appStampDto = param.getAppStampDto();
		AppRecordImageDto appRecordImageDto = param.getAppRecordImageDto();
		Application application = Application.createFromNew(
				EnumAdaptor.valueOf(applicationDto.getPrePostAtr(), PrePostAtr.class), applicationDto.getEmployeeID(),
				ApplicationType.STAMP_APPLICATION,
				new ApplicationDate(GeneralDate.fromString(applicationDto.getAppDate(), DATE_FORMAT)),
				applicationDto.getEnteredPerson(),
				applicationDto.getOpStampRequestMode() != null
						? Optional
								.of(EnumAdaptor.valueOf(applicationDto.getOpStampRequestMode(), StampRequestMode.class))
						: Optional.empty(),

				!StringUtils.isBlank(applicationDto.getOpReversionReason())
						? Optional.of(new ReasonForReversion(applicationDto.getOpReversionReason()))
						: Optional.empty(),

				!StringUtils.isBlank(applicationDto.getOpAppStartDate())
						? Optional.of(new ApplicationDate(
								GeneralDate.fromString(applicationDto.getOpAppStartDate(), DATE_FORMAT)))
						: Optional.empty(),

				!StringUtils.isBlank(applicationDto.getOpAppEndDate())
						? Optional.of(new ApplicationDate(
								GeneralDate.fromString(applicationDto.getOpAppEndDate(), DATE_FORMAT)))
						: Optional.empty(),

				!StringUtils.isBlank(applicationDto.getOpAppReason())
						? Optional.of(new AppReason(applicationDto.getOpAppReason()))
						: Optional.empty(),

				applicationDto.getOpAppStandardReasonCD() != null
						? Optional.of(new AppStandardReasonCode(applicationDto.getOpAppStandardReasonCD()))
						: Optional.empty());
		appStampDto.setAppID(application.getAppID());
		appRecordImageDto.setAppID(application.getAppID());
		return appCommonDomainServiceRegister.registerAppStamp(
				application,
				appStampDto != null ? Optional.of(appStampDto.toDomain()) : Optional.empty(),
						appRecordImageDto != null ? Optional.of(appRecordImageDto.toDomain()): Optional.empty(),
				param.getAppStampOutputDto().toDomain(),
				param.getRecoderFlag());
	}

}
