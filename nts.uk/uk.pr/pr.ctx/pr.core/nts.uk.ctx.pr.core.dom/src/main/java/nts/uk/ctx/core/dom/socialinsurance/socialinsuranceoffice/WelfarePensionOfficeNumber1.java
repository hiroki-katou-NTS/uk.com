package nts.uk.ctx.core.dom.socialinsurance.socialinsuranceoffice;

import nts.arc.primitive.constraint.StringMaxLength;
import nts.arc.primitive.StringPrimitiveValue;

/**
 * 厚生年金事業所整理番号1
 */
@StringMaxLength(6)
public class WelfarePensionOfficeNumber1 extends StringPrimitiveValue<WelfarePensionOfficeNumber1> {

    private static final long serialVersionUID = 1L;

    public WelfarePensionOfficeNumber1(String rawValue) {
        super(rawValue);
    }
}
