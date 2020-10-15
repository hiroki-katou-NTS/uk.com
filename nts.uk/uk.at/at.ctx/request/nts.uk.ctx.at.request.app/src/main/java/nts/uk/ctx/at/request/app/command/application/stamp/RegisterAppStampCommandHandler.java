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
import nts.uk.ctx.at.request.dom.application.stamp.AppRecordImage;
import nts.uk.ctx.at.request.dom.application.stamp.AppStamp;
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
		String pattern2 = "yyyy/MM/dd";
		AppStampDto appStampDto = param.getAppStampDto();
		AppRecordImageDto appRecordImageDto = param.getAppRecordImageDto();
		ApplicationDto applicationDto = param.getApplicationDto();
		Application application = Application.createFromNew(
				EnumAdaptor.valueOf(applicationDto.getPrePostAtr(), PrePostAtr.class),
				applicationDto.getEmployeeID(),
				EnumAdaptor.valueOf(applicationDto.getAppType(), ApplicationType.class),
				new ApplicationDate(GeneralDate.fromString(applicationDto.getAppDate(), pattern2)),
				applicationDto.getEnteredPerson(),
				applicationDto.getOpStampRequestMode() == null ? Optional.empty() : Optional.of(EnumAdaptor.valueOf(applicationDto.getOpStampRequestMode(), StampRequestMode.class)),
				applicationDto.getOpReversionReason() == null ? Optional.empty() : Optional.of(new ReasonForReversion(applicationDto.getOpReversionReason())),
				StringUtils.isBlank(applicationDto.getOpAppStartDate()) ? Optional.empty() : Optional.of(new ApplicationDate(GeneralDate.fromString(applicationDto.getOpAppStartDate(), pattern2))),
				StringUtils.isBlank(applicationDto.getOpAppEndDate()) ? Optional.empty() : Optional.of(new ApplicationDate(GeneralDate.fromString(applicationDto.getOpAppEndDate(), pattern2))),
				applicationDto.getOpAppReason() == null ? Optional.empty() : Optional.of(new AppReason(applicationDto.getOpAppReason())),
				applicationDto.getOpAppStandardReasonCD() == null ? Optional.empty() : Optional.of(new AppStandardReasonCode(applicationDto.getOpAppStandardReasonCD())));
		AppStamp as = null;
		if (appStampDto != null) {
			as = appStampDto.toDomain();
			as.setAppID(application.getAppID());
			application.setOpStampRequestMode(Optional.ofNullable(StampRequestMode.STAMP_ADDITIONAL));
		}
		AppRecordImage ar = null;
		if (appRecordImageDto != null) {
//			appRecordImageDto.setAppID(application.getAppID());
			ar = appRecordImageDto.toDomain();
			ar.setAppID(application.getAppID());
			application.setOpStampRequestMode(Optional.ofNullable(StampRequestMode.STAMP_ONLINE_RECORD));
		}
		return appCommonDomainServiceRegister.registerAppStamp(
				application,
				Optional.ofNullable(as),
				Optional.ofNullable(ar),
				param.getAppStampOutputDto().toDomain(),
				param.getRecoderFlag());
	}

}
