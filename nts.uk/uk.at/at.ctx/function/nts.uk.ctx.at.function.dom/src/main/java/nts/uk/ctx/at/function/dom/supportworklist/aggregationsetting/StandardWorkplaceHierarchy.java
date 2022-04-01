package nts.uk.ctx.at.function.dom.supportworklist.aggregationsetting;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerRange;

/**
 * 基準職場階層
 */
@IntegerRange(min = 1, max = 9)
public class StandardWorkplaceHierarchy extends IntegerPrimitiveValue<StandardWorkplaceHierarchy> {
    public StandardWorkplaceHierarchy(int rawValue) {
        super(rawValue);
    }
}
