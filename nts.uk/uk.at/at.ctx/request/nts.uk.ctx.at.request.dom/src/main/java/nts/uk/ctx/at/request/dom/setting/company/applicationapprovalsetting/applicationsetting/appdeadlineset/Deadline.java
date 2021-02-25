package nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.appdeadlineset;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerRange;

/**
 * refactor 4
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.申請承認.設定.会社別.申請承認設定.申請設定.申請締切設定.締切日数
 * @author Doan Duy Hung
 *
 */
@IntegerRange(max = 31, min = 0)
public class Deadline extends IntegerPrimitiveValue<Deadline>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4828558600292272324L;

	public Deadline(int rawValue) {
		super(rawValue);
		// TODO Auto-generated constructor stub
	}
	
}
