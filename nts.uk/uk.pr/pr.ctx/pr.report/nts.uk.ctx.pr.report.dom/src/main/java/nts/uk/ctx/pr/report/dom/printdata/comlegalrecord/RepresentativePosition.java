package nts.uk.ctx.pr.report.dom.printdata.comlegalrecord;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * 会社代表者職位
 */
@StringMaxLength(20)
public class RepresentativePosition extends StringPrimitiveValue<RepresentativePosition> {
    private static final long serialVersionUID = 1L;

    public RepresentativePosition(String rawValue)
    {
        super(rawValue);
    }
}
