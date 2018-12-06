package nts.uk.ctx.pr.core.dom.wageprovision.wagetable;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.*;

/**
 * 範囲下限
 */

@StringMaxLength(3)
@StringCharType(CharType.ALPHA_NUMERIC)
public class MasterCode extends StringPrimitiveValue<MasterCode>
{

    private static final long serialVersionUID = 1L;

    public MasterCode(String rawValue)
    {
        super(rawValue);
    }

}
