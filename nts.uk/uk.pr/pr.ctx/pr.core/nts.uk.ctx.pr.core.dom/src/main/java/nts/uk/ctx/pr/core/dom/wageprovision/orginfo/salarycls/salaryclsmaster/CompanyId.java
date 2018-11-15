package nts.uk.ctx.pr.core.dom.wageprovision.orginfo.salarycls.salaryclsmaster;

import nts.arc.primitive.UpperCaseAlphaNumericPrimitiveValue;
import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;

@StringMaxLength(17)
@StringCharType(CharType.ALPHA_NUMERIC)
public class CompanyId extends UpperCaseAlphaNumericPrimitiveValue<CompanyId> {

    public CompanyId(String rawValue) {
        super(rawValue);
    }
}
