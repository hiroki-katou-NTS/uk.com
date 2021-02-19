package nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskmaster;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.ShiftMasterName;
/**
 * 作業名称
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.shared(勤務予定、勤務実績).作業管理.作業マスタ.作業名称
 * @author lan_lt
 *
 */
@StringMaxLength(50)
public class TaskName extends StringPrimitiveValue<ShiftMasterName>{

	/** serialVersionUID **/
	private static final long serialVersionUID = 1L;

	public TaskName(String rawValue) {
		super(rawValue);
	}

}
