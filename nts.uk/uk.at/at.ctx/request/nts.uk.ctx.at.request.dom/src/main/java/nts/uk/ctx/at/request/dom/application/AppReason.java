package nts.uk.ctx.at.request.dom.application;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * refactor 4
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.申請承認.申請.申請理由
 * @author Doan Duy Hung
 *
 */
@StringMaxLength(400)
public class AppReason extends StringPrimitiveValue<AppReason> {

	private static final long serialVersionUID = 1L;

	public AppReason(String rawValue) {
		super(rawValue);
	}
	

}
