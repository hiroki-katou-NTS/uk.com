package nts.uk.ctx.at.request.dom.setting.workplace.appuseset;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * refactor 4
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.申請承認.設定.職場別設定.申請利用設定.申請利用設定の備考
 * @author Doan Duy Hung
 *
 */
@StringMaxLength(400)
public class AppUseSetRemark extends StringPrimitiveValue<AppUseSetRemark> {

	public AppUseSetRemark(String rawValue) {
		super(rawValue);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
}
