package nts.uk.ctx.at.function.app.find.alarm.extractionrange;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.PreviousClassification;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.month.ExtractionPeriodMonth;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.month.FixedMonthly;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.month.MonthNo;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.month.YearSpecifiedType;

/**
 * The class Extraction period monthly dto.<br>
 * Dto 抽出期間（月単位）
 */
@Data
@AllArgsConstructor
public class ExtractionPeriodMonthlyDto {

	/**
	 * The Extraction id.
	 */
	private String extractionId;

	/**
	 * The Extraction range.
	 */
	private int extractionRange;

	/**
	 * The Unit.
	 */
	private int unit;

	/**
	 * The Start specify.
	 */
	private int strSpecify;

	/**
	 * The Year type.
	 */
	private int yearType;

	/**
	 * The Specify month.
	 */
	private int specifyMonth;

	/**
	 * The Start month.
	 */
	private int strMonth;

	/**
	 * The Start current month.
	 */
	private Integer strCurrentMonth;

	/**
	 * The Start previous atr.
	 */
	private int strPreviousAtr;

	/**
	 * The End specify.
	 */
	private int endSpecify;

	/**
	 * The Extract period.
	 */
	private int extractPeriod;

	/**
	 * The End month.
	 */
	private int endMonth;

	/**
	 * The End current month.
	 */
	private Integer endCurrentMonth;

	/**
	 * The End previous atr.
	 */
	private int endPreviousAtr;

	/**
	 * No args constructor.
	 */
	private ExtractionPeriodMonthlyDto() {
	}

	/**
	 * Creates from domain.
	 *
	 * @param domain the domain
	 * @return the Extraction period monthly dto
	 */
	public static ExtractionPeriodMonthlyDto createFromDomain(ExtractionPeriodMonth domain) {
		if (domain == null) {
			return null;
		}
		ExtractionPeriodMonthlyDto dto = new ExtractionPeriodMonthlyDto();
		dto.setExtractionId(domain.getExtractionId());
		dto.setExtractionRange(domain.getExtractionRange().value);
		dto.setUnit(domain.getNumberOfMonth().value);
		dto.setStrSpecify(domain.getStartMonth().getSpecifyStartMonth().value);
		dto.setYearType(domain.getStartMonth().getFixedMonthly()
											  .map(fixedMonthly -> fixedMonthly.getYearSpecifiedType().value)
											  .orElse(YearSpecifiedType.CURRENT_YEAR.value));
		dto.setSpecifyMonth(domain.getStartMonth().getFixedMonthly()
												  .map(FixedMonthly::getDesignatedMonth)
												  .orElse(0));
		dto.setStrMonth(domain.getStartMonth().getStrMonthNo()
											  .map(MonthNo::getMonthNo)
											  .orElse(0));
		dto.setStrCurrentMonth(domain.getStartMonth().getStrMonthNo()
													 .map(monthNo -> monthNo.isCurentMonth() ? 1 : 0)
													 .orElse(null));
		dto.setStrPreviousAtr(PreviousClassification.BEFORE.value);
		dto.setEndSpecify(domain.getEndMonth().getSpecifyEndMonth().value);
		dto.setExtractPeriod(domain.getEndMonth().getExtractFromStartMonth().value);
		dto.setEndMonth(domain.getEndMonth().getEndMonthNo()
											.map(MonthNo::getMonthNo)
											.orElse(0));
		dto.setEndCurrentMonth(domain.getEndMonth().getEndMonthNo()
												   .map(monthNo -> monthNo.isCurentMonth() ? 1 : 0)
												   .orElse(null));
		dto.setEndPreviousAtr(domain.getEndMonth().getEndMonthNo()
												  .map(monthNo -> monthNo.getMonthPrevious().value)
												  .orElse(0));
		return dto;
	}

}
