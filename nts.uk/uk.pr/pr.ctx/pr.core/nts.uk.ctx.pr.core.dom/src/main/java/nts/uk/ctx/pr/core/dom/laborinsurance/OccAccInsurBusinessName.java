package nts.uk.ctx.pr.core.dom.laborinsurance;

/*
* 労災保険事業名称
*/
import nts.arc.primitive.PrimitiveValue;
import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

@StringMaxLength(20)
public class OccAccInsurBusinessName extends StringPrimitiveValue<PrimitiveValue<String>> {
    /** serialVersionUID */
    private static final long serialVersionUID = 1L;

    public OccAccInsurBusinessName(String rawValue) {
        super(rawValue);
    }
}
