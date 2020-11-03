package nts.uk.ctx.at.function.app.find.alarm;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.function.app.find.alarm.extractionrange.*;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.CheckCondition;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.ExtractionRangeBase;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.daily.ExtractionPeriodDaily;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.month.ExtractionPeriodMonth;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.month.mutilmonth.AverageMonth;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.periodunit.ExtractionPeriodUnit;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.year.AYear;

import java.util.ArrayList;
import java.util.List;

/**
 * The class Check condition dto.
 */
@Data
@AllArgsConstructor
public class CheckConditionDto {

	/**
	 * The Alarm category.
	 */
	private int alarmCategory;

	/**
	 * The Check condition codes.
	 */
	private List<String> checkConditionCodes;

	/**
	 * The Extraction daily.
	 */
	private ExtractionPeriodDailyDto extractionDaily;

	/**
	 * The Extraction unit.
	 */
	private ExtractionPeriodUnitDto extractionUnit;

	/**
	 * The List extraction monthly.
	 */
	private List<ExtractionPeriodMonthlyDto> listExtractionMonthly;

	/**
	 * The Extraction year.
	 */
	private ExtractionRangeYearDto extractionYear;

	/**
	 * The Extraction aver month.
	 */
	private ExtractionAverMonthDto extractionAverMonth;

	/**
	 * Creates from domain.
	 *
	 * @param domain the domain
	 * @return the Check condition dto
	 */
	public static CheckConditionDto createFromDomain(CheckCondition domain) {
		if (domain == null) {
			return null;
		}
		ExtractionPeriodDailyDto extractionPeriodDailyDto = null;
		ExtractionPeriodUnitDto extractionUnit = null;
		List<ExtractionPeriodMonthlyDto> extractionMonthlyList = new ArrayList<>();
		ExtractionRangeYearDto extractionYear = null;
		ExtractionAverMonthDto extractionAverMonth = null;

		if (!domain.getExtractPeriodList().isEmpty()) {
			ExtractionRangeBase extractBase = domain.getExtractPeriodList().get(0);
			if (domain.isDaily() || domain.isManHourCheck()) {
//			ExtractionRangeBase extractBase = domain.getExtractPeriodList().get(0);
				extractionPeriodDailyDto = ExtractionPeriodDailyDto.createFromDomain((ExtractionPeriodDaily) extractBase);
			} else if (domain.isMonthly() || domain.isMultipleMonth()) {
//			ExtractionRangeBase extractBase = domain.getExtractPeriodList().get(0);
				extractionMonthlyList.add(ExtractionPeriodMonthlyDto.createFromDomain((ExtractionPeriodMonth) extractBase));
			} else if (domain.is4W4D()) {
//			ExtractionRangeBase extractBase = domain.getExtractPeriodList().get(0);
				extractionUnit = ExtractionPeriodUnitDto.createFromDomain((ExtractionPeriodUnit) extractBase);
			} else if (domain.isAgrrement()) {
				for (ExtractionRangeBase base : domain.getExtractPeriodList()) {
					if (base instanceof ExtractionPeriodDaily) {
						extractionPeriodDailyDto = ExtractionPeriodDailyDto.createFromDomain((ExtractionPeriodDaily) base);
					} else if (base instanceof ExtractionPeriodMonth) {
						extractionMonthlyList.add(ExtractionPeriodMonthlyDto.createFromDomain((ExtractionPeriodMonth) base));
					} else if (base instanceof AYear) {
						extractionYear = ExtractionRangeYearDto.createFromDomain((AYear) base);
					} else if (base instanceof AverageMonth) {
						extractionAverMonth = ExtractionAverMonthDto.createFromDomain((AverageMonth) base);
					}
				}
			}
		}

		return new CheckConditionDto(domain.getAlarmCategory().value,
				domain.getCheckConditionList(),
				extractionPeriodDailyDto,
				extractionUnit,
				extractionMonthlyList,
				extractionYear,
				extractionAverMonth);
	}

}
