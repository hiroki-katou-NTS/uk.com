package nts.uk.ctx.at.request.dom.setting.company.emailset;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * refactor 4
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.申請承認.設定.会社別.メール設定.メール本文
 * @author Doan Duy Hung
 *
 */
@StringMaxLength(1000)
public class EmailText extends StringPrimitiveValue<EmailText> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 400579024074096055L;

	public EmailText(String rawValue) {
		super(rawValue);
		// TODO Auto-generated constructor stub
	}

}
