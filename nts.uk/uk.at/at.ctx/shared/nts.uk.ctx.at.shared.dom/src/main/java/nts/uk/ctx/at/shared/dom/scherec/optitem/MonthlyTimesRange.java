package nts.uk.ctx.at.shared.dom.scherec.optitem;

import java.util.Optional;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.common.anyitem.AnyTimesMonth;

/**
 * @author anhnm
 * 月別実績の回数範囲
 *
 */
@Getter
public class MonthlyTimesRange {

    // 上限値
    private Optional<AnyTimesMonth> upperLimit;
    
    // 下限値
    private Optional<AnyTimesMonth> lowerLimit;
    
    /**
     * Instantiates a new times range.
     *
     * @param upperLimit the upper limit
     * @param lowerLimit the lower limit
     */
    public MonthlyTimesRange(Double upperLimit, Double lowerLimit) {
        super();
        if (upperLimit == null) {
            this.upperLimit = Optional.empty();
        } else {
            this.upperLimit = Optional.of(new AnyTimesMonth(upperLimit));
        }
        if (lowerLimit == null) {
            this.lowerLimit = Optional.empty();
        } else {
            this.lowerLimit = Optional.of(new AnyTimesMonth(lowerLimit));
        }
    }

    /**
     * Checks if is invalid range.
     *
     * @return true, if is invalid range
     */
    public boolean isInvalidRange() {
        if (this.lowerLimit.isPresent() && this.upperLimit.isPresent()) {
            if (this.lowerLimit.get().greaterThan(this.upperLimit.get())) {
                return true;
            }
            return false;
        } else {
            return false;
        }
    }
}
