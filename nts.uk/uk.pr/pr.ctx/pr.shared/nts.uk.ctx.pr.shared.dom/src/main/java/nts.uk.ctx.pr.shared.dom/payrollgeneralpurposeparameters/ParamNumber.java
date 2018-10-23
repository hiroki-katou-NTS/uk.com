package nts.uk.ctx.pr.shared.dom.payrollgeneralpurposeparameters;

import nts.arc.primitive.StringPrimitiveValue;

/**
* パラメータ数値
*/
public class ParamNumber extends StringPrimitiveValue<ParamNumber> {
    
    private static final long serialVersionUID = 1L;
    
    public ParamNumber(String rawValue)
    {
         super(rawValue);
    }
    
}
