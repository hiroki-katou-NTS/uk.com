package nts.uk.ctx.at.function.dom.alarmworkplace.service.aggregateprocess;

import lombok.*;
import nts.arc.time.GeneralDate;

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
}
