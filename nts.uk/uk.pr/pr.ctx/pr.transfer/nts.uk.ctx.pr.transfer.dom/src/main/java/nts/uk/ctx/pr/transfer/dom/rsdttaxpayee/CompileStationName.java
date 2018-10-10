package nts.uk.ctx.pr.transfer.dom.rsdttaxpayee;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * とりまとめ局名
 */
@StringMaxLength(22)
public class CompileStationName extends StringPrimitiveValue<CompileStationName> {

    private static final long serialVersionUID = 1L;

    public CompileStationName(String rawValue) {
        super(rawValue);
    }

}
