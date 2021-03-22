package nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskframe;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerRange;
/**
 * 作業枠NO
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.shared(勤務予定、勤務実績).作業管理.作業枠.作業枠NO
 * @author lan_lt
 *
 */
@IntegerRange(min = 1, max = 5)
public class TaskFrameNo extends IntegerPrimitiveValue<TaskFrameNo> {

	/** serialVersionUID  */
	private static final long serialVersionUID = 1L;

	public TaskFrameNo(Integer rawValue) {
		super(rawValue);
	}
	
}
