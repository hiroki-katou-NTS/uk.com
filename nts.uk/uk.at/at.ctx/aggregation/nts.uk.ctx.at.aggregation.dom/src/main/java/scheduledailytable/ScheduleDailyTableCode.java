package scheduledailytable;

import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;
import nts.uk.shr.com.primitive.CodePrimitiveValue;

/**
 * 勤務計画実施表のコード
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.予実集計.勤務計画実施表.勤務計画実施表のコード
 * @author dan_pv
 *
 */
@StringCharType(CharType.NUMERIC)
@StringMaxLength(2)
public class ScheduleDailyTableCode extends CodePrimitiveValue<ScheduleDailyTableCode> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5929240406372373059L;

	public ScheduleDailyTableCode(String rawValue) {
		super(rawValue);
	}

}
