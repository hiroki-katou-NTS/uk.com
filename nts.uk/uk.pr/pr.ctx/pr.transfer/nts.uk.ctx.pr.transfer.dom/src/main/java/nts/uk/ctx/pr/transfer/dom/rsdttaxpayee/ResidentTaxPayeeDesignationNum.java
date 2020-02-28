package nts.uk.ctx.pr.transfer.dom.rsdttaxpayee;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * 住民税納付先指定番号
 */
@StringMaxLength(15)
@StringCharType(CharType.NUMERIC)
public class ResidentTaxPayeeDesignationNum extends StringPrimitiveValue<ResidentTaxPayeeDesignationNum> {

    private static final long serialVersionUID = 1L;

    public ResidentTaxPayeeDesignationNum(String rawValue) {
        super(rawValue);
    }

}
