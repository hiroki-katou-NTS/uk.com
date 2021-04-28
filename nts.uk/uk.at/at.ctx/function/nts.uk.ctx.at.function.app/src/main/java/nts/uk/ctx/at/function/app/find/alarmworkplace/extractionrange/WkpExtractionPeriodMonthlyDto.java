package nts.uk.ctx.at.function.app.find.alarmworkplace.extractionrange;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.PreviousClassification;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.month.ExtractionPeriodMonth;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.month.YearSpecifiedType;
import nts.uk.ctx.at.function.dom.alarmworkplace.ExtractionPeriodMonthly;

@Setter
@Getter
@AllArgsConstructor
public class WkpExtractionPeriodMonthlyDto {

	//Start month
	
	private int strSpecify;

	private Integer strMonth;

	private Boolean strCurrentMonth;

	private Integer strPreviousAtr;

	//End Month

	private int endSpecify;

	private Integer endMonth;

	private Boolean endCurrentMonth;

	private Integer endPreviousAtr;

	public static WkpExtractionPeriodMonthlyDto setdata(ExtractionPeriodMonthly domain){
		return new WkpExtractionPeriodMonthlyDto(
			domain.getStartMonth().getSpecifyStartMonth().value,
			domain.getStartMonth().getStrMonthNo().isPresent() ? domain.getStartMonth().getStrMonthNo().get().getMonthNo() : null,
			domain.getStartMonth().getStrMonthNo().isPresent() ? domain.getStartMonth().getStrMonthNo().get().isCurentMonth() : null,
			domain.getStartMonth().getStrMonthNo().isPresent() ? domain.getStartMonth().getStrMonthNo().get().getMonthPrevious().value : null,
			domain.getEndMonth().getSpecifyEndMonth().value,
			domain.getEndMonth().getEndMonthNo().isPresent() ? domain.getEndMonth().getEndMonthNo().get().getMonthNo() : null,
			domain.getEndMonth().getEndMonthNo().isPresent() ? domain.getEndMonth().getEndMonthNo().get().isCurentMonth() : null,
			domain.getEndMonth().getEndMonthNo().isPresent() ? domain.getEndMonth().getEndMonthNo().get().getMonthPrevious().value : null
		);
	}
}
