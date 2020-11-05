package nts.uk.ctx.at.request.app.find.application.optitem.optitemdto;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.scherec.optitem.*;

import java.util.Optional;

@Setter
@Getter
public class CalcResultRangeDto implements CalcResultRangeSetMemento {
    /**
     * The upper check.
     */
    private boolean upperCheck;

    /**
     * The lower check.
     */
    private boolean lowerCheck;

    // ===================== Optional ======================= //
    /**
     * The number upper.
     */
    private Double numberUpper;

    /**
     * The number lower.
     */
    private Double numberLower;

    /**
     * The time upper.
     */
    private Integer timeUpper;

    /**
     * The time lower.
     */
    private Integer timeLower;

    /**
     * The amount upper.
     */
    private Integer amountUpper;

    /**
     * The amount lower.
     */
    private Integer amountLower;


    @Override
    public void setUpperLimit(CalcRangeCheck upper) {
        this.upperCheck = upper == CalcRangeCheck.SET ? true : false;
    }


    @Override
    public void setLowerLimit(CalcRangeCheck lower) {
        this.lowerCheck = lower == CalcRangeCheck.SET ? true : false;
    }


    @Override
    public void setNumberRange(Optional<NumberRange> range) {
        if (range.isPresent() && range.get().getLowerLimit().isPresent()) {
            this.numberLower = range.get().getLowerLimit().get().v();

        }
        if (range.isPresent() && range.get().getUpperLimit().isPresent()) {
            this.numberUpper = range.get().getUpperLimit().get().v();
        }
    }


    @Override
    public void setTimeRange(Optional<TimeRange> range) {
        if (range.isPresent() && range.get().getLowerLimit().isPresent()) {
            this.timeLower = range.get().getLowerLimit().get().v().intValue();

        }
        if (range.isPresent() && range.get().getUpperLimit().isPresent()) {
            this.timeUpper = range.get().getUpperLimit().get().v().intValue();
        }
    }


    @Override
    public void setAmountRange(Optional<AmountRange> range) {
        if (range.isPresent() && range.get().getLowerLimit().isPresent()) {
            this.amountLower = range.get().getLowerLimit().get().v().intValue();

        }
        if (range.isPresent() && range.get().getUpperLimit().isPresent()) {
            this.amountUpper = range.get().getUpperLimit().get().v().intValue();
        }
    }
}
