package nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;
import nts.uk.shr.com.primitive.ZeroPaddedCode;

/**
 * 明細書コード
 */
@ZeroPaddedCode
@StringMaxLength(2)
@StringCharType(CharType.NUMERIC)
public class StatementCode extends StringPrimitiveValue<StatementCode> {
    private static final long serialVersionUID = 1L;

    public StatementCode(String rawValue)
    {
        super(rawValue);
    }
}
