package nts.uk.shr.com.time.closure;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.shr.com.time.calendar.date.ClosureDate;

/**
 * 締め月
 * 締めに基づく年月を厳密に特定するキー情報
 */
@RequiredArgsConstructor
@EqualsAndHashCode
@ToString
public class ClosureMonth {

    @Getter
    private final YearMonth yearMonth;

    @Getter
    private final int closureId;

    @Getter
    private final ClosureDate closureDate;

    /**
     * 年月
     * @return
     */
    public YearMonth yearMonth() {
        return yearMonth;
    }

    /**
     * 締めID
     * @return
     */
    public int closureId() {
        return closureId;
    }

    /**
     * 締め日
     * @return
     */
    public ClosureDate closureDate() {
        return closureDate;
    }

    /**
     * 既定の期間を求める
     * 年月と締め日から算出できるデフォルトの期間
     * 実際の期間は、途中で締め変更があった場合に変動する（中途半端な期間になったりする）
     * @return
     */
    public DatePeriod defaultPeriod() {
        return closureDate.periodOf(yearMonth);
    }
}