package nts.uk.ctx.at.function.app.export.holidaysremaining;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.time.calendar.period.DatePeriod;

import java.util.Optional;

/**
 * 当月期間：年月に対応する期間
 */
@AllArgsConstructor
@Getter
@Setter
public class CurrentPeriod {
    private DatePeriod currentPeriod;
}
