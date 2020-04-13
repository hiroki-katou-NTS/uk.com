package nts.uk.ctx.at.request.app.command.application.common;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.setting.employment.appemploymentsetting.AppEmploymentSetting;
import nts.uk.ctx.at.request.dom.setting.employment.appemploymentsetting.AppEmploymentSettingRepository;
import nts.uk.ctx.at.request.dom.setting.employment.appemploymentsetting.WorkTypeObjAppHoliday;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@Transactional
public class AddAppEmploymentSetCommandHandler extends CommandHandler<List<AppEmploymentSetCommand>> {

	@Inject
	AppEmploymentSettingRepository employmentSetting;

	@Override
	protected void handle(CommandHandlerContext<List<AppEmploymentSetCommand>> context) {
		// 会社ID
		String companyId = AppContexts.user().companyId();
		List<AppEmploymentSetCommand> listEmployments = context.getCommand();
		AppEmploymentSetting insertData = new AppEmploymentSetting(companyId, !CollectionUtil.isEmpty(listEmployments) ? listEmployments.get(0).getEmploymentCode(): "", 
				listEmployments.stream().map(x-> { 
					return new WorkTypeObjAppHoliday(!CollectionUtil.isEmpty(x.getLstWorkType()) ? x.getLstWorkType().stream().map(y -> y.getWorkTypeCode()).collect(Collectors.toList()) : null, 
							EnumAdaptor.valueOf(x.getAppType(), ApplicationType.class),
							x.isDisplayFlag(),
							x.getAppType() == 1 ? x.getHolidayOrPauseType() : null,
							x.getHolidayTypeUseFlg() == null ? false : x.getHolidayTypeUseFlg(),
							x.getAppType() == 10 ? x.getHolidayOrPauseType(): null);
						}).collect(Collectors.toList())
				);	
		employmentSetting.insert(insertData);
	}

}
