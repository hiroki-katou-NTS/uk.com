package nts.uk.ctx.at.function.dom.adapter.annualworkschedule;

import lombok.AllArgsConstructor;
import lombok.Value;

@AllArgsConstructor
@Value
public class DepartmentImport {
	/** The department code. */
	private String departmentCode; // 部門コード
	
	/** The department name. */
	private String departmentName; // 部門表示名
	
	/** The department generic name. */
	private String departmentGenericName; // 部門総称
}
