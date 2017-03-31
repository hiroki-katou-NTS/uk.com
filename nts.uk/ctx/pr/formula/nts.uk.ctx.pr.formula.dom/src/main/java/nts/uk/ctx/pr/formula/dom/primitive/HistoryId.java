package nts.uk.ctx.pr.formula.dom.primitive;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * @author hungnm
 *
 */
@StringMaxLength(36)
public class HistoryId extends StringPrimitiveValue<HistoryId> {
	public HistoryId(String rawValue) {
		super(rawValue);
	}

	private static final long serialVersionUID = 1L;
}
