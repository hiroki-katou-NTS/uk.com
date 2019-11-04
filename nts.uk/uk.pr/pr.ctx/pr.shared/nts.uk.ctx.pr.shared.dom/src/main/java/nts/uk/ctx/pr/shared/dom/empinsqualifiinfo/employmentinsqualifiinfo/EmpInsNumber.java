package nts.uk.ctx.pr.shared.dom.empinsqualifiinfo.employmentinsqualifiinfo;

import nts.arc.primitive.StringPrimitiveValue;

/**
* 雇用保険番号
*/
public class EmpInsNumber extends StringPrimitiveValue<EmpInsNumber>{
    
    private static final long serialVersionUID = 1L;
    
    public EmpInsNumber(String rawValue){
         super(rawValue);
    }
    
}
