package nts.uk.ctx.exio.dom.exo.outcnddetail;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;


@StringMaxLength(20)
public class ExtOutCndSearchCd extends StringPrimitiveValue<ExtOutCndSearchCd>{
	/**
	 * 外部出力条件検索コード
	 */
	private static final long serialVersionUID = 1L;
	
	public ExtOutCndSearchCd(String rawValue) {
		super(rawValue);
		// TODO Auto-generated constructor stub
	}


}
