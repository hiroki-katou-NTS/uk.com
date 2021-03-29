package nts.uk.ctx.at.shared.dom.application.common;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * refactor 4
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.申請承認.申請.差し戻しコメント
 * @author Doan Duy Hung
 *
 */
@StringMaxLength(400)
public class ReasonForReversionShare extends StringPrimitiveValue<ReasonForReversionShare>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7500471897109841422L;

	public ReasonForReversionShare(String rawValue) {
		super(rawValue);
		// TODO Auto-generated constructor stub
	}

}
