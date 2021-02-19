package nts.uk.ctx.at.schedule.dom.shift.management.shifttable;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerRange;

/**
 * 何日前に通知するかの日数
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務予定.シフト管理.シフト勤務.シフト表.何日前に通知するかの日数
 * @author hiroko_miura
 *
 */
@IntegerRange(min = 0, max = 15)
public class FromNoticeDays  extends IntegerPrimitiveValue<FromNoticeDays> {
	private static final long serialVersionUID = 1L;
	
	public FromNoticeDays (int rawValue) {
		super (rawValue);
	}
}
