package nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskmaster;

import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;
import nts.uk.shr.com.primitive.CodePrimitiveValue;

/**
 * 作業コード
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.shared(勤務予定、勤務実績).作業管理.作業マスタ.作業コード
 * @author lan_lt
 *
 */
@StringCharType(CharType.ALPHA_NUMERIC)
@StringMaxLength(20)
public class TaskCode extends CodePrimitiveValue<TaskCode>{

	/**	serialVersionUID  */
	private static final long serialVersionUID = 1L;
	
	public TaskCode(String rawValue) {
		super(rawValue);
	}


}
