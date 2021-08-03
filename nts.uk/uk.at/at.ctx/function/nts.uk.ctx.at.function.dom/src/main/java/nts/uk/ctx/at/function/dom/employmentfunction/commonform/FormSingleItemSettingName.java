package nts.uk.ctx.at.function.dom.employmentfunction.commonform;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.就業機能.帳票共通.帳票単独項目設定名称
 * @author Nws-DungDV
 *
 */
@StringMaxLength(12)
public class FormSingleItemSettingName extends StringPrimitiveValue<FormSingleItemSettingName> {
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	public FormSingleItemSettingName(String rawValue) {
		super(rawValue);
	}
}
