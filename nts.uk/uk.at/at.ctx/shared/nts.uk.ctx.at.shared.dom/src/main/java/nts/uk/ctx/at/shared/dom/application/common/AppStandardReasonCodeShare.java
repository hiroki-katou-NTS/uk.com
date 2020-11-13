package nts.uk.ctx.at.shared.dom.application.common;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerRange;

/**
 * 申請定型理由コード
 * 
 * @author thanh_nx
 *
 */
@IntegerRange(max = 99, min = 1)
public class AppStandardReasonCodeShare extends IntegerPrimitiveValue<AppStandardReasonCodeShare> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1221605104909349465L;

	public AppStandardReasonCodeShare(int rawValue) {
		super(rawValue);
		// TODO Auto-generated constructor stub
	}

}
