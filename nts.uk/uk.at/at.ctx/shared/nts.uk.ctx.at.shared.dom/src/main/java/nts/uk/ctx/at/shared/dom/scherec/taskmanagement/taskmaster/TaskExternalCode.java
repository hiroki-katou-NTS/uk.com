package nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskmaster;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;
/**
 * 作業外部コード
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.shared(勤務予定、勤務実績).作業管理.作業マスタ.作業外部コード
 * @author lan_lt
 *
 */
@StringCharType(CharType.ALPHA_NUMERIC)
@StringMaxLength(20)
public class TaskExternalCode extends StringPrimitiveValue<TaskExternalCode>{

	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	public TaskExternalCode(String rawValue) {
		super(rawValue);
	}

}
