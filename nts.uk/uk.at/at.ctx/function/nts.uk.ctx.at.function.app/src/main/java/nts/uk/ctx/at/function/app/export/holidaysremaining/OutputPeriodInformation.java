package nts.uk.ctx.at.function.app.export.holidaysremaining;


import lombok.Data;

import nts.arc.time.calendar.period.DatePeriod;
import nts.arc.time.calendar.period.YearMonthPeriod;

import java.util.List;
import java.util.Optional;

/**
 * 出力期間情報
 */
@Data
public class OutputPeriodInformation {
    // 年月期間
    private YearMonthPeriod period;

    private Optional<List<PeriodCorrespondingYm>> currentMonthAndFuture;

    private Optional<YearMonthPeriod> pastPeriod;

}
