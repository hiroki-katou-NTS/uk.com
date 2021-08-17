package nts.uk.ctx.at.aggregation.dom.adapter.employee;

import lombok.AllArgsConstructor;
import lombok.Value;

@AllArgsConstructor
@Value
public class WorkplaceImport {
	/** The workplace code. */
	private String workplaceCode; //職場コード
	
	/** The workplace generic name. */
	private String workplaceGenericName; //職場総称
	
	/** The workplace name. */
	private String workplaceName; //職場表示名
	
}
