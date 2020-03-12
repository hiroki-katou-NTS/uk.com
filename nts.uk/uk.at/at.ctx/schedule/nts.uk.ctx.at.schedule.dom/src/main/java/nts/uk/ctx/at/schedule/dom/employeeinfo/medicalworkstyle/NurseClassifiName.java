package nts.uk.ctx.at.schedule.dom.employeeinfo.medicalworkstyle;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * @author ThanhNX 看護区分名称
 */
@StringMaxLength(20)
public class NurseClassifiName extends StringPrimitiveValue<NurseClassifiName> {
	private static final long serialVersionUID = 1L;

	public NurseClassifiName(String rawValue) {
		super(rawValue);
	}

}
