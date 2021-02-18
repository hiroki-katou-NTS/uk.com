package nts.uk.ctx.at.function.dom.annualworkschedule.primitivevalue;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.就業機能.帳票共通.帳票の出力項目名称
 * 
 * @author LienPTK
 */
@StringMaxLength(20)
public class FormOutputItemName extends StringPrimitiveValue<FormOutputItemName> {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	public FormOutputItemName(String rawValue) {
		super(rawValue);
	}
}

