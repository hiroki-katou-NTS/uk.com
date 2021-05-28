package nts.uk.ctx.at.shared.dom.workrule.shiftmaster;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 *
 * @author hieunm
 *
 */
@StringMaxLength(10)
public class ShiftMasterImportCode extends StringPrimitiveValue<ShiftMasterImportCode> {

    private static final long serialVersionUID = 1L;

    public ShiftMasterImportCode(String rawValue) {
        super(rawValue);
    }
}
