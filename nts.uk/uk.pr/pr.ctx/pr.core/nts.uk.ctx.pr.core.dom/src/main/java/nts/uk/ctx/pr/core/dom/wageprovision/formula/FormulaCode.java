package nts.uk.ctx.pr.core.dom.wageprovision.formula;

import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;
import nts.uk.shr.com.primitive.CodePrimitiveValue;
import nts.uk.shr.com.primitive.ZeroPaddedCode;

@StringCharType(CharType.NUMERIC)
@StringMaxLength(4)
@ZeroPaddedCode
public class FormulaCode extends CodePrimitiveValue<FormulaCode> {

    private static final long serialVersionUID = 1L;
    public FormulaCode(String rawValue) {
        super(rawValue);
    }
}
