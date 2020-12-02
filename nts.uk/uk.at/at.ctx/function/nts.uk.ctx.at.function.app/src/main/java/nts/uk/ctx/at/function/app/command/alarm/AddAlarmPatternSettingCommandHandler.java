package nts.uk.ctx.at.function.app.command.alarm;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.function.dom.alarm.AlarmCategory;
import nts.uk.ctx.at.function.dom.alarm.AlarmPatternSetting;
import nts.uk.ctx.at.function.dom.alarm.AlarmPatternSettingRepository;
import nts.uk.ctx.at.function.dom.alarm.AlarmPatternSettingService;
import nts.uk.ctx.at.function.dom.alarm.AlarmPermissionSetting;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.CheckCondition;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.ExtractionRangeBase;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.month.mutilmonth.AverageMonth;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.year.AYear;
import nts.uk.shr.com.context.AppContexts;

/**
 * Class AddAlarmPatternSettingCommandHandler
 * @author phongtq
 *
 */

@Stateless
public class AddAlarmPatternSettingCommandHandler extends CommandHandler<AddAlarmPatternSettingCommand> {

	@Inject
	private AlarmPatternSettingRepository repo;

	@Inject
	private AlarmPatternSettingService domainService;

	@Override
	protected void handle(CommandHandlerContext<AddAlarmPatternSettingCommand> context) {
		
		AddAlarmPatternSettingCommand c = context.getCommand();
		String companyId = AppContexts.user().companyId();
		
		// check duplicate code
		if (!domainService.checkDuplicateCode(c.getAlarmPatternCD())) {
			
			AlarmPermissionSetting alarmPerSet = new AlarmPermissionSetting(c.getAlarmPatternCD(), companyId,
					c.getAlarmPerSet().isAuthSetting(), c.getAlarmPerSet().getRoleIds());

			List<CheckCondition> checkConList = c.getCheckConditonList().stream()
					.map(x -> convertToCheckCondition(x))
					.collect(Collectors.toList());
			
			// create domain
			AlarmPatternSetting domain = new AlarmPatternSetting(checkConList, c.getAlarmPatternCD(), companyId,
					alarmPerSet, c.getAlarmPatterName());
			
			// check domain logic
			if (domain.selectedCheckCodition()) {
				// アラームリストのパターンを新規登録する (Add pattern of alarm list )
				repo.create(domain);
			}
			
		}else{
			throw new BusinessException("Msg_3");
		}
	}
	
	public CheckCondition convertToCheckCondition (CheckConditionCommand command) {
		List<ExtractionRangeBase> extractionList = new ArrayList<>();
		if (command.getAlarmCategory() == AlarmCategory.DAILY.value
				|| command.getAlarmCategory() == AlarmCategory.MAN_HOUR_CHECK.value 
				|| command.getAlarmCategory() == AlarmCategory.APPLICATION_APPROVAL.value) {

			extractionList.add(command.getExtractionPeriodDaily().toDomain());

		} else if (command.getAlarmCategory() == AlarmCategory.SCHEDULE_4WEEK.value) {

			extractionList.add(command.getExtractionPeriodUnit().toDomain());

		} else if (command.getAlarmCategory() == AlarmCategory.MONTHLY.value ||
				command.getAlarmCategory() == AlarmCategory.MULTIPLE_MONTH.value) {

			command.getListExtractionMonthly().forEach(e -> {
				extractionList.add(e.toDomain());
			});
		} else if(command.getAlarmCategory() == AlarmCategory.AGREEMENT.value) {
			
			extractionList.add(command.getExtractionPeriodDaily().toDomain());
			command.getListExtractionMonthly().forEach(e -> {
				extractionList.add(e.toDomain());
			});
			AYear extractYear = command.getExtractionYear().toDomain();
			extractionList.add(extractYear);
			
			extractionList.forEach( e-> {
				e.setExtractionId(extractYear.getExtractionId());
				e.setExtractionRange(extractYear.getExtractionRange());
			});
			
			// Add averageMonth to extractionList
			AverageMonth averageMonth = command.getExtractionAverMonth().toDomain();
			extractionList.add(averageMonth);
			
			// Set ExtractionId & ExtractionRange
			extractionList.forEach(e -> {
				e.setExtractionId(averageMonth.getExtractionId());
				e.setExtractionRange(averageMonth.getExtractionRange());
			});
		}
		return new CheckCondition(EnumAdaptor.valueOf(command.getAlarmCategory(), AlarmCategory.class), command.getCheckConditionCodes(), extractionList);
	}
	
}
