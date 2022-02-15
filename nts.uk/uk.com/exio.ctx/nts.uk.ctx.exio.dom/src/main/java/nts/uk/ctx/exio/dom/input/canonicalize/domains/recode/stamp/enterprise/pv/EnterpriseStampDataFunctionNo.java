package nts.uk.ctx.exio.dom.input.canonicalize.domains.recode.stamp.enterprise.pv;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerRange;

/**
 * E版打刻データファンクションNO
 */
@IntegerRange(min = 0, max = 99)
public class EnterpriseStampDataFunctionNo extends IntegerPrimitiveValue<EnterpriseStampDataFunctionNo> {

    /**
     * Constructs.
     *
     * @param rawValue raw value
     */
    public EnterpriseStampDataFunctionNo(Integer rawValue) {
        super(rawValue);
    }

}
