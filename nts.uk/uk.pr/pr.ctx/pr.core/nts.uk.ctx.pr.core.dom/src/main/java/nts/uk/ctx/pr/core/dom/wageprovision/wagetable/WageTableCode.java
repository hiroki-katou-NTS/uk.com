package nts.uk.ctx.pr.core.dom.wageprovision.wagetable;

<<<<<<< HEAD
import nts.arc.primitive.StringPrimitiveValue;
=======
>>>>>>> pj/pr/team_G/QMM019
import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;
import nts.uk.shr.com.primitive.CodePrimitiveValue;
import nts.uk.shr.com.primitive.ZeroPaddedCode;

<<<<<<< HEAD
/**
* 賃金テーブルコード
*/
@StringMaxLength(3)
@ZeroPaddedCode
@StringCharType(CharType.NUMERIC)
public class WageTableCode extends CodePrimitiveValue<WageTableCode>
{
    
=======
@StringMaxLength(3)
@StringCharType(CharType.NUMERIC)
@ZeroPaddedCode
public class WageTableCode extends CodePrimitiveValue<WageTableCode> {

>>>>>>> pj/pr/team_G/QMM019
    private static final long serialVersionUID = 1L;
    
    public WageTableCode(String rawValue)
    {
         super(rawValue);
    }
    
}
