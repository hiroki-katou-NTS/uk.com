package nts.uk.ctx.core.dom.printdata;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * 会社代表者名
 */
@StringMaxLength(20)
public class RepresentativeName extends StringPrimitiveValue<RepresentativeName> {
    private static final long serialVersionUID = 1L;

    public RepresentativeName(String rawValue)
    {
        super(rawValue);
    }
}
