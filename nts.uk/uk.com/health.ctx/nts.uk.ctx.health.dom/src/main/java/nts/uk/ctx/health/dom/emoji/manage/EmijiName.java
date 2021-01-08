package nts.uk.ctx.health.dom.emoji.manage;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/*
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.NittsuSystem.Common (NtsCommons).ヘルスライフ.感情状態管理.感情状態管理.感情名称
 */
@StringMaxLength(20)
public class EmijiName extends StringPrimitiveValue<EmijiName> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EmijiName(String rawValue) {
		super(rawValue);
	}

}
