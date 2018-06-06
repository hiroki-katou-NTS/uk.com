package nts.uk.ctx.sys.assist.dom.storage;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

//ファイル圧縮パスワード
@StringMaxLength(16)
public class FileCompressionPassword extends StringPrimitiveValue<FileCompressionPassword>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FileCompressionPassword(String rawValue) {
		super(rawValue);
		// TODO Auto-generated constructor stub
	}
}
