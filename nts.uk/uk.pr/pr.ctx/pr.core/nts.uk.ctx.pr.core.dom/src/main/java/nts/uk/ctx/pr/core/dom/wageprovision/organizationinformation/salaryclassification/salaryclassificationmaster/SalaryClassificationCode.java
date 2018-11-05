package nts.uk.ctx.pr.core.dom.wageprovision.organizationinformation.salaryclassification.salaryclassificationmaster;

import nts.arc.primitive.constraint.StringMaxLength;
import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.uk.shr.com.primitive.CodePrimitiveValue;
import nts.uk.shr.com.primitive.ZeroPaddedCode;

/**
 * 給与分類コード
 */
@ZeroPaddedCode
@StringMaxLength(10)
@StringCharType(CharType.NUMERIC)
public class SalaryClassificationCode extends CodePrimitiveValue<SalaryClassificationCode> {

    private static final long serialVersionUID = 1L;

    public SalaryClassificationCode(String rawValue) {
        super(rawValue);
    }

}
