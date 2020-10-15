package nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.stampsetting;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerRange;

/**
 * refactor 4
 * 応援枠の表示件数
 * @author Doan Duy Hung
 *
 */
@IntegerRange(max = 20, min = 1)
public class SupportFrameDispNO extends IntegerPrimitiveValue<SupportFrameDispNO> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3738155021814397255L;

	public SupportFrameDispNO(int rawValue) {
		super(rawValue);
		// TODO Auto-generated constructor stub
	}
	
}
