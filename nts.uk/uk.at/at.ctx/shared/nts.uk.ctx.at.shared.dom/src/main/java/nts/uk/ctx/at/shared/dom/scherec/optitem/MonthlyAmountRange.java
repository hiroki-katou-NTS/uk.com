package nts.uk.ctx.at.shared.dom.scherec.optitem;

import java.util.Optional;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.common.anyitem.AnyAmountMonth;

/**
 * 
 * @author anhnm
 * 月別実績の金額範囲
 *
 */
@Getter
public class MonthlyAmountRange {

    // 上限値
    private Optional<AnyAmountMonth> upperLimit;
    
    // 下限値
    private Optional<AnyAmountMonth> lowerLimit;
    
    /**
     * Instantiates a new amount range.
     *
     * @param upperLimit the upper limit
     * @param lowerLimit the lower limit
     */
    public MonthlyAmountRange(Integer upperLimit, Integer lowerLimit) {
        super();
        if (upperLimit == null) {
            this.upperLimit = Optional.empty();
        } else {
            this.upperLimit = Optional.of(new AnyAmountMonth(upperLimit));
        }
        if (lowerLimit == null) {
            this.lowerLimit = Optional.empty();
        } else {
            this.lowerLimit = Optional.of(new AnyAmountMonth(lowerLimit));
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
