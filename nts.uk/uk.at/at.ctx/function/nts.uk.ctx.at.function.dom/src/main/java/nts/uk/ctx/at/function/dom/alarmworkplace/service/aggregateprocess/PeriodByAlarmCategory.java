package nts.uk.ctx.at.function.dom.alarmworkplace.service.aggregateprocess;

import lombok.*;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;

import java.util.ArrayList;
import java.util.List;

/**
 * カテゴリ別期間
 *
 * @author Le Huu Dat
 */
@Getter
@AllArgsConstructor
public class PeriodByAlarmCategory {

    int category;
    GeneralDate startDate;
    GeneralDate endDate;
    private YearMonth yearMonth;
}
