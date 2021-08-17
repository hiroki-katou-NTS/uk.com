package nts.uk.ctx.at.aggregation.dom.adapter.employee;

import lombok.AllArgsConstructor;
import lombok.Value;

@AllArgsConstructor
@Value
public class EmploymentImport {
	/** The employment code. */
	private String employmentCode; //雇用コード
	
	/** The employment name. */
	private String employmentName; //雇用名称
}
