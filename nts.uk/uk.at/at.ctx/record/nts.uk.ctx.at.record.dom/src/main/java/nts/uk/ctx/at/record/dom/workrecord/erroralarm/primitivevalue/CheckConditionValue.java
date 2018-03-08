package nts.uk.ctx.at.record.dom.workrecord.erroralarm.primitivevalue;

import nts.arc.primitive.PrimitiveValue;

public interface CheckConditionValue {

	public Integer value();
	
	public int compare(Integer target);
	
	public <T extends PrimitiveValue<T>> int compare(T target);
}
