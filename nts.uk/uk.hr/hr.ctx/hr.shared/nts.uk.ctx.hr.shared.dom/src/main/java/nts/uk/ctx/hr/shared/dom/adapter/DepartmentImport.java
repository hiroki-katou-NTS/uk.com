package nts.uk.ctx.hr.shared.dom.adapter;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DepartmentImport {
	/** The department code. */
	private String departmentCode; // 部門コード
	
	/** The department name. */
	private String departmentName; // 部門表示名
	
	/** The department generic name. */
	private String departmentGenericName; // 部門総称

	public DepartmentImport(String departmentCode, String departmentName, String departmentGenericName) {
		super();
		this.departmentCode = departmentCode;
		this.departmentName = departmentName;
		this.departmentGenericName = departmentGenericName;
	}
}
