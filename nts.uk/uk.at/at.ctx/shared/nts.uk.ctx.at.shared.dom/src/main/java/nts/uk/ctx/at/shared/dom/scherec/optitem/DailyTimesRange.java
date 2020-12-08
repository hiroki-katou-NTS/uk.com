package nts.uk.ctx.at.shared.dom.scherec.optitem;

import java.math.BigDecimal;
import java.util.Optional;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.optionalitemvalue.AnyItemTimes;

/**
 * 
 * @author anhnm
 * 日別実績の回数範囲
 *
 */
@Getter
public class DailyTimesRange {

    // 上限値
    private Optional<AnyItemTimes> upperLimit;
    
    // 下限値
    private Optional<AnyItemTimes> lowerLimit;
    
    /**
     * Instantiates a new times range.
     *
     * @param upperLimit the upper limit
     * @param lowerLimit the lower limit
     */
    public DailyTimesRange(BigDecimal upperLimit, BigDecimal lowerLimit) {
        super();
        if (upperLimit == null) {
            this.upperLimit = Optional.empty();
        } else {
            this.upperLimit = Optional.of(new AnyItemTimes(upperLimit));
        }
        if (lowerLimit == null) {
            this.lowerLimit = Optional.empty();
        } else {
            this.lowerLimit = Optional.of(new AnyItemTimes(lowerLimit));
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
