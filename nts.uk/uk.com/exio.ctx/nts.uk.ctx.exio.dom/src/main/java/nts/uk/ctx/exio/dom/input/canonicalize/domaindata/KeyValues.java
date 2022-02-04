package nts.uk.ctx.exio.dom.input.canonicalize.domaindata;

import java.math.BigDecimal;
import java.util.List;

import lombok.Value;
import nts.arc.time.GeneralDate;

@Value
public class KeyValues {

	private final List<Object> values;
	
	public Object get(int index) {
		return values.get(index);
	}
	
	public Long getInt(int index) {
		return (Long) values.get(index);
	}
	
	public BigDecimal getBigDecimal(int index) {
		return (BigDecimal) values.get(index);
	}
	
	public String getString(int index) {
		return (String) values.get(index);
	}
	
	public GeneralDate getGeneralDate(int index) {
		return (GeneralDate) values.get(index);
	}
}
