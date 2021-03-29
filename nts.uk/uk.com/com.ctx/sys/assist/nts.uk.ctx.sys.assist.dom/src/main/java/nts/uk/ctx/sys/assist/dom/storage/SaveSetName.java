package nts.uk.ctx.sys.assist.dom.storage;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

//保存セット名称
@StringMaxLength(30)
public class SaveSetName extends StringPrimitiveValue<SaveSetName>{

	private static final long serialVersionUID = 1L;
	
	public SaveSetName(String rawValue) {
		super(rawValue);
	}
}
