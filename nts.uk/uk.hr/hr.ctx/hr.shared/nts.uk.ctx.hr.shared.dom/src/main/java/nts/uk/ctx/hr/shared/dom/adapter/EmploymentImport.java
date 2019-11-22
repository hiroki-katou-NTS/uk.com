package nts.uk.ctx.hr.shared.dom.adapter;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EmploymentImport {

	/** The employment code. */
	private String employmentCode; //雇用コード
	
	/** The employment name. */
	private String employmentName; //雇用名称

	public EmploymentImport(String employmentCode, String employmentName) {
		super();
		this.employmentCode = employmentCode;
		this.employmentName = employmentName;
	}

}
