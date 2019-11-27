package nts.uk.ctx.pr.core.dom.socialinsurance.socialinsuranceoffice;

import nts.arc.primitive.constraint.StringMaxLength;
import nts.arc.primitive.StringPrimitiveValue;

/**
 * 厚生年金事業所整理番号2
 */
@StringMaxLength(8)
public class WelfarePensionOfficeNumber2 extends StringPrimitiveValue<WelfarePensionOfficeNumber2> {

    private static final long serialVersionUID = 1L;

    public WelfarePensionOfficeNumber2(String rawValue) {
        super(rawValue);
    }
}