package nts.uk.ctx.at.function.dom.indexreconstruction;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * 	インデックス再構成カテゴリ名
 * @author ngatt-nws
 *
 */
@StringMaxLength(30)
public class CategoryName extends StringPrimitiveValue<CategoryName> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CategoryName(String rawValue) {
		super(rawValue);
	}

}
