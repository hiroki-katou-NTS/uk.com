package nts.uk.ctx.at.request.dom.setting.company.emailset;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * refactor 4
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.申請承認.設定.会社別.メール設定.メール件名
 * @author Doan Duy Hung
 *
 */
@StringMaxLength(100)
public class EmailSubject extends StringPrimitiveValue<EmailSubject> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 220054255182883807L;

	public EmailSubject(String rawValue) {
		super(rawValue);
		// TODO Auto-generated constructor stub
	}

}
