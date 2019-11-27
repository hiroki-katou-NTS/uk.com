package nts.uk.ctx.pr.core.dom.wageprovision.wagetable;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * 資格名称
 */
@StringMaxLength(30)
public class QualificationName extends StringPrimitiveValue<QualificationName>
{

    private static final long serialVersionUID = 1L;

    public QualificationName(String rawValue)
    {
        super(rawValue);
    }

}