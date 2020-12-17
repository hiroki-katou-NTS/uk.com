package nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskframe;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerMaxValue;
import nts.arc.primitive.constraint.IntegerMinValue;
/**
 * 作業枠NO
 * @author lan_lt
 *
 */
@IntegerMinValue(1)
@IntegerMaxValue(5)
public class TaskFrameNo extends IntegerPrimitiveValue<TaskFrameNo> {

	/** serialVersionUID  */
	private static final long serialVersionUID = 1L;

	public TaskFrameNo(Integer rawValue) {
		super(rawValue);
	}
	
}
