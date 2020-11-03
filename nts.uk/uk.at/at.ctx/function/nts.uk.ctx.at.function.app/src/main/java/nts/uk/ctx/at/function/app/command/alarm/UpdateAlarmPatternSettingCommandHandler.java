package nts.uk.ctx.at.function.app.command.alarm;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.function.dom.alarm.AlarmCategory;
import nts.uk.ctx.at.function.dom.alarm.AlarmPatternSetting;
import nts.uk.ctx.at.function.dom.alarm.AlarmPatternSettingRepository;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.CheckCondition;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.ExtractionRangeBase;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.month.ExtractionPeriodMonth;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.month.mutilmonth.AverageMonth;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Class UpdateAlarmPatternSettingCommandHandler
 *
 * @author phongtq
 */
@Stateless
public class UpdateAlarmPatternSettingCommandHandler extends CommandHandler<AddAlarmPatternSettingCommand> {

	@Inject
	private AlarmPatternSettingRepository repo;

	@Inject
	private CreateAverage createAverage;

	@Override
	protected void handle(CommandHandlerContext<AddAlarmPatternSettingCommand> context) {

		AddAlarmPatternSettingCommand command = context.getCommand();
		String companyId = AppContexts.user().companyId();

//		List<CheckCondition> listWorkTypeCode = checkConList.stream().filter(x-> x.getAlarmCategory().equals(AlarmCategory.AGREEMENT)).collect(Collectors.toList());
//		List<ExtractionRangeBase> baseTemp = listWorkTypeCode.stream().flatMap(x -> x.getExtractPeriodList().stream()).filter(x -> (x instanceof AverageMonth)).collect(Collectors.toList());
//		ExtractionRangeBase month = baseTemp.get(0);

		// find domain
		AlarmPatternSetting domain = this.repo.findByAlarmPatternCode(companyId, command.getAlarmPatternCD()).get();
		List<CheckCondition> listWorkTypeCode2 = domain.getCheckConList().stream().filter(x -> x.getAlarmCategory().equals(AlarmCategory.AGREEMENT)).collect(Collectors.toList());
		List<ExtractionRangeBase> baseTemp2 = listWorkTypeCode2.stream().flatMap(x -> x.getExtractPeriodList().stream()).filter(x -> (x instanceof ExtractionPeriodMonth)).collect(Collectors.toList());
		if (!baseTemp2.isEmpty()) {
			ExtractionRangeBase month21 = baseTemp2.get(0);

			Optional<AverageMonth> monthFind = this.repo.findAverageMonth(month21.getExtractionId());
			if (!monthFind.isPresent()) {
				AverageMonth month2 = new AverageMonth(month21.getExtractionId(), 4, 0);
				this.createAverage.createAver(month2);
			}
		}

		// set update property  
		AlarmPatternSetting updateDomain = AlarmPatternSetting.createFromMemento(domain.getCompanyID(), command);

		//AverageMonth
		// check domain logic
		if (updateDomain.isCheckConListNotEmpty()) {
			// アラームリストのパターンを更新する (Update pattern of alarm list )
			this.repo.update(updateDomain);
		}
	}

}
