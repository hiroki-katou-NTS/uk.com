package nts.uk.ctx.at.request.dom.setting.company.appreasonstandard;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerRange;

/**
 * refactor 4
 * 申請定型理由コード
 * @author Doan Duy Hung
 *
 */
@IntegerRange(max = 99, min = 1)
public class AppStandardReasonCode extends IntegerPrimitiveValue<AppStandardReasonCode>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1221605104909349465L;

	public AppStandardReasonCode(Integer rawValue) {
		super(rawValue);
		// TODO Auto-generated constructor stub
	}

}
