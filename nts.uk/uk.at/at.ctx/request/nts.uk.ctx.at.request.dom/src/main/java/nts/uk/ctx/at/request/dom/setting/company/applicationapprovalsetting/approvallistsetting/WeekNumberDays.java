package nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.approvallistsetting;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerRange;
/**
 * refactor 4
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.申請承認.設定.会社別.申請承認設定.承認一覧設定.週間日数（警告前日数）
 * @author Doan Duy Hung
 *
 */
@IntegerRange(max = 7, min = 0)
public class WeekNumberDays extends IntegerPrimitiveValue<WeekNumberDays>{

	public WeekNumberDays(int rawValue) {
		super(rawValue);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -6636589118328505947L;

}
