package nts.uk.ctx.pr.core.dom.laborinsurance.laborinsuranceoffice;

import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;
import nts.uk.shr.com.primitive.CodePrimitiveValue;
import nts.uk.shr.com.primitive.ZeroPaddedCode;

/**
 * 公共職業安定所コード
 */
@StringMaxLength(4)
@ZeroPaddedCode
@StringCharType(CharType.NUMERIC)
public class PublicEmploymentSecurityOfficeCode extends CodePrimitiveValue<PublicEmploymentSecurityOfficeCode> {

    private static final long serialVersionUID = 1L;

    public PublicEmploymentSecurityOfficeCode(String rawValue) {
        super(rawValue);
    }
}
