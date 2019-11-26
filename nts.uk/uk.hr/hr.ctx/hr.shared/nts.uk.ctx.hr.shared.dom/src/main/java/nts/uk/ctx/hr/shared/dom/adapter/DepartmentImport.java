package nts.uk.ctx.hr.shared.dom.adapter;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DepartmentImport {
	/**
	 * 会社ID
	 */
	private String companyId;

	/**
	 * 削除フラグ
	 */
	private boolean deleteFlag;

	/**
	 * 部門履歴ID
	 */
	private String departmentHistoryId;

	/**
	 * 部門ID
	 */
	private String departmentId;

	/**
	 * 部門コード
	 */
	private String departmentCode;

	/**
	 * 部門名称
	 */
	private String departmentName;

	/**
	 * 部門総称
	 */
	private String departmentGeneric;

	/**
	 * 部門表示名
	 */
	private String departmentDisplayName;

	/**
	 * 階層コード
	 */
	private String hierarchyCode;

	/**
	 * 部門外部コード
	 */
	private String departmentExternalCode;

	public DepartmentImport(String companyId, boolean deleteFlag, String departmentHistoryId, String departmentId,
			String departmentCode, String departmentName, String departmentGeneric, String departmentDisplayName,
			String hierarchyCode, String departmentExternalCode) {
		super();
		this.companyId = companyId;
		this.deleteFlag = deleteFlag;
		this.departmentHistoryId = departmentHistoryId;
		this.departmentId = departmentId;
		this.departmentCode = departmentCode;
		this.departmentName = departmentName;
		this.departmentGeneric = departmentGeneric;
		this.departmentDisplayName = departmentDisplayName;
		this.hierarchyCode = hierarchyCode;
		this.departmentExternalCode = departmentExternalCode;
	}
	
	
	
}
