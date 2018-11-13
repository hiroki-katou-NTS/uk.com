package nts.uk.ctx.core.dom.printdata;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * メモ
 */
@StringMaxLength(500)
public class Memo extends StringPrimitiveValue<nts.uk.ctx.pr.core.dom.laborinsurance.laborinsuranceoffice.Memo> {

    private static final long serialVersionUID = 1L;

    public Memo(String rawValue) {
        super(rawValue);
    }
}
