package nts.uk.ctx.pr.core.dom.wageprovision.formula;

import nts.arc.primitive.DecimalPrimitiveValue;
import nts.arc.primitive.LongPrimitiveValue;
import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.*;

import java.math.BigDecimal;

/**
 * 基底項目固定値
 */

@LongMaxValue(9999999999L)
@LongMinValue(-9999999999L)
public class BaseItemFixedValue extends LongPrimitiveValue<BaseItemFixedValue> {

    private static final long serialVersionUID = 1L;

    public BaseItemFixedValue(Long rawValue) {
        super(rawValue);
    }

}
