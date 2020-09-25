package nts.uk.ctx.at.request.dom.setting.company.appreasonstandard;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * refactor 4
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.申請承認.設定.会社別.申請定型理由.定型理由
 * @author Doan Duy Hung
 *
 */
@StringMaxLength(40)
public class ReasonForFixedForm extends StringPrimitiveValue<ReasonForFixedForm> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3712012174267892905L;

	public ReasonForFixedForm(String rawValue) {
		super(rawValue);
		// TODO Auto-generated constructor stub
	}

}
