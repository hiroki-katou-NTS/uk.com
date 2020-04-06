package nts.uk.ctx.at.record.dom.stamp.management;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * 打刻ページコメント
 * @author phongtq
 *
 */

@StringMaxLength(200)
public class PageComment extends StringPrimitiveValue<PageComment>{
	
	private static final long serialVersionUID = 1L;

	/**
	 * @param rawValue
	 */
	public PageComment(String rawValue) {
		super(rawValue);
	}
}