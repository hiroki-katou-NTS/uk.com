package nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskmaster;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * 作業略名
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.shared(勤務予定、勤務実績).作業管理.作業マスタ.作業略名
 * @author lan_lt
 *
 */
@StringMaxLength(16)
public class TaskAbName extends StringPrimitiveValue<TaskAbName>{

	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	public TaskAbName(String rawValue) {
		super(rawValue);
	}

}
