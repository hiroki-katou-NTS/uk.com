package nts.uk.ctx.at.aggregation.dom.scheduledailytable;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * 勤務計画実施表の印鑑欄見出し
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.予実集計.勤務計画実施表.勤務計画実施表の印鑑欄見出し
 * @author dan_pv
 *
 */
@StringMaxLength(8)
public class ScheduleDailyTableInkanTitle extends  StringPrimitiveValue<ScheduleDailyTableInkanTitle> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4677917277045690855L;
	
	public ScheduleDailyTableInkanTitle(String rawValue) {
		super(rawValue);
	}

}
