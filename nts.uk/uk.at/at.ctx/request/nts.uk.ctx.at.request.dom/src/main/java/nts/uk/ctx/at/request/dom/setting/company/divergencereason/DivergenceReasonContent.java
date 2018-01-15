package nts.uk.ctx.at.request.dom.setting.company.divergencereason;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;
/**
 * @author loivt
 * 乖離理由入力
 */
@StringMaxLength(400)
public class DivergenceReasonContent extends StringPrimitiveValue<DivergenceReasonContent>{
private static final long serialVersionUID = 1L;
	
	public DivergenceReasonContent(String rawValue) {
		super(rawValue);
		
	}
}
