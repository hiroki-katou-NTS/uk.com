package nts.uk.ctx.at.request.dom.setting.company.appreasonstandard;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerRange;

/**
 * refactor 4
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.申請承認.設定.会社別.申請定型理由.申請定型理由コード
 * @author Doan Duy Hung
 *
 */
@IntegerRange(max = 99, min = 1)
public class AppStandardReasonCode extends IntegerPrimitiveValue<AppStandardReasonCode>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1221605104909349465L;

	public AppStandardReasonCode(int rawValue) {
		super(rawValue);
		// TODO Auto-generated constructor stub
	}

}
