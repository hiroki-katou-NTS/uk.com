package nts.uk.ctx.pr.core.dom.wageprovision.organizationinfor.salarycls.salaryclsmaster;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * メモ
 */

@StringMaxLength(500)
public class Memo extends StringPrimitiveValue<Memo> {

    public Memo(String rawValue) {
        super(rawValue);
    }
}
