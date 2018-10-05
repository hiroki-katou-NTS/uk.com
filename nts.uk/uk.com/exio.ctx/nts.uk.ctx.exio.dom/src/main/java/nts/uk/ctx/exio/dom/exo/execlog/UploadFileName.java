package nts.uk.ctx.exio.dom.exo.execlog;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/*
 * アップロードファイル名
 */
@StringMaxLength(256)
public class UploadFileName extends StringPrimitiveValue<UploadFileName> {

	private static final long serialVersionUID = 1L;

	public UploadFileName(String rawValue) {
		super(rawValue);
	}
}
