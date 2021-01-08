package nts.uk.ctx.at.function.app.find.alarm.extractionrange;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.PreviousClassification;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.month.ExtractionPeriodMonth;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.month.YearSpecifiedType;

@Setter
@Getter
public class ExtractionPeriodMonthlyDto {

	private String extractionId;

	private int extractionRange;

	private int unit;

	private int strSpecify;

	private int yearType;

	private int specifyMonth;

	private int strMonth;

	private int strCurrentMonth;

	private int strPreviousAtr;

	private int endSpecify;

	private int extractPeriod;

	private int endMonth;

	private int endCurrentMonth;

	private int endPreviousAtr;

	public static ExtractionPeriodMonthlyDto fromDomain(ExtractionPeriodMonth domain){
		ExtractionPeriodMonthlyDto dto = new ExtractionPeriodMonthlyDto();
		dto.setExtractionId(domain.getExtractionId());
		dto.setExtractionRange(domain.getExtractionRange().value);
		dto.setUnit(domain.getNumberOfMonth().value);
		dto.setStrSpecify(domain.getStartMonth().getSpecifyStartMonth().value);
		dto.setYearType(domain.getStartMonth().getFixedMonthly().isPresent()
				? domain.getStartMonth().getFixedMonthly().get().getYearSpecifiedType().value
				: YearSpecifiedType.CURRENT_YEAR.value);
		dto.setSpecifyMonth(domain.getStartMonth().getFixedMonthly().isPresent()
				? domain.getStartMonth().getFixedMonthly().get().getDesignatedMonth()
				: 0);
		dto.setStrMonth(domain.getStartMonth().getStrMonthNo().isPresent()
				? domain.getStartMonth().getStrMonthNo().get().getMonthNo()
				: 0);
		dto.setStrCurrentMonth(domain.getStartMonth().getStrMonthNo().isPresent()
				? (domain.getStartMonth().getStrMonthNo().get().isCurentMonth() ? 1 : 0)
				: 0);
		dto.setStrPreviousAtr(PreviousClassification.BEFORE.value);
		dto.setEndSpecify(domain.getEndMonth().getSpecifyEndMonth().value);
		dto.setExtractPeriod(domain.getEndMonth().getExtractFromStartMonth().value);
		dto.setEndMonth(domain.getEndMonth().getEndMonthNo().get().getMonthNo());
		dto.setEndCurrentMonth(domain.getEndMonth().getEndMonthNo().get().isCurentMonth() ? 1 : 0);
		dto.setEndPreviousAtr(domain.getEndMonth().getEndMonthNo().get().getMonthPrevious().value);
		return dto;
	}
}
