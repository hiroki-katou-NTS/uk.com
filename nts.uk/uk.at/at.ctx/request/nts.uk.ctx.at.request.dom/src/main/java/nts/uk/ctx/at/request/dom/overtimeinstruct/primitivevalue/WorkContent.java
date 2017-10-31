package nts.uk.ctx.at.request.dom.overtimeinstruct.primitivevalue;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * @author loivt
 * 作業内容
 */
@StringMaxLength(200)
public class WorkContent extends StringPrimitiveValue<WorkContent>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public WorkContent(String rawValue) {
		super(rawValue);
		// TODO Auto-generated constructor stub
	}

}
