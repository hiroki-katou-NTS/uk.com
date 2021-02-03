package nts.uk.ctx.at.shared.dom.employeeworkway.medicalworkstyle;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;
/**
 * 契約コード
 * @author lan_lt
 *
 */
@StringCharType(CharType.NUMERIC)
@StringMaxLength(12)
public class ContractCode extends StringPrimitiveValue<ContractCode>{
	
	private static final long serialVersionUID = 1L;

	public ContractCode(String rawValue) {
		super(rawValue);
	}
	
}
