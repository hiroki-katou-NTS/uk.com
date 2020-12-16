package nts.uk.ctx.at.shared.dom.scherec.optitem;

import nts.arc.primitive.constraint.StringMaxLength;
import nts.uk.shr.com.primitive.CodePrimitiveValue;

/**
 * 
 * @author anhnm
 * 任意項目のメモ
 */
@StringMaxLength(500)
public class NoteOptionalItem extends CodePrimitiveValue<NoteOptionalItem> {

    public NoteOptionalItem(String rawValue) {
        super(rawValue);
    }

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

}
