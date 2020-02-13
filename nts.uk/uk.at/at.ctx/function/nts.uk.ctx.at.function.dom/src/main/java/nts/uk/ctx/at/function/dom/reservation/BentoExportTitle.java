package nts.uk.ctx.at.function.dom.reservation;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * 帳票タイトル
 * @author Doan Duy Hung
 *
 */
@StringMaxLength(20)
public class BentoExportTitle extends StringPrimitiveValue<BentoExportTitle>{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	public BentoExportTitle(String rawValue) {
		super(rawValue);
	}

}
