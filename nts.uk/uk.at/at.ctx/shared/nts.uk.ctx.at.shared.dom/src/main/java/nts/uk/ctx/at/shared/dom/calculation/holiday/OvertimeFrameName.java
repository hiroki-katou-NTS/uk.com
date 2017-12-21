package nts.uk.ctx.at.shared.dom.calculation.holiday;
/**
 * @author phongtq
 */
import nts.arc.primitive.PrimitiveValue;
import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;
@StringMaxLength(12)
public class OvertimeFrameName extends StringPrimitiveValue<PrimitiveValue<String>> {
	/** serialVersionUID */
	private static final long serialVersionUID = 1L;
	
	public OvertimeFrameName(String rawValue) {
		super(rawValue);
	}
}