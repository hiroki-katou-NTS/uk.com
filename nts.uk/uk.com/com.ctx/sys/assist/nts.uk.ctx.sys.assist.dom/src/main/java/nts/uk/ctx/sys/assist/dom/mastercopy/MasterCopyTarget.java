package nts.uk.ctx.sys.assist.dom.mastercopy;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * The Class MasterCopyTarget.
 */
// マスタコピー対象
@StringMaxLength(100)
public class MasterCopyTarget extends StringPrimitiveValue<MasterCopyTarget>{

	/**
	 * Instantiates a new master copy target.
	 *
	 * @param rawValue the raw value
	 */
	public MasterCopyTarget(String rawValue) {
		super(rawValue);
	}

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 884391690882365052L;

}
