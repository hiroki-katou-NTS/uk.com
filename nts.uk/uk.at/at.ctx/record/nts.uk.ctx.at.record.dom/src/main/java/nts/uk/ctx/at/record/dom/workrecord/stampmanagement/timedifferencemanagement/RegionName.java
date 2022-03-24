package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timedifferencemanagement;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;
import nts.uk.shr.com.primitive.ZeroPaddedCode;

/**
 * 地域名称
 * @author chungnt
 *
 */
@StringMaxLength(200)
@StringCharType(CharType.ALPHA_NUMERIC)
@ZeroPaddedCode
public class RegionName extends StringPrimitiveValue<RegionName>{
	
	/**serialVersionUID*/
	private static final long serialVersionUID = 1L;
	public RegionName (String rawValue){
		super(rawValue);
	}
}
