package nts.uk.ctx.at.function.dom.employmentinfoterminal.infoterminal;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerMaxValue;

/**
 * 
 * @author huylq
 *
 *	就業情報端末コード	
 */

@IntegerMaxValue(9999)
public class EmpInfoTerminalCode extends IntegerPrimitiveValue<EmpInfoTerminalCode>{
	
	private static final long serialVersionUID = 1L;

	public EmpInfoTerminalCode(Integer rawValue) {
		super(rawValue);
	}
}
