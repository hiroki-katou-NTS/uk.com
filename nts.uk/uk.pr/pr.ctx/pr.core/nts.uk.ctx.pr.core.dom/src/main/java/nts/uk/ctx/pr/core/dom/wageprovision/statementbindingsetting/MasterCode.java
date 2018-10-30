package nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

@StringMaxLength(10)
public class MasterCode extends StringPrimitiveValue<MasterCode>{
    private static final long serialVersionUID = 1L;

    public MasterCode(String rawValue)
    {
        super(rawValue);
    }
}
