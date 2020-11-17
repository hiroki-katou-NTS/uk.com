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

    String name;

    GeneralDate startDate;

    GeneralDate endDate;

    int period36Agreement;

    public List<GeneralDate> getListDate() {
        List<GeneralDate> result = new ArrayList<GeneralDate>();
        GeneralDate date = GeneralDate.localDate(startDate.localDate());
        while (date.beforeOrEquals(endDate)) {
            result.add(date);
            date = date.addDays(1);
        }
        return result;
    }

}
