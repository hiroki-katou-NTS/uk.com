package nts.uk.ctx.at.function.app.command.alarm;

import java.util.List;
import java.util.stream.Collectors;
import javax.ejb.Stateless;
import javax.inject.Inject;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.function.dom.alarm.AlarmCategory;
import nts.uk.ctx.at.function.dom.alarm.AlarmPatternSetting;
import nts.uk.ctx.at.function.dom.alarm.AlarmPatternSettingRepository;
import nts.uk.ctx.at.function.dom.alarm.AlarmPermissionSetting;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.CheckCondition;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.ExtractionRangeBase;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class UpdateAlarmPatternSettingCommandHandler extends CommandHandler<AddAlarmPatternSettingCommand> {

	@Inject
	private AlarmPatternSettingRepository repo;

	@Override
	protected void handle(CommandHandlerContext<AddAlarmPatternSettingCommand> context) {

		AddAlarmPatternSettingCommand c = context.getCommand();
		String companyId = AppContexts.user().companyId();

		// find domain
		AlarmPatternSetting domain = repo.findByAlarmPatternCode(companyId, c.getAlarmPatternCD()).get();

		AlarmPermissionSetting alarmPerSet = new AlarmPermissionSetting(c.getAlarmPatternCD(), companyId,
				c.getAlarmPerSet().isAuthSetting(), c.getAlarmPerSet().getRoleIds());

		List<CheckCondition> checkConList = c.getCheckConditonList().stream()
				.map(x -> new CheckCondition(c.getAlarmPatternCD(), companyId,
						EnumAdaptor.valueOf(x.getAlarmCategory(), AlarmCategory.class), x.getCheckConditionCodes(),
						(ExtractionRangeBase) x.getExtractionPeriodDaily().toDomain()))
				.collect(Collectors.toList());
		
		// set update property  
		domain.setAlarmPerSet(alarmPerSet);
		domain.setCheckConList(checkConList);
		domain.setAlarmPatternName(c.getAlarmPatterName());
		
		// check domain logic
		if (domain.selectedCheckCodition()) {
			repo.update(domain);
		}

	}
}
