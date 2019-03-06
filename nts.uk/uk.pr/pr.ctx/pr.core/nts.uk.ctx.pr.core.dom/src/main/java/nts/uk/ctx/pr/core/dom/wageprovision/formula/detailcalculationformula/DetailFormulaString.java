package nts.uk.ctx.pr.core.dom.wageprovision.formula.detailcalculationformula;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

@StringMaxLength(2000)
public class DetailFormulaString extends StringPrimitiveValue<DetailFormulaString> {
    public DetailFormulaString(String rawValue) {
        super(rawValue);
    }
}
