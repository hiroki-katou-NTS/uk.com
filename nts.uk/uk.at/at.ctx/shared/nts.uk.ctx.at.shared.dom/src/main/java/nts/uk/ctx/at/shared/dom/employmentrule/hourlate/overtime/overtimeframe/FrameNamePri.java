package nts.uk.ctx.at.shared.dom.employmentrule.hourlate.overtime.overtimeframe;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;
/**
 * @author loivt
 * 残業枠名称
 */
@StringMaxLength(12)
public class FrameNamePri extends StringPrimitiveValue<FrameNamePri>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FrameNamePri(String rawValue) {
		super(rawValue);
		// TODO Auto-generated constructor stub
	}
	

}
