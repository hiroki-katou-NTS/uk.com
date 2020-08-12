package nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * @author ThanhNX
 * 
 *         就業情報端末名称
 */
@StringMaxLength(20)
public class EmpInfoTerminalName extends StringPrimitiveValue<EmpInfoTerminalName> {

	private static final long serialVersionUID = 1L;

	public EmpInfoTerminalName(String rawValue) {
		super(rawValue);
	}

}
