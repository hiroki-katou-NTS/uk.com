package nts.uk.ctx.office.dom.comment;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/*
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.オフィス支援.在席照会. 日々の連絡コメント
 */
@StringMaxLength(100)
public class DailyContactComment extends StringPrimitiveValue<DailyContactComment> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DailyContactComment(String rawValue) {
		super(rawValue);
	}
}
