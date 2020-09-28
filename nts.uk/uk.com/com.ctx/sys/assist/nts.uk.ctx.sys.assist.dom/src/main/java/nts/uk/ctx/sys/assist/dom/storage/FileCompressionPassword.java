package nts.uk.ctx.sys.assist.dom.storage;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;

//ファイル圧縮パスワード
@StringMaxLength(16)
@StringCharType(CharType.ALPHA_NUMERIC)
public class FileCompressionPassword extends StringPrimitiveValue<FileCompressionPassword>{

	private static final long serialVersionUID = 1L;

	public FileCompressionPassword(String rawValue) {
		super(rawValue);
	}
}
