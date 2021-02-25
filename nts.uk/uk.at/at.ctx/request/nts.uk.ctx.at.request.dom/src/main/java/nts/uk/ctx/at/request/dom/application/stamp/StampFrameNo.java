package nts.uk.ctx.at.request.dom.application.stamp;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerRange;

/**
 * refactor 4
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.申請承認.申請.打刻申請.打刻枠No
 * @author Doan Duy Hung
 *
 */
@IntegerRange(max = 10, min = 1)
public class StampFrameNo extends IntegerPrimitiveValue<StampFrameNo>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8212556232884385255L;

	public StampFrameNo(Integer rawValue) {
		super(rawValue);
		// TODO Auto-generated constructor stub
	}

}
