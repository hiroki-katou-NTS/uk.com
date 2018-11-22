package nts.uk.ctx.pr.core.dom.wageprovision.statementlayout;

import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;
import nts.uk.shr.com.primitive.CodePrimitiveValue;
import nts.uk.shr.com.primitive.ZeroPaddedCode;

/**
 * 明細書コード
 */
@StringMaxLength(2)
@ZeroPaddedCode
@StringCharType(CharType.NUMERIC)
public class StatementCode extends CodePrimitiveValue<StatementName> {

    private static final long serialVersionUID = 1L;

    public StatementCode(String rawValue) {
        super(rawValue);

    }
}