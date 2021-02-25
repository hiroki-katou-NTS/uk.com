package nts.uk.ctx.at.function.app.command.alarm;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
import nts.uk.ctx.at.function.dom.alarm.extractionrange.ExtractionRange;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.ExtractionRangeBase;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.month.ExtractionPeriodMonth;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.month.mutilmonth.AverageMonth;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.year.AYear;
import nts.uk.shr.com.context.AppContexts;

/**
 * Class UpdateAlarmPatternSettingCommandHandler
 * @author phongtq
 *
 */

@Stateless
public class UpdateAlarmPatternSettingCommandHandler extends CommandHandler<AddAlarmPatternSettingCommand> {

	@Inject
	private AlarmPatternSettingRepository repo;

	@Inject
	private CreateAverage createAverage;

	@Override
	protected void handle(CommandHandlerContext<AddAlarmPatternSettingCommand> context) {

		AddAlarmPatternSettingCommand c = context.getCommand();
		String companyId = AppContexts.user().companyId();

//		List<CheckCondition> listWorkTypeCode = checkConList.stream().filter(x-> x.getAlarmCategory().equals(AlarmCategory.AGREEMENT)).collect(Collectors.toList());
//		List<ExtractionRangeBase> baseTemp = listWorkTypeCode.stream().flatMap(x -> x.getExtractPeriodList().stream()).filter(x -> (x instanceof AverageMonth)).collect(Collectors.toList());
//		ExtractionRangeBase month = baseTemp.get(0);


		// find domain
		AlarmPatternSetting domain = repo.findByAlarmPatternCode(companyId, c.getAlarmPatternCD()).get();
		List<CheckCondition> listWorkTypeCode2 = domain.getCheckConList().stream().filter(x-> x.getAlarmCategory().equals(AlarmCategory.AGREEMENT)).collect(Collectors.toList());
		List<ExtractionRangeBase> baseTemp2 = listWorkTypeCode2.stream().flatMap(x -> x.getExtractPeriodList().stream()).filter(x -> (x instanceof ExtractionPeriodMonth)).collect(Collectors.toList());
		if(!baseTemp2.isEmpty()){
			ExtractionRangeBase month21 = baseTemp2.get(0);

			Optional<AverageMonth> monthFind = repo.findAverageMonth(month21.getExtractionId());
			if(!monthFind.isPresent()){
				AverageMonth month2 = new AverageMonth(month21.getExtractionId(), 4, 0);
				createAverage.createAver(month2);
			}
		}

		AlarmPermissionSetting alarmPerSet = new AlarmPermissionSetting(c.getAlarmPatternCD(), companyId,
				c.getAlarmPerSet().isAuthSetting(), c.getAlarmPerSet().getRoleIds());

		List<CheckCondition> checkConList = c.getCheckConditonList().stream()
				.map(x ->convertToCheckCondition(x, c.getAlarmPatternCD()))
				.collect(Collectors.toList());
		// set update property
		domain.setAlarmPerSet(alarmPerSet);
		domain.setCheckConList(checkConList);
		domain.setAlarmPatternName(c.getAlarmPatterName());

		//AverageMonth
		// check domain logic
		if (domain.selectedCheckCodition()) {
			// アラームリストのパターンを更新する (Update pattern of alarm list )

			repo.update(domain);
		}

	}

	public CheckCondition convertToCheckCondition (CheckConditionCommand command, String code) {
		String companyId = AppContexts.user().companyId();
		List<ExtractionRangeBase> extractionList = new ArrayList<>();
		if (command.getAlarmCategory() == AlarmCategory.DAILY.value
				|| command.getAlarmCategory() == AlarmCategory.MAN_HOUR_CHECK.value) {

			extractionList.add(command.getExtractionPeriodDaily().toDomain());

		} else if (command.getAlarmCategory() == AlarmCategory.SCHEDULE_4WEEK.value) {

			extractionList.add(command.getExtractionPeriodUnit().toDomain());

		} else if (command.getAlarmCategory() == AlarmCategory.MONTHLY.value ||
				command.getAlarmCategory() == AlarmCategory.MULTIPLE_MONTH.value ) {

			command.getListExtractionMonthly().forEach(e -> {
				extractionList.add(e.toDomain());
			});
		} else if(command.getAlarmCategory() == AlarmCategory.AGREEMENT.value) {

			extractionList.add(command.getExtractionPeriodDaily().toDomain());
			command.getListExtractionMonthly().forEach(e -> {
				extractionList.add(e.toDomain());
			});

			Optional<AlarmPatternSetting> dataExtra =  repo.findByAlarmPatternCode(companyId, code);
			AYear extractYear = command.getExtractionYear().toDomain();
			extractYear.setExtractionRange(ExtractionRange.YEAR);
			extractionList.add(extractYear);

			// Add averageMonth to extractionList
			AverageMonth averageMonth = command.getExtractionAverMonth().toDomain();
			averageMonth.setExtractionRange(ExtractionRange.DES_STANDARD_MONTH);
			extractionList.add(averageMonth);

			// Set ExtractionId & ExtractionRange
			extractionList.forEach( e-> {
				e.setExtractionId(dataExtra.get().getCheckConList().get(0).getExtractPeriodList().get(0).getExtractionId());
				e.setExtractionRange(averageMonth.getExtractionRange());
			});
		}

		return new CheckCondition(EnumAdaptor.valueOf(command.getAlarmCategory(), AlarmCategory.class), command.getCheckConditionCodes(), extractionList);
	}

}
