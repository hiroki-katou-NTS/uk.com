package nts.uk.ctx.pr.shared.dom.salgenpurposeparam;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

@StringMaxLength(40)
public class GenPurposeParamName extends StringPrimitiveValue<GenPurposeParamName> {

    private static final long serialVersionUID = 1L;

    public GenPurposeParamName(String rawValue) {
        super(rawValue);
    }
}