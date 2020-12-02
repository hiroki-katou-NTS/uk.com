package nts.uk.ctx.at.shared.dom.scherec.optitem;

import java.util.Optional;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.common.anyitem.AnyTimeMonth;

/**
 * 
 * @author anhnm
 * 月別実績の時間範囲
 * 
 */
@Getter
public class MonthlyTimeRange {

    // 上限値
    private Optional<AnyTimeMonth> upperLimit;
    
    // 下限値
    private Optional<AnyTimeMonth> lowerLimit;
    
    /**
     * Instantiates a new time range.
     *
     * @param upperLimit the upper limit
     * @param lowerLimit the lower limit
     */
    public MonthlyTimeRange(Integer upperLimit, Integer lowerLimit) {
        super();
        if (upperLimit == null) {
            this.upperLimit = Optional.empty();
        } else {
            this.upperLimit = Optional.of(new AnyTimeMonth(upperLimit));
        }
        if (lowerLimit == null) {
            this.lowerLimit = Optional.empty();
        } else {
            this.lowerLimit = Optional.of(new AnyTimeMonth(lowerLimit));
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
