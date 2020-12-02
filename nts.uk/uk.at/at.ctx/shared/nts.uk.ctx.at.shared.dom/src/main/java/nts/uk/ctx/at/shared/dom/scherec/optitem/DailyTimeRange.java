package nts.uk.ctx.at.shared.dom.scherec.optitem;

import java.util.Optional;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.optionalitemvalue.AnyItemTime;

/**
 * 
 * @author anhnm
 * 日別実績の時間範囲
 *
 */
@Getter
public class DailyTimeRange {

    // 上限値
    private Optional<AnyItemTime> upperLimit;
    
    // 下限値
    private Optional<AnyItemTime> lowerLimit;
    
    /**
     * Instantiates a new time range.
     *
     * @param upperLimit the upper limit
     * @param lowerLimit the lower limit
     */
    public DailyTimeRange(Integer upperLimit, Integer lowerLimit) {
        super();
        if (upperLimit == null) {
            this.upperLimit = Optional.empty();
        } else {
            this.upperLimit = Optional.of(new AnyItemTime(upperLimit));
        }
        if (lowerLimit == null) {
            this.lowerLimit = Optional.empty();
        } else {
            this.lowerLimit = Optional.of(new AnyItemTime(lowerLimit));
        }
    }

    /**
     * Checks if is invalid range.
     *
     * @return true, if is invalid range
     */
    public boolean isInvalidRange() {
        if (this.lowerLimit.get().greaterThan(this.upperLimit.get())) {
            return true;
        }
        return false;
    }
}
