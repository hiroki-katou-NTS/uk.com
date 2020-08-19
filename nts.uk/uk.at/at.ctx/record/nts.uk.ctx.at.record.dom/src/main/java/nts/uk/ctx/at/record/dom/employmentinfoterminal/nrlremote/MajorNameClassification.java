package nts.uk.ctx.at.record.dom.employmentinfoterminal.nrlremote;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * @author ThanhNX
 *
 *         リモート項目名称
 */
@StringMaxLength(20)
public class MajorNameClassification extends StringPrimitiveValue<MajorNameClassification> {

	private static final long serialVersionUID = 1L;

	public MajorNameClassification(String rawValue) {
		super(rawValue);
	}

}
