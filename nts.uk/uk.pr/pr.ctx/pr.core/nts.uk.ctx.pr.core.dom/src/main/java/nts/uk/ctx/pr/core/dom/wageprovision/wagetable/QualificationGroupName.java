package nts.uk.ctx.pr.core.dom.wageprovision.wagetable;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;

/**
* 資格グループ名
*/@StringMaxLength(30)
public class QualificationGroupName extends StringPrimitiveValue<QualificationGroupName>
{
    
    private static final long serialVersionUID = 1L;
    
    public QualificationGroupName(String rawValue)
    {
         super(rawValue);
    }
    
}
