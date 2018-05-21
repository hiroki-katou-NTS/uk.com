package nts.uk.ctx.sys.assist.dom.mastercopy;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * The Class MasterCopyCategoryName.
 */
// マスタコピーカテゴリ
@StringMaxLength(40)
public class MasterCopyCategoryName extends StringPrimitiveValue<MasterCopyCategoryName>{

	/**
	 * Instantiates a new master copy category name.
	 *
	 * @param rawValue the raw value
	 */
	public MasterCopyCategoryName(String rawValue) {
		super(rawValue);
	}

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -496359119655899722L;

}
