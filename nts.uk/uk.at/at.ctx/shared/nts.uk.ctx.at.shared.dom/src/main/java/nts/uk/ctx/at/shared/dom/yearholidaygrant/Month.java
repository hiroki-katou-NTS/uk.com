package nts.uk.ctx.at.shared.dom.yearholidaygrant;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.PrimitiveValue;
import nts.arc.primitive.constraint.IntegerRange;

@IntegerRange(max = 11, min = 0)
public class Month extends IntegerPrimitiveValue<PrimitiveValue<Integer>>{
    /** serialVersionUID */
    private static final long serialVersionUID = 1L;
    public Month(Integer rawValue){
        super(rawValue);
    }
}

