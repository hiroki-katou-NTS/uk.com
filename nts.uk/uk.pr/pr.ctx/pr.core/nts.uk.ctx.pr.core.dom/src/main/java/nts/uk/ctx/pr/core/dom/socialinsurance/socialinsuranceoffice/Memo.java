package nts.uk.ctx.pr.core.dom.socialinsurance.socialinsuranceoffice;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * メモ
 */
@StringMaxLength(500)
public class Memo extends StringPrimitiveValue<Memo> {

    private static final long serialVersionUID = 1L;

    public Memo(String rawValue) {
        super(rawValue);
    }
}
