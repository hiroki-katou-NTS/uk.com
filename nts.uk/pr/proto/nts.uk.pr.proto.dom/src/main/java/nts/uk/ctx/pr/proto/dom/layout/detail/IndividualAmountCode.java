package nts.uk.ctx.pr.proto.dom.layout.detail;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLengh;

@StringMaxLengh(2)
public class IndividualAmountCode extends StringPrimitiveValue<IndividualAmountCode> {

    public IndividualAmountCode(String rawValue) {
        super(rawValue);
    }

    private static final long serialVersionUID = 1L;
}

    
