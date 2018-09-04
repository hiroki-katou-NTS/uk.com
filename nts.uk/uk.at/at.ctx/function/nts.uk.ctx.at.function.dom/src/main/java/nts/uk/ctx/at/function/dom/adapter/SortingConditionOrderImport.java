package nts.uk.ctx.at.function.dom.adapter;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SortingConditionOrderImport {
	/** The order. */
	private int order;
	
	/** The type. */
	private RegularSortingTypeImport type;
}
