package nts.uk.ctx.pr.core.dom.laborinsurance.laborinsuranceoffice;

import nts.arc.primitive.constraint.StringMaxLength;
import nts.uk.shr.com.primitive.CodePrimitiveValue;
import nts.uk.shr.com.primitive.ZeroPaddedCode;
import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;

/**
 * 労働保険事業所コード
 */
@StringMaxLength(12)
@ZeroPaddedCode
@StringCharType(CharType.NUMERIC)
public class LaborInsuranceOfficeCode extends CodePrimitiveValue<LaborInsuranceOfficeCode> {

    private static final long serialVersionUID = 1L;

    public LaborInsuranceOfficeCode(String rawValue) {
        super(rawValue);
    }

}
