package nts.uk.ctx.at.schedule.dom.shift.schedulehorizontal.primitives;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;
/**
 * 
 * @author yennth
 *
 */
@StringMaxLength(20)
public class CategoryName extends StringPrimitiveValue<CategoryName>{
	/** serialVersionUID */
	private static final long serialVersionUID = 1L;
	/** カテゴリ名称 **/
	public CategoryName(String rawValue){
		super(rawValue);
	}
}
