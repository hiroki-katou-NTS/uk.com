package nts.uk.ctx.pr.transfer.dom.rsdttaxpayee;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * 住民税納付先コード
 */
@StringMaxLength(6)
@StringCharType(CharType.ALPHA_NUMERIC)
public class ResidentTaxPayeeCode extends StringPrimitiveValue<ResidentTaxPayeeCode> {

    private static final long serialVersionUID = 1L;

    public ResidentTaxPayeeCode(String rawValue) {
        super(rawValue);
    }

}
