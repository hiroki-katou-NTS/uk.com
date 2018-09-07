package nts.uk.ctx.core.dom.socialinsurance.socialinsuranceoffice;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerRange;

/**
 * 厚生年金基金番号
 */
@IntegerRange(min = 0, max = 9999)
public class WelfarePensionFundNumber extends IntegerPrimitiveValue<WelfarePensionFundNumber> {

    private static final long serialVersionUID = 1L;

    public WelfarePensionFundNumber(Integer rawValue) {
        super(rawValue);
    }
}
