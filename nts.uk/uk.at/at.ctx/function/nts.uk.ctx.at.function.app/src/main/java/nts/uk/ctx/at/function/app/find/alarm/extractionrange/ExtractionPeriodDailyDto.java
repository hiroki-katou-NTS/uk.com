package nts.uk.ctx.at.function.app.find.alarm.extractionrange;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.daily.ExtractionPeriodDaily;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.daily.Month;

/**
 * The class Extraction period daily dto.
 */
@Data
@AllArgsConstructor
public class ExtractionPeriodDailyDto {

	/**
	 * The Extraction id.
	 */
	private String extractionId;

	/**
	 * The Extraction range.
	 */
	private int extractionRange;

	/**
	 * The Start specify.
	 */
	private int strSpecify;

	/**
	 * The Start previous day.
	 */
	private Integer strPreviousDay;

	/**
	 * The Start make to day.
	 */
	private Integer strMakeToDay;

	/**
	 * The Start day.
	 */
	private Integer strDay;

	/**
	 * The Start previous month.
	 */
	private Integer strPreviousMonth;

	/**
	 * The Start current month.
	 */
	private Integer strCurrentMonth;

	/**
	 * The Start month.
	 */
	private Integer strMonth;

	/**
	 * The End specify.
	 */
	private int endSpecify;

	/**
	 * The End previous day.
	 */
	private Integer endPreviousDay;

	/**
	 * The End make to day.
	 */
	private Integer endMakeToDay;

	/**
	 * The End day.
	 */
	private Integer endDay;

	/**
	 * The End previous month.
	 */
	private Integer endPreviousMonth;

	/**
	 * The End current month.
	 */
	private Integer endCurrentMonth;

	/**
	 * The End month.
	 */
	private Integer endMonth;

	/**
	 * No args constructor.
	 */
	private ExtractionPeriodDailyDto() {
	}

	/**
	 * Creates from domain.
	 *
	 * @param domain the domain
	 * @return the Extraction period daily dto
	 */
	public static ExtractionPeriodDailyDto createFromDomain(ExtractionPeriodDaily domain) {
		if (domain == null) {
			return null;
		}
		ExtractionPeriodDailyDto dto = new ExtractionPeriodDailyDto();
		dto.setExtractionId(domain.getExtractionId());
		dto.setExtractionRange(domain.getExtractionRange().value);

		// Set start date
		dto.setStrSpecify(domain.getStartDate().getStartSpecify().value);
		switch (domain.getStartDate().getStartSpecify()) {
			case DAYS:
				dto.setStrPreviousDay(domain.getStartDate().getStartDays()
														   .map(days -> days.getDayPrevious().value)
														   .orElse(null));
				dto.setStrMakeToDay(domain.getStartDate().getStartDays()
														 .map(days -> days.isToday() ? 1 : 0)
														 .orElse(null));
				dto.setStrDay(domain.getStartDate().getStartDays()
												   .map(days -> days.getDay().v())
												   .orElse(null));
				break;
			case MONTH:
				dto.setStrPreviousMonth(domain.getStartDate().getStartMonth()
															 .map(month -> month.getMonthPrevious().value)
															 .orElse(null));
				dto.setStrCurrentMonth(domain.getStartDate().getStartMonth()
															.map(month -> month.isCurrentMonth() ? 1 : 0)
															.orElse(null));
				dto.setStrMonth(domain.getStartDate().getStartMonth()
													 .map(Month::getMonth)
													 .orElse(null));
				break;
		}

		// Set end date
		dto.setEndSpecify(domain.getEndDate().getEndSpecify().value);
		switch (domain.getEndDate().getEndSpecify()) {
			case DAYS:
				dto.setEndPreviousDay(domain.getEndDate().getEndDays()
														 .map(days -> days.getDayPrevious().value)
														 .orElse(null));
				dto.setEndMakeToDay(domain.getEndDate().getEndDays()
													   .map(days -> days.isToday() ? 1 : 0)
													   .orElse(null));
				dto.setEndDay(domain.getEndDate().getEndDays()
												 .map(days -> days.getDay().v())
												 .orElse(null));
				break;
			case MONTH:
				dto.setEndPreviousMonth(domain.getEndDate().getEndMonth()
														   .map(month -> month.getMonthPrevious().value)
														   .orElse(null));
				dto.setEndCurrentMonth(domain.getEndDate().getEndMonth()
														  .map(month -> month.isCurrentMonth() ? 1 : 0)
														  .orElse(null));
				dto.setEndMonth(domain.getEndDate().getEndMonth()
												   .map(Month::getMonth)
												   .orElse(null));
				break;
		}

		return dto;
	}

}
