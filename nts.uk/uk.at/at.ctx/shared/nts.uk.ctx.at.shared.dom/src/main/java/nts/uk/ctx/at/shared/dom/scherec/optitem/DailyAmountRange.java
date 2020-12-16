package nts.uk.ctx.at.shared.dom.scherec.optitem;

import java.util.Optional;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.optionalitemvalue.AnyItemAmount;

/**
 * 
 * @author anhnm
 * 日別実績の金額範囲
 *
 */
@Getter
public class DailyAmountRange {

    // 上限値
    private Optional<AnyItemAmount> upperLimit;
    
    // 下限値
    private Optional<AnyItemAmount> lowerLimit;
    
    /**
     * Instantiates a new amount range.
     *
     * @param upperLimit the upper limit
     * @param lowerLimit the lower limit
     */
    public DailyAmountRange(Integer upperLimit, Integer lowerLimit) {
        super();
        if (upperLimit == null) {
            this.upperLimit = Optional.empty();
        } else {
            this.upperLimit = Optional.of(new AnyItemAmount(upperLimit));
        }
        if (lowerLimit == null) {
            this.lowerLimit = Optional.empty();
        } else {
            this.lowerLimit = Optional.of(new AnyItemAmount(lowerLimit));
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
