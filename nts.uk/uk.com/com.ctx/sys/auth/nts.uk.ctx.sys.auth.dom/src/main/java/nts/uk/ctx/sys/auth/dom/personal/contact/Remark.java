package nts.uk.ctx.sys.auth.dom.personal.contact;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * 記念日のタイトル
 */
@StringMaxLength(200)
public class Remark extends StringPrimitiveValue<Remark> {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public Remark(String rawValue) {
        super(rawValue);
    }
}
