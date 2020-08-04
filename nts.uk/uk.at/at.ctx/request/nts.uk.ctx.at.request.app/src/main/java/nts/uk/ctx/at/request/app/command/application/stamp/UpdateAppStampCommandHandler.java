package nts.uk.ctx.at.request.app.command.application.stamp;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.uk.ctx.at.request.app.find.application.ApplicationDto;
import nts.uk.ctx.at.request.app.find.application.stamp.dto.AppRecordImageDto;
import nts.uk.ctx.at.request.app.find.application.stamp.dto.AppStampDto;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.ProcessResult;
import nts.uk.ctx.at.request.dom.application.stamp.AppCommonDomainServiceRegister;
@Transactional
@Stateless
public class UpdateAppStampCommandHandler extends CommandHandlerWithResult<RegisterOrUpdateAppStampParam, ProcessResult>{
	
	@Inject
	private AppCommonDomainServiceRegister appCommonDomainServiceRegister;
	
	public static final String DATE_FORMAT = "yyyy/MM/dd";
	@Override
	protected ProcessResult handle(CommandHandlerContext<RegisterOrUpdateAppStampParam> context) {
		RegisterOrUpdateAppStampParam param = context.getCommand();
		ApplicationDto applicationDto = param.getApplicationDto();
		AppStampDto appStampDto = param.getAppStampDto();
		AppRecordImageDto appRecordImageDto = param.getAppRecordImageDto();
		appStampDto.setAppID(applicationDto.getAppID());
		appRecordImageDto.setAppID(applicationDto.getAppID());
		return appCommonDomainServiceRegister.updateAppStamp(
				applicationDto.toDomain(),
				appStampDto != null ? Optional.of(appStampDto.toDomain()) : Optional.empty(),
				appRecordImageDto != null ? Optional.of(appRecordImageDto.toDomain()) : Optional.empty(),
				param.getRecoderFlag());
	}
	

}
