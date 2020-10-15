package nts.uk.ctx.sys.assist.dom.storage;

import nts.arc.primitive.UpperCaseAlphaNumericPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

//ファイル圧縮パスワード
@StringMaxLength(16)
public class FileCompressionPassword extends UpperCaseAlphaNumericPrimitiveValue<FileCompressionPassword>{

	private static final long serialVersionUID = 1L;

	public FileCompressionPassword(String rawValue) {
		super(rawValue);
	}
}
