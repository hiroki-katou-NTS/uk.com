package nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskmaster;

import nts.arc.primitive.StringPrimitiveValue;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.ShiftMasterName;

/**
 * 作業名称
 * @author lan_lt
 *
 */
public class TaskName extends StringPrimitiveValue<ShiftMasterName>{

	/** serialVersionUID **/
	private static final long serialVersionUID = 1L;

	public TaskName(String rawValue) {
		super(rawValue);
	}

}
