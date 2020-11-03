package nts.uk.ctx.at.function.app.command.alarm;

import lombok.Data;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.function.app.command.alarm.extractionrange.*;
import nts.uk.ctx.at.function.dom.alarm.AlarmCategory;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.AlarmCheckConditionCode;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.CheckCondition;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.ExtractionRangeBase;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.month.mutilmonth.AverageMonth;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.year.AYear;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * The class Check condition command.<br>
 * Command チェック条件
 */
@Data
public class CheckConditionCommand {

	/**
	 * The Alarm category.
	 */
	private int alarmCategory;

	/**
	 * The Check condition code list.
	 */
	private List<String> checkConditionCodeList;

	/**
	 * The Extraction period daily.
	 */
	private ExtractionPeriodDailyCommand extractionPeriodDaily;

	/**
	 * The Extraction period unit.
	 */
	private ExtractionPeriodUnitCommand extractionPeriodUnit;

	/**
	 * The Extraction monthly list.
	 */
	private List<ExtractionPeriodMonthlyCommand> extractionMonthlyList;

	/**
	 * The Extraction year.
	 */
	private ExtractionRangeYearCommand extractionYear;

	/**
	 * The Extraction aver month.
	 */
	private ExtractionAverageMonthCommand extractionAverMonth;

	/**
	 * To domain.
	 *
	 * @param command the command
	 * @return the domain check condition
	 */
	public static CheckCondition toDomain(CheckConditionCommand command) {
		if (command == null) {
			return null;
		}
		List<ExtractionRangeBase> extractionList = new ArrayList<>();
		if (command.getAlarmCategory() == AlarmCategory.DAILY.value
				|| command.getAlarmCategory() == AlarmCategory.MAN_HOUR_CHECK.value) {
			extractionList.add(command.getExtractionPeriodDaily().toDomain());
		} else if (command.getAlarmCategory() == AlarmCategory.SCHEDULE_4WEEK.value) {
			extractionList.add(command.getExtractionPeriodUnit().toDomain());
		} else if (command.getAlarmCategory() == AlarmCategory.MONTHLY.value
				|| command.getAlarmCategory() == AlarmCategory.MULTIPLE_MONTH.value) {
			command.getExtractionMonthlyList().forEach(e -> extractionList.add(e.toDomain()));
		} else if (command.getAlarmCategory() == AlarmCategory.AGREEMENT.value) {
			extractionList.add(command.getExtractionPeriodDaily().toDomain());
			command.getExtractionMonthlyList().forEach(e -> extractionList.add(e.toDomain()));
			AYear extractYear = command.getExtractionYear().toDomain();
			extractionList.add(extractYear);
			extractionList.forEach(e -> {
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
		AlarmCategory alarmCategory = EnumAdaptor.valueOf(command.getAlarmCategory(), AlarmCategory.class);
		List<AlarmCheckConditionCode> checkConditionList = command.getCheckConditionCodeList()
																  .stream()
																  .map(AlarmCheckConditionCode::new)
																  .collect(Collectors.toList());
		return new CheckCondition(alarmCategory,
								  checkConditionList,
								  extractionList,
								  Optional.empty());
	}

}
