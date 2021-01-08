package nts.uk.ctx.office.dom.goout;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/*
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.オフィス支援.在席照会.在席照会.外出理由入力
 */
@StringMaxLength(400)
public class GoOutReason extends StringPrimitiveValue<GoOutReason> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public GoOutReason(String rawValue) {
		super(rawValue);
	}

}
