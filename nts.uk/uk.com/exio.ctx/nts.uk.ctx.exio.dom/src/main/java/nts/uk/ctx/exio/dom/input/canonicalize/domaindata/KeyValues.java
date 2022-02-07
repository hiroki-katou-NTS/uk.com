package nts.uk.ctx.exio.dom.input.canonicalize.domaindata;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import lombok.Value;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.exio.dom.input.canonicalize.result.IntermediateResult;

import static java.util.stream.Collectors.toList;

@Value
public class KeyValues {

	private final List<Object> values;

	public static KeyValues create(IntermediateResult interm, List<Integer> itemNos) {
		return itemNos.stream()
				.map(itemNo -> interm.getItemByNo(itemNo).get())
				.map(item -> item.getValue())
				.collect(Collectors.collectingAndThen(toList(), KeyValues::new));
	}
	
	public Object get(int index) {
		return values.get(index);
	}
	
	public int getInt(int index) {
		return (int) values.get(index);
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
