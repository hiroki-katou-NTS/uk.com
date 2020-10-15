package nts.uk.ctx.at.record.dom.employmentinfoterminal.nrlremote;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * @author ThanhNX
 *
 *         入力範囲
 */
@StringMaxLength(100)
public class NrlRemoteInputRange extends StringPrimitiveValue<NrlRemoteInputRange> {

	private static final long serialVersionUID = 1L;

	public NrlRemoteInputRange(String rawValue) {
		super(rawValue);
	}

}
