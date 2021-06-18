package nts.uk.ctx.at.function.app.find.alarmworkplace.extractionrange;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.function.dom.alarmworkplace.ExtractionPeriodDaily;

@Data
@AllArgsConstructor
public class WkpExtractionPeriodDailyDto {

    //Start date
    private int strSpecify;

    private Integer strPreviousMonth;

    private Integer strMonth;

    private Boolean strCurrentMonth;

    private Integer strPreviousDay;

    private Boolean strMakeToDay;

    private Integer strDay;

    //End date

    private int endSpecify;

    private Integer endPreviousDay;

    private Boolean endMakeToDay;

    private Integer endDay;

    private Integer endPreviousMonth;

    private Boolean endCurrentMonth;

    private Integer endMonth;

    public static WkpExtractionPeriodDailyDto setdata(ExtractionPeriodDaily domain) {
        return new WkpExtractionPeriodDailyDto(
            domain.getStartDate().getStartSpecify().value,
            domain.getStartDate().getStrMonth().isPresent() ? domain.getStartDate().getStrMonth().get().getMonthPrevious().value : null,
            domain.getStartDate().getStrMonth().isPresent() ? domain.getStartDate().getStrMonth().get().getMonth() : null,
            domain.getStartDate().getStrMonth().isPresent() ? domain.getStartDate().getStrMonth().get().isCurentMonth() : null,
            domain.getStartDate().getStrDays().isPresent() ? domain.getStartDate().getStrDays().get().getDayPrevious().value : null,
            domain.getStartDate().getStrDays().isPresent() ? domain.getStartDate().getStrDays().get().isMakeToDay() : null,
            domain.getStartDate().getStrDays().isPresent() ? domain.getStartDate().getStrDays().get().getDay().v() : null,
            domain.getEndDate().getEndSpecify().value,
            domain.getEndDate().getEndDays().isPresent() ? domain.getEndDate().getEndDays().get().getDayPrevious().value : null,
            domain.getEndDate().getEndDays().isPresent() ? domain.getEndDate().getEndDays().get().isMakeToDay() : null,
            domain.getEndDate().getEndDays().isPresent() ? domain.getEndDate().getEndDays().get().getDay().v() : null,
            domain.getEndDate().getEndMonth().isPresent() ? domain.getEndDate().getEndMonth().get().getMonthPrevious().value : null,
            domain.getEndDate().getEndMonth().isPresent() ? domain.getEndDate().getEndMonth().get().isCurentMonth() : null,
            domain.getEndDate().getEndMonth().isPresent() ? domain.getEndDate().getEndMonth().get().getMonth() : null
        );
    }
}
