package nts.uk.ctx.at.request.app.command.application.common;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.setting.employment.appemploymentsetting.AppEmployWorkType;
import nts.uk.ctx.at.request.dom.setting.employment.appemploymentsetting.AppEmploymentSetting;
import nts.uk.ctx.at.request.dom.setting.employment.appemploymentsetting.AppEmploymentSettingRepository;
import nts.uk.shr.com.context.AppContexts;
@Stateless
@Transactional
public class UpdateAppEmploymentSetCommandHandler extends CommandHandler<List<AppEmploymentSetCommand>>{
	@Inject
	AppEmploymentSettingRepository employmentSetting;
	@Override
	protected void handle(CommandHandlerContext<List<AppEmploymentSetCommand>> context) {
		// 会社ID
		String companyId = AppContexts.user().companyId();
		List<AppEmploymentSetCommand> listEmployments = context.getCommand();
		List<AppEmploymentSetting> insertDatas = listEmployments.stream().map(item -> {
			return new AppEmploymentSetting(companyId, item.getEmploymentCode(),
					EnumAdaptor.valueOf(item.getAppType(), ApplicationType.class), item.getHolidayOrPauseType(),
					item.getHolidayTypeUseFlg(), item.isDisplayFlag(), item.getLstWorkType().stream().map(typeItem -> {
						return AppEmployWorkType.createSimpleFromJavaType(companyId, typeItem.getEmploymentCode(),
								typeItem.getAppType(), typeItem.getHolidayOrPauseType(), typeItem.getWorkTypeCode());
					}).collect(Collectors.toList()));
		}).collect(Collectors.toList());

		employmentSetting.update(insertDatas);
	}

}
