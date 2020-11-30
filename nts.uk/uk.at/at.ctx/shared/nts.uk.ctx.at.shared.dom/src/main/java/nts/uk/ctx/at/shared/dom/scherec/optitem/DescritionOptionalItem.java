package nts.uk.ctx.at.shared.dom.scherec.optitem;

import nts.arc.primitive.constraint.StringMaxLength;
import nts.uk.shr.com.primitive.CodePrimitiveValue;
/**
 * 
 * @author anhnm
 * 任意項目の説明文
 */
@StringMaxLength(200)
public class DescritionOptionalItem extends CodePrimitiveValue<DescritionOptionalItem> {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public DescritionOptionalItem(String rawValue) {
        super(rawValue);
    }

}
