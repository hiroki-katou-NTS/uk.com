package nts.uk.ctx.at.request.app.command.application.common;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import org.eclipse.persistence.internal.xr.CollectionResult;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.setting.employment.appemploymentsetting.AppEmployWorkType;
import nts.uk.ctx.at.request.dom.setting.employment.appemploymentsetting.AppEmploymentSetting;
import nts.uk.ctx.at.request.dom.setting.employment.appemploymentsetting.AppEmploymentSettingRepository;
import nts.uk.ctx.at.request.dom.setting.employment.appemploymentsetting.AppEmploymentSetting;
import nts.uk.ctx.at.request.dom.setting.employment.appemploymentsetting.WorkTypeObjAppHoliday;
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
//		List<AppEmploymentSetting> insertDatas = listEmployments.stream().map(item -> {
//			return new AppEmploymentSetting(companyId, item.getEmploymentCode(),
//					EnumAdaptor.valueOf(item.getAppType(), ApplicationType.class), item.getHolidayOrPauseType(),
//					item.getHolidayTypeUseFlg(), item.isDisplayFlag(), item.getLstWorkType().stream().map(typeItem -> {
//						return AppEmployWorkType.createSimpleFromJavaType(companyId, typeItem.getEmploymentCode(),
//								typeItem.getAppType(), typeItem.getHolidayOrPauseType(), typeItem.getWorkTypeCode());
//					}).collect(Collectors.toList()));
//		}).collect(Collectors.toList());
		AppEmploymentSetting insertData = new AppEmploymentSetting(companyId, !CollectionUtil.isEmpty(listEmployments) ? listEmployments.get(0).getEmploymentCode(): "", 
				listEmployments.stream().map(x-> { 
					return new WorkTypeObjAppHoliday(!CollectionUtil.isEmpty(x.getLstWorkType()) ? x.getLstWorkType().stream().map(y -> y.getWorkTypeCode()).collect(Collectors.toList()) : null, 
							EnumAdaptor.valueOf(x.getAppType(), ApplicationType.class),
							x.isDisplayFlag(),
							x.getAppType() == 1 ? x.getHolidayOrPauseType() : null,
							x.getHolidayTypeUseFlg(),
							x.getAppType() == 10 ? x.getHolidayOrPauseType(): null);
						}).collect(Collectors.toList())
				);
		employmentSetting.update(insertData);
//		employmentSetting.update(insertDatas);
	}

}
