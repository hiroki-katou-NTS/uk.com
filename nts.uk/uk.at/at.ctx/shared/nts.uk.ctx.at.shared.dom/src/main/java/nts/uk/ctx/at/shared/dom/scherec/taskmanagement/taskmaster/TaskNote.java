package nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskmaster;
/**
 * 作業備考
 * @author lan_lt
 *
 */
import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;
/**
 * 作業備考
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.shared(勤務予定、勤務実績).作業管理.作業マスタ.作業備考
 * @author lan_lt
 *
 */
@StringMaxLength(200)
public class TaskNote extends StringPrimitiveValue<TaskNote>{

	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	public TaskNote(String rawValue) {
		super(rawValue);
	}

}
