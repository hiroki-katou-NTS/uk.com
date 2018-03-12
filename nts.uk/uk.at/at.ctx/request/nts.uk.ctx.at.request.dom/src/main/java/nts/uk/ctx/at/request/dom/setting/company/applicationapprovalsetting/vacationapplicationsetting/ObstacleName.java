package nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.vacationapplicationsetting;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;
/**
 * 代表者名
 * @author yennth
 *
 */
@StringMaxLength(8)
public class ObstacleName extends StringPrimitiveValue<ObstacleName>{

	public ObstacleName(String rawValue) {
		super(rawValue);
	}

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

}
