package nts.uk.ctx.at.function.dom.processexecution;

import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;
import nts.uk.shr.com.primitive.CodePrimitiveValue;
import nts.uk.shr.com.primitive.ZeroPaddedCode;
/**
 * インデックス再構成カテゴリNO
 * @author ngatt-nws
 *
 */
@StringMaxLength(2)
@StringCharType(CharType.NUMERIC)
@ZeroPaddedCode
public class IndexReconstructionCategoryNO extends CodePrimitiveValue<IndexReconstructionCategoryNO> {
	private static final long serialVersionUID = 1L;

	public IndexReconstructionCategoryNO(String rawValue) {
		super(rawValue);
	}
}
