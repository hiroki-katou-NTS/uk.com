package nts.uk.ctx.at.aggregation.dom.scheduledailytable;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * 勤務計画実施表のコメント
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.予実集計.勤務計画実施表.勤務計画実施表のコメント
 * @author dan_pv
 *
 */
@StringMaxLength(200)
public class ScheduleDailyTableComment extends StringPrimitiveValue<ScheduleDailyTableComment> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3455730316604059843L;

	public ScheduleDailyTableComment(String rawValue) {
		super(rawValue);
	}

}
