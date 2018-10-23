package nts.uk.ctx.pr.core.dom.wageprovision.orginfo.salarycls.salaryclsmaster;

import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;
import nts.uk.shr.com.primitive.CodePrimitiveValue;

@StringCharType(CharType.NUMERIC)
@StringMaxLength(10)
public class SalaryClsCode extends CodePrimitiveValue<SalaryClsCode> {

    public SalaryClsCode(String rawValue) {
        super(rawValue);
    }
}
