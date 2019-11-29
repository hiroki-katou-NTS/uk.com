package nts.uk.ctx.pr.shared.dom.empinsqualifiinfo.employmentinsqualifiinfo;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
* 雇用保険番号
*/
@StringMaxLength(11)
public class EmpInsNumber extends StringPrimitiveValue<EmpInsNumber>{
    
    private static final long serialVersionUID = 1L;
    
    public EmpInsNumber(String rawValue){
         super(rawValue);
    }
    
}
