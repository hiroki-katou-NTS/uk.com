package nts.uk.ctx.at.aggregation.dom.scheduledailytable;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * 勤務計画実施表の名称
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.予実集計.勤務計画実施表.勤務計画実施表の名称
 * @author dan_pv
 *
 */
@StringMaxLength(20)
public class ScheduleDailyTableName extends StringPrimitiveValue<ScheduleDailyTableName> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 634576031617622920L;

	public ScheduleDailyTableName(String rawValue) {
		super(rawValue);
	}

}
