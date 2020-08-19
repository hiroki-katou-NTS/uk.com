package nts.uk.ctx.at.record.dom.employmentinfoterminal.nrlremote;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * @author ThanhNX
 *
 *         変数名
 */
@StringMaxLength(20)
public class VariableName extends StringPrimitiveValue<VariableName> {

	private static final long serialVersionUID = 1L;

	public VariableName(String rawValue) {
		super(rawValue);
	}

}
