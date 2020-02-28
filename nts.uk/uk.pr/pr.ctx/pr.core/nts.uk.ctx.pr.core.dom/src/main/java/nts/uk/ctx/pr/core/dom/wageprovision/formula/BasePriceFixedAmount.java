package nts.uk.ctx.pr.core.dom.wageprovision.formula;

import nts.arc.primitive.DecimalPrimitiveValue;
import nts.arc.primitive.LongPrimitiveValue;
import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.*;

import java.math.BigDecimal;

/**
 * 基準金額固定額
 */

@LongMaxValue(9999999999L)
@LongMinValue(-9999999999L)
public class BasePriceFixedAmount extends LongPrimitiveValue<BasePriceFixedAmount> {

    private static final long serialVersionUID = 1L;

    public BasePriceFixedAmount(Long rawValue) {
        super(rawValue);
    }

}
