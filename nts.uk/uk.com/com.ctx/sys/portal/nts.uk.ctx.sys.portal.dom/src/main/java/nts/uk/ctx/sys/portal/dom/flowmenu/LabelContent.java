package nts.uk.ctx.sys.portal.dom.flowmenu;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.システム.ポータル.トップページの部品.フローメニュー作成.ラベル内容
 */
@StringMaxLength(100)
public class LabelContent extends StringPrimitiveValue<LabelContent> {

	private static final long serialVersionUID = 1L;

	public LabelContent(String rawValue) {
		super(rawValue);
	}
}
